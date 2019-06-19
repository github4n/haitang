package com.bmn.haitang.demo.pig.controller;

import com.bmn.haitang.demo.pig.doman.Admin;
import com.bmn.haitang.demo.pig.manager.OnlineAdminManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2017/6/26.
 */
@RestController
@RequestMapping(value = "/v1/api/hello")
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);



    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();

        Admin admin = OnlineAdminManager.INSTANCE.getOnlineAdmin("123");
        if(admin == null) {
            mv.setViewName("/pig");
            return mv;
        }

        mv.addObject("admin", admin);

        mv.setViewName("page/hello");
        return mv;
    }



}
