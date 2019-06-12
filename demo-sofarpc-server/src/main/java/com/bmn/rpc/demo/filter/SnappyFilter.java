package com.bmn.rpc.demo.filter;


import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.filter.FilterInvoker;
import com.bmn.haitang.demo.serialize.pb.msg.Rpc.HelloRequestMsg;
import com.bmn.haitang.demo.serialize.pb.msg.Rpc.HelloResponseMsg;
import com.google.protobuf.ByteString;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/4
 */
public class SnappyFilter extends Filter {

    @Override
    public SofaResponse invoke(FilterInvoker invoker, SofaRequest request) throws SofaRpcException {
        Object[] args = request.getMethodArgs();
        if(args != null && args.length == 1) {
            Object arg = args[0];

            if (arg instanceof HelloRequestMsg) {
                HelloRequestMsg requestMsg = (HelloRequestMsg) arg;
                ByteString data = requestMsg.getData();


            }


        }

        return invoker.invoke(request);
    }

    @Override
    public void onAsyncResponse(ConsumerConfig config, SofaRequest request, SofaResponse response,
        Throwable exception) throws SofaRpcException {
        Object resp = response.getAppResponse();
        if(response != null && resp instanceof HelloResponseMsg) {
            HelloResponseMsg responseMsg = (HelloResponseMsg) resp;
            ByteString result = responseMsg.getResult();


        }

    }
}
