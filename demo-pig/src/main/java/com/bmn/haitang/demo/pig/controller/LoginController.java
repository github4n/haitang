package com.bmn.haitang.demo.pig.controller;

import com.alibaba.fastjson.JSONObject;
import com.bmn.haitang.demo.pig.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/login/{userId}")
    public ModelAndView login(@PathVariable("userId") String userId, String keyWord) {
        User user = new User();
        user.setName("张三");
        user.setAge(20);

        ModelAndView mv = new ModelAndView();

        mv.addObject("user", user);

        mv.setViewName("page/login");

        return mv;
    }








}
