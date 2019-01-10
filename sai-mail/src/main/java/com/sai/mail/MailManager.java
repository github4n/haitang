package com.sai.mail;

import com.sai.config.ConfigManager;
import com.sai.core.ApplicationRunner;
import com.sai.service.MailService;
import com.sai.service.ServiceContext;
import com.sai.util.StringUtils;
import com.sai.util.TimeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author: zyq
 * @date: 2018/12/27
 */
public class MailManager implements ApplicationRunner {

    private static final Logger logger = LogManager.getLogger(MailManager.class);

    private volatile static MailManager instance;

    private Map<MailConfigEnum, MailConfig> configMap = new HashMap<>(8);

    private MailManager() {
    }

    public static MailManager getInstance() {
        if (instance == null) {
            synchronized (MailManager.class) {
                if (instance == null) {
                    instance = new MailManager();
                }
            }
        }

        return instance;
    }


    public void start() {
        ConfigManager.getInstance()
            .loadMapConfig("mail.config", (String key, String property, Object value) -> {

                MailConfigEnum configName = MailConfigEnum.forConfig(key);
                if (configName == null) {
                    throw new IllegalStateException(String.format("%s config not support", key));
                }

                MailConfig mailConfig = configMap.get(configName);
                if (mailConfig == null) {
                    mailConfig = new MailConfig();
                    configMap.put(configName, mailConfig);
                }

                if(value == null) {
                    return;
                }

                mailConfig.set(property, String.valueOf(value));
            });
    }

    public MailConfig getConfig(MailConfigEnum configName) {
        MailConfig config = configMap.get(configName);
        if(config == null) {
            throw new IllegalStateException("mail config name: " + configName + " not exist");
        }

        return config;
    }

    @Override
    public void run(String[] args) throws Exception {
        this.start();
        logger.info("mail manager runner start.");
    }
}
