package com.bmn.rpc.demo.filter;


import com.alipay.sofa.rpc.codec.Compressor;
import com.alipay.sofa.rpc.codec.CompressorFactory;
import com.alipay.sofa.rpc.core.exception.RpcErrorType;
import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.filter.FilterInvoker;
import com.bmn.haitang.demo.serialize.pb.msg.Rpc.HelloRequestMsg;
import com.bmn.haitang.demo.serialize.pb.msg.Rpc.HelloResponseMsg;
import com.google.protobuf.ByteString;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * rpc server 压缩过滤器
 *
 * <p>对请求参数HelloRequestMsg解压缩。对响应结果HelloResponseMsg压缩过滤器</p>
 *
 * 在rpc调用时。如果本次请求是对接口参数压缩发送，则需要对参数解压缩。同时响应结果进行压缩后返回。
 *
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/4
 */
public class SnappyServerFilter extends Filter {
    // 日志打印 sofa rpc 框架日志中
    private static final Logger logger = LoggerFactory.getLogger(SnappyServerFilter.class);

    public static final String SNAPPY_NAME = "snappy";

    static {
        CompressorFactory.getCompressor(SNAPPY_NAME);
    }

    @Override
    public SofaResponse invoke(FilterInvoker invoker, SofaRequest request) throws SofaRpcException {
        // 未开启压缩
        if (request.getRequestProp("compress") == null) {
            return invoker.invoke(request);
        }

        logger.debug("rpc call compress: true");

        Object[] args = request.getMethodArgs();
        Compressor compressor = CompressorFactory.getCompressor(SNAPPY_NAME);
        if (args != null && args.length == 1) {
            try {
                // 获取请求参数
                HelloRequestMsg msg = (HelloRequestMsg) args[0];
                // 获取压缩数据
                ByteString compressData = msg.getCompressData();
                // 解压缩
                byte[] cdata = compressor.deCompress(compressData.toByteArray());
                // 重新设置请求参数
                args[0] = msg.toBuilder().clearCompressData().setData(new String(cdata, "utf-8"))
                    .build();
            } catch (UnsupportedEncodingException e) {
                logger.error("call snappy request args:{} error", args, e);
                throw new SofaRpcException(RpcErrorType.SERVER_FILTER,
                    "call snappy filter request error", e);
            }
        }

        SofaResponse response = invoker.invoke(request);

        Object resp = response.getAppResponse();
        if (resp == null) {
            return response;
        }

        try {
            // 获取响应结果
            HelloResponseMsg msg = (HelloResponseMsg) resp;
            // 获取战斗原始字符串
            String originData = msg.getResult();
            // 压缩
            byte[] ddata = compressor.compress(originData.getBytes("utf-8"));
            // 重新生成压缩后的响应结果
            HelloResponseMsg responseMsg = msg.toBuilder().clearResult()
                .setCompressResult(ByteString.copyFrom(ddata)).build();

            response.setAppResponse(responseMsg);
        } catch (Exception e) {
            logger.error("call snappy response error", e);
            throw new SofaRpcException(RpcErrorType.SERVER_FILTER,
                "call snappy filter response error",
                e);
        }

        return response;
    }

}
