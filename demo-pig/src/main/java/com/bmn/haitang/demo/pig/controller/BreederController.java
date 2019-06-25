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
@RequestMapping(value = "/breeder")
public class BreederController {
    private static final Logger logger = LoggerFactory.getLogger(BreederController.class);

    @Autowired
    private HelloService helloService;

    /**
     * 猪窝
     * @return
     */
    @GetMapping("/pigsty")
    public ModelAndView pigsty() {
        ModelAndView mv = new ModelAndView();

        Admin admin = OnlineAdminManager.INSTANCE.getOnlineAdmin("123");
        if(admin == null) {
            mv.setViewName("/pig");
            return mv;
        }

        helloService.hello(mv);

        mv.addObject("admin", admin);

        mv.setViewName("page/breeder/pigsty");
        return mv;
    }


}
