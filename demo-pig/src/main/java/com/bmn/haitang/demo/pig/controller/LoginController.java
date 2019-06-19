package com.bmn.haitang.demo.pig.controller;

import com.alibaba.fastjson.JSON;
import com.bmn.haitang.demo.pig.service.LoginService;
import com.bmn.haitang.demo.pig.vo.LoginResponseVO;
import com.bmn.haitang.demo.pig.vo.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2017/6/26.
 */
@RestController
@RequestMapping(value = "/v1/api/login")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("page/login");
        return mv;
    }

    @PostMapping("/loginService")
    public String login(@RequestBody MultiValueMap<String, String> model) {
        String loginName = model.getFirst("loginName");
        String loginPwd = model.getFirst("loginPwd");

        ResponseDTO response = loginService.login(loginName, loginPwd);
        if(response.isError()) {

            Object resp = response.getResp();
            return JSON.toJSONString(resp);
        }

        LoginResponseVO responseVO = new LoginResponseVO();
        responseVO.setSuccess("v1/api/hello/index");
        return JSON.toJSONString(responseVO);
    }




}
