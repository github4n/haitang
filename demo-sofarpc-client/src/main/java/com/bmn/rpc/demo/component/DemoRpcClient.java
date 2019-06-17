package com.bmn.rpc.demo.component;

import com.alipay.sofa.rpc.codec.SerializerFactory;
import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.rpc.config.ApplicationConfig;
import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.config.MethodConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.bmn.rpc.demo.filter.GrayLevelClientFilter;
import com.bmn.rpc.demo.filter.SnappyClientFilter;
import com.bmn.rpc.demo.service.HelloWorldService;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/15
 */
@Component
public class DemoRpcClient implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(DemoRpcClient.class);


    @Value("${rpc.zookeeper.address}")
    private String zkAddress;

    @Value("${rpc.request.timeout}")
    private int requestTimeout;

    @Value("${rpc.connection.timeout}")
    private int connectionTimeout;
    @Value("${rpc.connection.size}")
    private int connectionSize;

    private HelloWorldService helloWorldService;

    public HelloWorldService getService() {
        return helloWorldService;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        RegistryConfig registryConfig = new RegistryConfig()
            .setProtocol(RpcConstants.REGISTRY_PROTOCOL_ZK)
            .setAddress(zkAddress);

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setAppName("spurs");

        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setName("helloCallback");
        methodConfig.setInvokeType(RpcConstants.INVOKER_TYPE_CALLBACK);

        MethodConfig helloMethodConfig = new MethodConfig();
        helloMethodConfig.setName("hello");
        helloMethodConfig.setInvokeType(RpcConstants.INVOKER_TYPE_SYNC);

        // rpc 接口调用配置
        ConsumerConfig<HelloWorldService> consumerConfig = new ConsumerConfig<HelloWorldService>()
            .setInterfaceId(HelloWorldService.class.getName())
            .setRegistry(registryConfig)
            .setMethods(Arrays.asList(methodConfig, helloMethodConfig))
            .setApplication(applicationConfig)
            .setProtocol(RpcConstants.PROTOCOL_TYPE_BOLT)
            .setInvokeType(RpcConstants.INVOKER_TYPE_FUTURE)
            .setSerialization(RpcConstants.SERIALIZE_PROTOBUF)
            .setConnectionNum(connectionSize)
            .setRouter(Arrays.asList("!directUrl", "!mesh"))
            .setConnectTimeout(connectionTimeout)
            .setTimeout(requestTimeout);

        consumerConfig.setFilterRef(Arrays.asList(new GrayLevelClientFilter(), new SnappyClientFilter()));

        /**
         * 预热加载序列化。避免请求请求超时
         *
         * 默认是首次数据序列化时才加载。加载会很长时间。
         */
        SerializerFactory.getSerializer(RpcConstants.SERIALIZE_PROTOBUF);

        helloWorldService = consumerConfig.refer();

        logger.info("demo rpc client start finish");
    }

}
