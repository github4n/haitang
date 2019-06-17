package com.bmn.rpc.demo.filter;


import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.filter.FilterInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * rpc client 灰度测试过滤器
 *
 * <p>修改请求方法为灰度方法</p>
 *
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/4
 */
public class GrayLevelClientFilter extends Filter {
    // 日志打印 sofa rpc 框架日志中
    private static final Logger logger = LoggerFactory.getLogger(GrayLevelClientFilter.class);

    public static final String SNAPPY_NAME = "snappy";

    /**
     * 可以自定义配置文件，配置是否开启灰度
     */
    public boolean needGray() {
        return true;
    }

    /**
     * 在业务线程中被调用
     */
    @Override
    public SofaResponse invoke(FilterInvoker invoker, SofaRequest request) throws SofaRpcException {
        if (needGray()) {

            logger.debug("rpc call request gray: true");

            // 修改调用方法为灰度方法
            request.setMethodName("helloGray");
        }

        return invoker.invoke(request);
    }

}
