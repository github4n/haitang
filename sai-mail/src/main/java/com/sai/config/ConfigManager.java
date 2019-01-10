package com.sai.config;

import com.sai.core.ApplicationRunner;
import com.sai.core.env.EnumerablePropertySource;
import com.sai.core.env.PropertiesPropertySource;
import com.sai.core.env.PropertySource;
import com.sai.mail.MailConfig;
import com.sai.mail.MailConfigEnum;
import com.sai.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.K;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 配置文件管理器
 */
public class ConfigManager implements ApplicationRunner {
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);

    private static final ConfigManager INSTANCE = new ConfigManager();

    private ConfigManager() {
    }

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    //配置文件路径
    private static final String CONFIG_PATH = "application.properties";

    private EnumerablePropertySource properties;

    /*
     * 加载配置文件
     */
    public void start() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        URL url = classLoader.getResource(CONFIG_PATH);
        if (url == null) {
            throw new IllegalStateException("服务加载配置失败");
        }

        Properties properties = new Properties();

        try (InputStream is = url.openStream()) {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("服务加载配置异常");
        }

        this.properties = new PropertiesPropertySource("system-config", properties);
    }

    public PropertySource getProperties() {
        if (properties == null) {
            throw new NullPointerException("服务配置未初始化异常");
        }

        return this.properties;
    }

    public String getProperty(String property) {
        Object v = this.properties.getProperty(property);
        return v == null ? "" : v.toString();
    }

    public String[] getPropertyToArray(String property) {
        String v = getProperty(property);
        return StringUtils.tokenizeToStringArray(v, ",");
    }

    public void loadMapConfig(String propertyPrefix, ConfigMapConsumer<String, String, Object> consumer) {
        Map<String, MailConfig> map = new HashMap<>();

        String[] propertyNames = this.properties.getPropertyNames();
        for(String name : propertyNames) {
            if(name.startsWith(propertyPrefix)) {
                int idx = propertyPrefix.length() + 1;

                String config = name.substring(idx);
                String[] configs = config.split("\\.");
                if(configs.length != 2) {
                    throw new IllegalStateException(String.format("%s config error.", name));
                }


                try {
                    consumer.accept(configs[0], configs[1], this.properties.getProperty(name));
                }catch (Exception e) {
                    throw new IllegalStateException(String.format("%s config accept error.", name));
                }

            }
        }
    }

    @Override
    public void run(String[] args) throws Exception {
        this.start();
        logger.info("config manager runner start.");
    }

    @FunctionalInterface
    public interface ConfigMapConsumer<K, P, V> {
        void accept(K k, P p, V v);
    }
}
