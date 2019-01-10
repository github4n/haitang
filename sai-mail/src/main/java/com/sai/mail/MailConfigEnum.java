package com.sai.mail;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zyq
 * @date: 2018/12/29
 */
public enum  MailConfigEnum {

    HUGO("hugo"),
    HUGO_SENDER("hugo_sender")
    ;

    private final String configName;

    private MailConfigEnum(String configName) {
        this.configName = configName;
    }

    public String configName() {
        return this.configName;
    }

    private static Map<String, MailConfigEnum> configLookup = new HashMap<>(2);
    static {
        for (MailConfigEnum type : MailConfigEnum.values()) {
            configLookup.put(type.configName, type);
        }
    }

    public static MailConfigEnum forConfig(String name) {
        return configLookup.get(name);
    }
}
