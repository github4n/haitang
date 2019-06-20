package com.bmn.rpc.demo.callback;

import com.alipay.common.tracer.extensions.log.constants.MDCKeyConstants;
import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.invoke.SofaResponseCallback;
import com.alipay.sofa.rpc.core.request.RequestBase;
import org.slf4j.MDC;

/**
 *
 * sofa rpc 异步响应时，在调用callback 之前，清除了tracer数据。
 *
 * 所以需要自己重新配置
 *
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/19
 */
public class SofaTracerResponseCallback implements SofaResponseCallback<String> {

    private SofaResponseCallback<String> callback;

    public SofaTracerResponseCallback(SofaResponseCallback callback) {
        this.callback = callback;
    }


    @Override
    public void onAppResponse(Object appResponse, String methodName, RequestBase request) {
        try {
            clientReceived();

            this.callback.onAppResponse(appResponse, methodName, request);
        } finally {
            removeTracer();
        }
    }

    @Override
    public void onAppException(Throwable throwable, String methodName, RequestBase request) {
        try {
            clientReceived();

            this.callback.onAppException(throwable, methodName, request);
        } finally {
            removeTracer();
        }
    }

    @Override
    public void onSofaException(SofaRpcException sofaException, String methodName,
        RequestBase request) {
        try {
            clientReceived();

            this.callback.onSofaException(sofaException, methodName, request);
        } finally {
            removeTracer();
        }
    }

    // sofarpc 跟踪id
    private String tracerId;
    private String spanId;

    // 业务请求跟踪id
    private String requestTracerId;

    /**
     * sofa rpc 发送请求时调用。通过过滤器调用
     */
    public void clientBefore() {
        tracerId = MDC.get(MDCKeyConstants.MDC_TRACEID);
        spanId = MDC.get(MDCKeyConstants.MDC_SPANID);
    }

    /**
     * 客户端接收到响应后
     */
    private void clientReceived() {
        if (tracerId != null && spanId != null) {
            MDC.put(MDCKeyConstants.MDC_TRACEID, tracerId);
            MDC.put(MDCKeyConstants.MDC_SPANID, spanId);
        }

    }

    /**
     * 处理响应结果结束
     */
    private void removeTracer() {
        MDC.remove(MDCKeyConstants.MDC_TRACEID);
        MDC.remove(MDCKeyConstants.MDC_SPANID);
    }
}
