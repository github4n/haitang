package com.sai;

import com.sai.config.ConfigManager;
import com.sai.core.ApplicationRunner;
import com.sai.mail.MailManager;
import com.sai.runner.KeywordMailRunner;
import com.sai.runner.LogTestRunner;
import com.sai.runner.ServiceContextRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: zyq
 * @date: 2018/12/29
 */
public class SaiApplication {

    private static final Logger logger = LogManager.getLogger(SaiApplication.class);

    private Set<Class<?>> primaryClass;

    private SaiApplication(Class<?>... primaryClass) {
        this.primaryClass = new LinkedHashSet<>(Arrays.asList(primaryClass));
    }

    private void run(String[] args) {
        try {
            cannRunners(args);
        }catch (Throwable ex) {
            handleRunFailure(ex);
            throw new IllegalStateException(ex);
        }

        logger.info("Application run success.");
    }

    private void cannRunners(String[] args) {
        List<ApplicationRunner> runners = new ArrayList<>();
        runners.add(ConfigManager.getInstance());
        runners.add(MailManager.getInstance());
        runners.add(new ServiceContextRunner());
        //runners.add(new KeywordMailRunner());
        runners.add(new LogTestRunner());

        for(ApplicationRunner runner : runners) {
            callRunners(runner, args);
        }
    }

    private void callRunners(ApplicationRunner runner, String[] args) {
        try {
            runner.run(args);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to execute ApplicationRunner", ex);
        }
    }

    private void handleRunFailure(Throwable exception) {
        logger.error("Application start fail.", exception);
    }

    public static void run(Class<?>[] primaryClass, String[] args) {
        new SaiApplication(primaryClass).run(args);
    }

    public static void main(String[] args) {
        SaiApplication.run(new Class<?>[0], args);
    }
}
