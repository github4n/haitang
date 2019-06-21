package com.bmn.haitang.demo.pig.controller;

import com.alibaba.fastjson.JSON;
import com.bmn.haitang.demo.pig.doman.Admin;
import com.bmn.haitang.demo.pig.manager.OnlineAdminManager;
import com.bmn.haitang.demo.pig.service.HelloService;
import com.bmn.haitang.demo.pig.service.LoginService;
import com.bmn.haitang.demo.pig.vo.LoginResponseVO;
import com.bmn.haitang.demo.pig.vo.ResponseDTO;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2017/6/26.
 */
@Controller
@RequestMapping(value = "/spring")
public class BreederController {
    private static final Logger logger = LoggerFactory.getLogger(BreederController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private HelloService helloService;

    @RequestMapping
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/pig/");
        return mv;
    }

    @RequestMapping("/")
    public ModelAndView pig() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("page/login");
        return mv;
    }

    @PostMapping("/loginService")
    @ResponseBody
    public String login(@RequestBody MultiValueMap<String, String> model) {
        String loginName = model.getFirst("loginName");
        String loginPwd = model.getFirst("loginPwd");

        ResponseDTO response = loginService.login(loginName, loginPwd);
        if(response.isError()) {

            Object resp = response.getResp();
            return JSON.toJSONString(resp);
        }

        LoginResponseVO responseVO = new LoginResponseVO();
        responseVO.setSuccess("hello");
        return JSON.toJSONString(responseVO);
    }


    @GetMapping("/mvc/servlet/show")
    public ModelAndView hello() {
        ModelAndView mv = new ModelAndView();

        Admin admin = OnlineAdminManager.INSTANCE.getOnlineAdmin("123");
        if(admin == null) {
            mv.setViewName("/pig");
            return mv;
        }

        helloService.hello(mv);

        mv.addObject("admin", admin);

        mv.setViewName("page/bm/role");
        return mv;
    }

    @RequestMapping("logout")
    public ModelAndView logout(HttpServletRequest request) {
        OnlineAdminManager.INSTANCE.offline("123");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/pig/");

        return mv;
    }

}
