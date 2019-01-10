package com.sai.runner;

import com.sai.core.ApplicationRunner;
import com.sai.service.ServiceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: zyq
 * @date: 2019/1/2
 */
public class KeywordMailRunner implements ApplicationRunner {
    private static final Logger logger = LogManager.getLogger(KeywordMailRunner.class);

    private static ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
        new ThreadFactory() {
            private final AtomicInteger id = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("keyword mail runner thread " + id.getAndIncrement());
                return t;
            }
        });

    public void start() {
        executorService.scheduleAtFixedRate(() -> {
            monitor();
        }, 0, 1, TimeUnit.HOURS);
    }

    private void monitor() {
        try {
            int hour = LocalDateTime.now().getHour();
            if (hour != 20) {
                return;
            }

            ServiceContext.getKeywordService().pullMailToUpload();
        } catch (Throwable ex) {
            logger.error("keyword mail runner monitor error.", ex);
        }
    }

    @Override
    public void run(String[] args) throws Exception {
        start();
        logger.info("keyword mail runner start.");
    }
}
