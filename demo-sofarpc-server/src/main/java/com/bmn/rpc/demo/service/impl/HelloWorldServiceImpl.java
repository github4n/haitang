package com.bmn.rpc.demo.service.impl;

import com.bmn.haitang.demo.serialize.pb.msg.Rpc.HelloRequestMsg;
import com.bmn.haitang.demo.serialize.pb.msg.Rpc.HelloResponseMsg;
import com.bmn.rpc.demo.service.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/12
 */
public class HelloWorldServiceImpl implements HelloWorldService {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldServiceImpl.class);

    @Override
    public HelloResponseMsg hello(HelloRequestMsg msg) {
        logger.debug("receive hello request");
        return HelloResponseMsg.newBuilder().setResult("world").build();
    }

    @Override
    public HelloResponseMsg helloGray(HelloRequestMsg msg) {
        logger.debug("receive hello gray request");
        return HelloResponseMsg.newBuilder().setResult("world_gray").build();
    }

    @Override
    public HelloResponseMsg helloFuture(HelloRequestMsg msg) {
        logger.debug("receive hello future request");
        return HelloResponseMsg.newBuilder().setResult("world_future").build();
    }

    @Override
    public HelloResponseMsg helloCallback(HelloRequestMsg msg) {
        logger.debug("receive hello callback request");
        return HelloResponseMsg.newBuilder().setResult("world_callback").build();
    }
}
