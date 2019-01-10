package com.sai.runner;

import com.sai.core.ApplicationRunner;
import com.sai.service.ServiceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author: zyq
 * @date: 2019/1/2
 */
public class ServiceContextRunner implements ApplicationRunner {
    private static final Logger logger = LogManager.getLogger(ServiceContextRunner.class);


    @Override
    public void run(String[] args) throws Exception {
        ServiceContext.init();
        logger.info("service context start.");
    }
}
