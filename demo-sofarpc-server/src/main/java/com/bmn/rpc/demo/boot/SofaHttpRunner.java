package com.bmn.rpc.demo.boot;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.bmn.bootstrap.config.ApplicationConfig;
import com.bmn.bootstrap.context.BmnApplicationContext;
import com.bmn.bootstrap.runner.ApplicationRunner;
import com.bmn.bootstrap.util.StringUtils;
import com.bmn.rpc.demo.service.RestService;
import com.bmn.rpc.demo.service.impl.RestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/12
 */
public class SofaHttpRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(SofaHttpRunner.class);

    @Override
    public void run(BmnApplicationContext context, String[] args) throws Exception {
        logger.info("soft http starting...");

        String protocol = ApplicationConfig.getInstance().getStringProperty("sofa.http.protocal");
        protocol = StringUtils.isEmpty(protocol) ? "rest" : protocol;

        int port = ApplicationConfig.getInstance().getIntProperty("sofa.http.port");
        int maxThreads = ApplicationConfig.getInstance().getIntProperty("sofa.http.maxThreads");
        int ioThreads = ApplicationConfig.getInstance().getIntProperty("sofa.http.ioThreads");

        ServerConfig serverConfig = new ServerConfig()
            .setProtocol(protocol)
            .setMaxThreads(maxThreads)
            .setIoThreads(ioThreads)
            .setPort(port)
            .setDaemon(false);

        ProviderConfig<RestService> providerConfig = new ProviderConfig<RestService>()
            .setInterfaceId(RestService.class.getName())
            .setRef(new RestServiceImpl(context))
            .setBootstrap(protocol)
            .setServer(serverConfig)
            .setRegister(false);

        providerConfig.export();

        logger.info("soft http started, port:{}", port);
    }
}
