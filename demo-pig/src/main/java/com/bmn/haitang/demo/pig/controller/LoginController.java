package com.bmn.haitang.demo.pig.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.core.publisher.Mono;

/**
 * Created by Administrator on 2017/6/26.
 */
@RestController
@RequestMapping(value = "/v1/api")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 获取登录界面
     * @return
     */
    @GetMapping("/commond/{skill}/{subType}")
    public DeferredResult<JSONObject> login(@PathVariable("skill") String skill,
        @PathVariable("subType") String subType, String keyWord) {
        DeferredResult<JSONObject> result = new DeferredResult<>();

        return result;
    }

    @GetMapping("/login")
    public Mono<JSONObject> login(@PathVariable("userId") String userId, String keyWord) {
        return null;
    }








}
