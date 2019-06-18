package com.bmn.rpc.demo.service;

import java.util.concurrent.ExecutionException;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/5/27
 */
public interface HelloClientService {

    public String callHello() throws ExecutionException, InterruptedException;
    public String callHelloFuture() throws ExecutionException, InterruptedException;
    public String callHelloCallback(DeferredResult<String> result) throws ExecutionException, InterruptedException;
}
