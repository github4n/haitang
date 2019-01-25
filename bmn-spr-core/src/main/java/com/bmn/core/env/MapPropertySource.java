package com.bmn.core.env;

import com.sai.util.StringUtils;

import java.util.Map;
import javax.annotation.Nullable;

/**
 * @author: zyq
 * @date: 2018/12/29
 */
public class MapPropertySource extends EnumerablePropertySource<Map<String, Object>> {


    public MapPropertySource(String name,
        Map<String, Object> source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        return StringUtils.toStringArray(this.source.keySet());
    }

    @Override
    public boolean containsProperty(String name) {
        return this.source.containsKey(name);
    }

    @Nullable
    @Override
    public Object getProperty(String name) {
        return this.source.get(name);
    }
}
