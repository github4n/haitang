package com.bmn.rpc.demo.boot;

import com.alipay.sofa.rpc.codec.SerializerFactory;
import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.bmn.bootstrap.annotation.Order;
import com.bmn.bootstrap.config.ApplicationConfig;
import com.bmn.bootstrap.context.BmnApplicationContext;
import com.bmn.bootstrap.runner.ApplicationRunner;
import com.bmn.bootstrap.util.StringUtils;
import com.bmn.rpc.demo.filter.SnappyServerFilter;
import com.bmn.rpc.demo.service.HelloWorldService;
import com.bmn.rpc.demo.service.impl.HelloWorldServiceImpl;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/12
 */
@Order(1)
public class SofaRpcRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(SofaRpcRunner.class);


    @Override
    public void run(BmnApplicationContext context, String[] args) throws Exception {
        logger.info("sofa rpc starting...");


        ApplicationConfig config = ApplicationConfig.getInstance();

        String zkAddress = config.getStringProperty("sofa.zookeeperAddress");
        RegistryConfig registryConfig = new RegistryConfig()
            .setProtocol(RpcConstants.REGISTRY_PROTOCOL_ZK)
            .setAddress(zkAddress);

        String protocol = config.getStringProperty("sofa.protocol");
        protocol = StringUtils.isEmpty(protocol) ? "bolt" : protocol;
        int port = config.getIntProperty("sofa.port");

        int coreThreads = config.getIntProperty("sofa.pool.core");
        int maxThreads = config.getIntProperty("sofa.pool.max");
        int queueSize = config.getIntProperty("sofa.pool.queueSize");
        int aliveTime = config.getIntProperty("sofa.pool.aliveTime");
        boolean preStart = config.getBooleanProperty("sofa.pool.preStart");

        ServerConfig serverConfig = new ServerConfig()
            .setProtocol(protocol) // 设置一个协议，默认bolt
            .setPort(port) // 设置一个端口，默认12200
            .setCoreThreads(coreThreads)
            .setMaxThreads(maxThreads)
            .setQueues(queueSize)
            .setAliveTime(aliveTime)
            .setPreStartCore(preStart)
            .setDaemon(false); // 非守护线程

        ProviderConfig<HelloWorldService> providerConfig = new ProviderConfig<HelloWorldService>()
            .setInterfaceId(HelloWorldService.class.getName()) // 指定接口
            .setRef(new HelloWorldServiceImpl()) // 指定实现
            .setRegistry(registryConfig)
            .setParameter("grayscale", "0")
            .setServer(serverConfig); // 指定服务端


        providerConfig.setFilterRef(Arrays.asList(new SnappyServerFilter()));

        /**
         * 预热加载序列化。避免请求请求超时
         *
         * 默认是首次数据序列化时才加载。加载会很长时间。
         */
        SerializerFactory.getSerializer(RpcConstants.SERIALIZE_PROTOBUF);

        providerConfig.export(); // 发布服务

        logger.info("sofa rpc started, zk:{}, port:{}", zkAddress, port);
    }
}
