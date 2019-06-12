package com.bmn.rpc.demo.service.impl;

import com.bmn.rpc.demo.service.HelloWorldService;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/12
 */
public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public String hello(String msg) {
        return "world";
    }
}
