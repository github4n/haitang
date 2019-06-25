package com.bmn.haitang.demo.pig.controller;

import com.bmn.haitang.demo.pig.doman.Admin;
import com.bmn.haitang.demo.pig.manager.OnlineAdminManager;
import com.bmn.haitang.demo.pig.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2017/6/26.
 */
@Controller
@RequestMapping(value = "/nour/pig")
public class PigController {
    private static final Logger logger = LoggerFactory.getLogger(PigController.class);

    @Autowired
    private HelloService helloService;

    /**
     * 猪猪集市
     * @return
     */
    @GetMapping("/market")
    public ModelAndView market() {
        ModelAndView mv = new ModelAndView();

        Admin admin = OnlineAdminManager.INSTANCE.getOnlineAdmin("123");
        if(admin == null) {
            mv.setViewName("/pig");
            return mv;
        }

        helloService.hello(mv);

        mv.addObject("admin", admin);

        mv.setViewName("page/pig/market");
        return mv;
    }

}
