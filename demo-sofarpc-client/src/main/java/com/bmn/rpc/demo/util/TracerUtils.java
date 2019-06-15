package com.bmn.rpc.demo.util;

import com.alipay.common.tracer.core.context.trace.SofaTraceContext;
import com.alipay.common.tracer.core.holder.SofaTraceContextHolder;
import com.alipay.common.tracer.core.span.SofaTracerSpan;
import com.alipay.sofa.rpc.core.exception.RpcErrorType;
import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.tracer.sofatracer.RpcSofaTracer;
import com.alipay.sofa.rpc.tracer.sofatracer.code.TracerResultCode;
import com.alipay.sofa.rpc.tracer.sofatracer.log.tags.RpcSpanTags;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/5/30
 */
public abstract class TracerUtils {

    /**
     * 处理场景：
     * <p>客户端运行过程中，服务器全宕机情况</p>
     * <p>客户端会一直发送请求。同时会创建请求SofaTracerSpan</p>
     * <p>然后路由过程中发现没有服务器可用。抛出异常</p>
     * <p>此时请求应该为失败结束，但是sofarpc没有处理异步请求失败时清除SofaTracerSpan</p>
     *
     * 如果耗时影响业务。则直接调用finish()
     *
     * 参考：
     * <p>{@link RpcSofaTracer#clientReceived}</p>
     *
     * 异步调用异常后，清除tracer
     */
    public static void asyncCallErrorClearTracer(Throwable t) {
        long start = Instant.now().toEpochMilli();
        SofaTraceContext sofaTraceContext = SofaTraceContextHolder.getSofaTraceContext();
        SofaTracerSpan clientSpan = sofaTraceContext.pop();
        if (clientSpan == null) {
            return;
        }
        String resultCode = TracerResultCode.RPC_RESULT_ROUTE_FAILED;
        String errorSourceApp = clientSpan.getTagsWithStr().get(RpcSpanTags.LOCAL_APP);
        String tracerErrorCode = TracerResultCode.RPC_ERROR_TYPE_ADDRESS_ROUTE_ERROR;

        Throwable throwableShow = new SofaRpcException(RpcErrorType.CLIENT_ROUTER, t.getMessage());

        Map<String, String> contextMap = new HashMap<>();
        generateClientErrorContext(contextMap, clientSpan);
        clientSpan.reportError(tracerErrorCode, contextMap,
            throwableShow,
            errorSourceApp,
            RpcSofaTracer.ERROR_SOURCE);

        clientSpan.setTag(RpcSpanTags.RESULT_CODE, resultCode);
        //finish client
        clientSpan.finish();

        //client span
        if (clientSpan.getParentSofaTracerSpan() != null) {
            //restore parent
            sofaTraceContext.push(clientSpan.getParentSofaTracerSpan());
        }

        long e = Instant.now().toEpochMilli();
        System.out.println("esplied " + (e - start));
    }

    private static void generateClientErrorContext(Map<String, String> context,
        SofaTracerSpan clientSpan) {
        Map<String, String> tagsWithStr = clientSpan.getTagsWithStr();
        //记录的上下文信息
        context.put("serviceName", tagsWithStr.get(RpcSpanTags.SERVICE));
        context.put("methodName", tagsWithStr.get(RpcSpanTags.METHOD));
        context.put("protocol", tagsWithStr.get(RpcSpanTags.PROTOCOL));
    }
}
