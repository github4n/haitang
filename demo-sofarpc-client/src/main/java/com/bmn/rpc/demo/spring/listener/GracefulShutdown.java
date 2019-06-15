package com.bmn.rpc.demo.spring.listener;

import com.alipay.sofa.rpc.context.RpcRuntimeContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/15
 */

@Component
public class GracefulShutdown implements ApplicationListener<ContextClosedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(GracefulShutdown.class);

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {

        logger.info("shutdown rpc client start...");
        RpcRuntimeContext.destroy();

        logger.info("shutdown rpc client finish");
    }
}
