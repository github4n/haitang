package com.bmn.rpc.demo;

import com.bmn.bootstrap.BmnApplication;
import com.bmn.bootstrap.annotation.ComponentScan;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/12
 */
@ComponentScan("com.bmn.rpc.demo.boot")
public class DemoServerApplication {

    public static void main(String[] args) {
        BmnApplication.run(DemoServerApplication.class, args);
    }
}
