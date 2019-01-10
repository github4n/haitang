package com.sai.runner;

import com.sai.core.ApplicationRunner;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

/**
 * @author: zyq
 * @date: 2019/1/10
 */
public class LogTestRunner implements ApplicationRunner {
    private static final Logger logger = LogManager.getLogger(LogTestRunner.class);

    @Override
    public void run(String[] args) throws Exception {

        // 需要配置log4j2.component.properties开启调试信息
        StatusLogger.getLogger();

        Thread t = new Thread(()-> {
            sendLog();
        });

        t.setName("Log-test-runner-thread-1");
        t.start();
    }

    private static final String LOG_CONTENT = "this is test log content. haha";
    private static final String LOG_ERROR_CONTENT = "this is test log content. error";

    private void sendLog() {

        while(true) {
            logger.info(LOG_CONTENT);
            logger.error(LOG_CONTENT);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
