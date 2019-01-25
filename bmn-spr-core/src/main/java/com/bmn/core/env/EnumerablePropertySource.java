package com.bmn.core.env;

import com.bmn.util.ObjectUtils;

/**
 * @author: zyq
 * @date: 2018/12/29
 */
public abstract class EnumerablePropertySource<T> extends PropertySource<T> {


    public EnumerablePropertySource(String name, T source) {
        super(name, source);
    }


    @Override
    public boolean containsProperty(String name) {
        return ObjectUtils.containsElement(getPropertyNames(), name);
    }


    public abstract String[] getPropertyNames();
}
