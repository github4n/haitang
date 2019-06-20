package com.bmn.haitang.demo.pig.vo.ui;

import java.util.List;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/20
 */
public interface UIComponent extends Cloneable{
    int getId();

    List<UIComponent> getChildren();

    void setChildren(List<UIComponent> component);

    UIComponent clone() throws CloneNotSupportedException;

    void filter(UIComponentFilter filter);

    interface UIComponentFilter {
        void doFilter(List<UIComponent> components);
    }
}
