package com.bmn.core.env;

import javax.annotation.Nullable;

/**
 * @author: zyq
 * @date: 2018/12/29
 */
public abstract class PropertySource<T> {

    protected final String name;
    protected final T source;

    public PropertySource(String name, T source) {
        this.name = name;
        this.source = source;
    }

    public String getName() {
        return this.name;
    }

    public T getSource() {
        return this.source;
    }

    public boolean containsProperty(String name) {
        return (getProperty(name) != null);
    }

    @Nullable
    public abstract Object getProperty(String name);

}
