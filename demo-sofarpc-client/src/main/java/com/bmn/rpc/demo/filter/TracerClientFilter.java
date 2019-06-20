package com.bmn.rpc.demo.filter;


import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.invoke.SofaResponseCallback;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.filter.FilterInvoker;
import com.bmn.rpc.demo.callback.SofaTracerResponseCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * rpc client 记录请求跟踪id过滤器
 *
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/4
 */
public class TracerClientFilter extends Filter {

    private static final Logger logger = LoggerFactory.getLogger(TracerClientFilter.class);

    @Override
    public SofaResponse invoke(FilterInvoker invoker, SofaRequest request) throws SofaRpcException {

        if (RpcConstants.INVOKER_TYPE_CALLBACK.equals(request.getInvokeType())) {
            SofaResponseCallback<String> callback = request.getSofaResponseCallback();
            if(callback != null && callback instanceof SofaTracerResponseCallback) {
                SofaTracerResponseCallback tracerResponseCallback = (SofaTracerResponseCallback) callback;

                tracerResponseCallback.clientBefore();

                logger.debug("rpc battle callback tracer success");
            }
        }

        return invoker.invoke(request);
    }

}
