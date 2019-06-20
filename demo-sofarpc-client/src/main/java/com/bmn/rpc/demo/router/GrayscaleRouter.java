package com.bmn.rpc.demo.router;

import com.alipay.sofa.rpc.client.ProviderInfo;
import com.alipay.sofa.rpc.client.Router;
import com.alipay.sofa.rpc.common.utils.StringUtils;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 恢度路由过滤
 *
 * 如果服务配置了支持恢度功能，客户端在开启恢度后。当有请求时，只会路由到恢度服务器
 *
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/20
 */
public class GrayscaleRouter extends Router {

    private static final Logger logger = LoggerFactory.getLogger(GrayscaleRouter.class);

    public static final String RPC_GRAYSCALE_ROUTER = "GRAYSCALE";

    private boolean isGrayLevel() {
        return true;
    }

    @Override
    public List<ProviderInfo> route(SofaRequest request, List<ProviderInfo> providerInfos) {
        if (!isGrayLevel()) {
            return providerInfos;
        }

        logger.debug("rpc call gray scale router start...");

        List<ProviderInfo> current = new ArrayList<>();
        for (ProviderInfo providerInfo : providerInfos) {
            if (StringUtils.equals("1", providerInfo.getAttr("grayscale"))) {
                current.add(providerInfo);
            }
        }

        if (current.isEmpty()) {
            logger.warn("rpc call gray scale router is empty");
        }

        logger.debug("rpc call gray scale router: {}", current);

        recordRouterWay(RPC_GRAYSCALE_ROUTER);
        return current;
    }
}
