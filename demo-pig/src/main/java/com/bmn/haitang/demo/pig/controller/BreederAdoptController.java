package com.bmn.haitang.demo.pig.controller;

import com.bmn.haitang.demo.pig.service.BreederService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2017/6/26.
 */
@Controller
@RequestMapping(value = "/breeder/adopt")
public class BreederAdoptController {
    private static final Logger logger = LoggerFactory.getLogger(BreederAdoptController.class);

    @Autowired
    private BreederService breederService;

    /**
     * 猪窝
     * @return
     */
    @GetMapping("/page")
    public ModelAndView page() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("page/breeder/adopt");
        return mv;
    }


    @PostMapping("/grid")
    @ResponseBody
    public String grid() {
        return breederService.grid();
    }
}
