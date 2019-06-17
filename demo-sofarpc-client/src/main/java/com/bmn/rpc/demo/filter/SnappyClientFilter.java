package com.bmn.rpc.demo.filter;


import com.alipay.sofa.rpc.codec.Compressor;
import com.alipay.sofa.rpc.codec.CompressorFactory;
import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.core.exception.RpcErrorType;
import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.filter.FilterInvoker;
import com.bmn.haitang.demo.serialize.pb.msg.Rpc.HelloRequestMsg;
import com.bmn.haitang.demo.serialize.pb.msg.Rpc.HelloResponseMsg;
import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * rpc client 压缩过滤器
 *
 * <p>对请求参数HelloRequestMsg压缩发送。对响应结果HelloResponseMsg解压缩过滤器</p>
 *
 * 在rpc调用时，对HelloRequestMsg data：数据解压发送。其它参数不压缩
 *
 * 如果请求时使用了压缩。则响应结果也需要解压缩
 *
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/4
 */
public class SnappyClientFilter extends Filter {
    // 日志打印 sofa rpc 框架日志中
    private static final Logger logger = LoggerFactory.getLogger(SnappyClientFilter.class);

    public static final String SNAPPY_NAME = "snappy";

    /**
     * 预热加载
     */
    static {
        CompressorFactory.getCompressor(SNAPPY_NAME);
    }

    /**
     * 可以自定义配置文件，配置是否开启压缩
     */
    public boolean needCompress() {
        return true;
    }

    /**
     * 在业务线程中被调用
     */
    @Override
    public SofaResponse invoke(FilterInvoker invoker, SofaRequest request) throws SofaRpcException {
        if (needCompress()) {

            //通知服务端本次请求启动压缩
            request.addRequestProp("compress", 0b1);

            logger.debug("rpc call request compress: true");

            Object[] args = request.getMethodArgs();
            if (args != null && args.length == 1) {

                Compressor compressor = CompressorFactory.getCompressor(SNAPPY_NAME);
                try {
                    // 获取接口参数
                    HelloRequestMsg msg = (HelloRequestMsg) args[0];
                    // 获取战斗数据字符串
                    String originData = msg.getData();
                    // 压缩
                    byte[] cdata = compressor.compress(originData.getBytes("utf-8"));
                    // 重新生成压缩后参数
                    args[0] = msg.toBuilder().clearData()
                        .setCompressData(ByteString.copyFrom(cdata))
                        .build();
                } catch (Exception e) {
                    logger.error("rpc call snappy request args:{} error", args, e);
                    throw new SofaRpcException(RpcErrorType.CLIENT_FILTER,
                        "rpc call snappy filter error",
                        e);
                }
            }
        }

        SofaResponse response = invoker.invoke(request);
        // 处理同步请求
        if(RpcConstants.INVOKER_TYPE_SYNC.equals(request.getInvokeType())) {
            onAsyncResponse((ConsumerConfig)invoker.getConfig(), request, response, null);
        }

        return response;
    }

    /**
     * 由RPC-CB Thread Pool。 在rpc callback线程池中被调用
     *
     * <p>由于是异步调用。在过滤器执行之前，先执行了EventBus.post(ClientAsyncReceiveEvent)。会清除MDC</p>
     * 所以此方法中打印的日志没有跟踪id
     *
     * 参考：{@link com.alipay.sofa.rpc.message.bolt.BoltFutureInvokeCallback#onResponse}
     */
    @Override
    public void onAsyncResponse(ConsumerConfig config, SofaRequest request, SofaResponse response,
        Throwable exception) throws SofaRpcException {
        // 判断是压缩的请求，响应结果需要解压缩
        // 如：timeout 异常后response为null
        if (response != null && request.getRequestProp("compress") != null) {

            logger.debug("rpc call response compress: true");

            Object resp = response.getAppResponse();
            Compressor compressor = CompressorFactory.getCompressor(SNAPPY_NAME);
            try {
                // 获取响应结果
                HelloResponseMsg msg = (HelloResponseMsg) resp;
                // 获取压缩数据
                ByteString compressResult = msg.getCompressResult();
                // 解压缩
                byte[] ddata = compressor.deCompress(compressResult.toByteArray());
                // 重新生成响应结果
                HelloResponseMsg responseMsg = HelloResponseMsg.newBuilder()
                    .setResult(new String(ddata, "utf-8")).build();
                response.setAppResponse(responseMsg);
            } catch (Exception e) {
                logger.error("rpc call snappy response error", e);
                throw new SofaRpcException(RpcErrorType.CLIENT_FILTER,
                    "rpc call snappy filter response error", e);
            }
        }
    }
}
