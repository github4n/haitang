package com.bmn.rpc.demo.controller;

import com.bmn.rpc.demo.service.HelloClientService;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/5/27
 */
@RestController
@RequestMapping(value = "/v1/api")
public class HelloController {

    @Autowired
    private HelloClientService battleClientService;

    @GetMapping("/hello")
    public DeferredResult<String> hello() {
        DeferredResult<String> result = new DeferredResult<>();

        try {
            String r = battleClientService.callHello();
            result.setResult(r);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping("/hello/future")
    public DeferredResult<String> helloFuture() {
        DeferredResult<String> result = new DeferredResult<>();

        try {
            String r = battleClientService.callHelloFuture();
            result.setResult(r);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    @GetMapping("/hello/callback")
    public DeferredResult<String> helloCallback() {
        DeferredResult<String> result = new DeferredResult<>();

        try {
            String r = battleClientService.callHelloCallback();
            result.setResult(r);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }
}
