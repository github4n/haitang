package com.bmn.rpc.demo.service;

import com.bmn.haitang.demo.serialize.pb.msg.Rpc.HelloRequestMsg;
import com.bmn.haitang.demo.serialize.pb.msg.Rpc.HelloResponseMsg;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/12
 */
public interface HelloWorldService {

    /**
     * 同步调用
     * @return
     */
    public HelloResponseMsg hello(HelloRequestMsg msg);

    /**
     * future方式调用
     * @return
     */
    public HelloResponseMsg helloFuture(HelloRequestMsg msg);

    /**
     * 回调调用
     * @return
     */
    public HelloResponseMsg helloCallback(HelloRequestMsg msg);
}
