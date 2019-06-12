package com.bmn.rpc.demo.boot.listener;

import com.alipay.common.tracer.core.configuration.SofaTracerConfiguration;
import com.alipay.common.tracer.core.configuration.SofaTracerExternalConfiguration;
import com.alipay.sofa.rpc.common.RpcConfigs;
import com.bmn.bootstrap.annotation.Order;
import com.bmn.bootstrap.config.ApplicationConfig;
import com.bmn.bootstrap.listener.ApplicationEvent;
import com.bmn.bootstrap.listener.ApplicationListener;
import com.bmn.bootstrap.listener.event.ContextRefreshedEvent;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * lua初始化处理
 *
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/5/21
 */
@Order(3)
public class RpcConfigApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory
        .getLogger(RpcConfigApplicationListener.class);

    private static final String TRACER_KEY = "tracer.%s";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("refresh rpc config begin...");

        Map<String, Object> customConfigs = ApplicationConfig
            .getInstance().getConfig("sofa.rpc", Map.class);
        if (customConfigs != null) {
            for (Map.Entry<String, Object> entry : customConfigs.entrySet()) {
                RpcConfigs.putValue(entry.getKey(), entry.getValue());
                logger
                    .info("refresh rpc config key:{}, value:{}", entry.getKey(), entry.getValue());
            }
        }

        // tracer配置
        SofaTracerConfiguration
            .setSofaTracerExternalConfiguration(new SofaTracerExternalConfiguration() {
                @Override
                public String getValue(String key) {
                    String tracerKey = String.format(TRACER_KEY, key);
                    return ApplicationConfig.getInstance().getStringProperty(tracerKey);
                }

                @Override
                public boolean contains(String key) {
                    String tracerKey = String.format(TRACER_KEY, key);
                    return ApplicationConfig.getInstance().containsProperty(tracerKey);
                }
            });

        logger.info("refresh rpc config finished");
    }

    @Override
    public boolean supportsEventType(ApplicationEvent eventType) {
        return eventType instanceof ContextRefreshedEvent;
    }

}
