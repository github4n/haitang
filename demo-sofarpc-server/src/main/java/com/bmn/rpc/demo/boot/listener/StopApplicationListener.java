package com.bmn.rpc.demo.boot.listener;

import com.alipay.sofa.rpc.context.RpcRuntimeContext;
import com.bmn.bootstrap.annotation.Order;
import com.bmn.bootstrap.listener.ApplicationEvent;
import com.bmn.bootstrap.listener.ApplicationListener;
import com.bmn.bootstrap.listener.event.ContextClosedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 停服处理
 *
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/5/21
 */
@Order()
public class StopApplicationListener implements ApplicationListener<ContextClosedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(StopApplicationListener.class);


    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.info("start closed application listener...");

        Thread t = new Thread(() -> close());
        t.setName("BMN-CLOSE-THREAD");
        t.start();

        logger.info("closed application listener finish");
    }

    private void close() {
        logger.info("sofa rpc close start...");

        RpcRuntimeContext.destroy();

        logger.info("sofa rpc close finished");
    }

    @Override
    public boolean supportsEventType(ApplicationEvent eventType) {
        return eventType instanceof ContextClosedEvent;
    }

}
