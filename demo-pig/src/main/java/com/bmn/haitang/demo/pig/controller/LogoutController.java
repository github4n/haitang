package com.bmn.haitang.demo.pig.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/6/26.
 */
@Controller
public class LogoutController {

    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {



        return "login";
    }

}
