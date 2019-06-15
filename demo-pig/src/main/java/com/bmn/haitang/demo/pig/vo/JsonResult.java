package com.bmn.haitang.demo.pig.vo;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/27.
 */
public class JsonResult {

    private Map<String, Object> values = new HashMap<>();

    public void putValue(String key, Object v) {
        values.put(key, v);
    }

    public String build() {
        return "";
    }
}
