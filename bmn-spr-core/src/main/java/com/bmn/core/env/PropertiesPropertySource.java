package com.bmn.core.env;

import java.util.Map;
import java.util.Properties;

/**
 * @author: zyq
 * @date: 2018/12/29
 */
public class PropertiesPropertySource extends MapPropertySource {

    public PropertiesPropertySource(String name, Properties source) {
        super(name, (Map) source);
    }

    public PropertiesPropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }
}
