package com.bmn.haitang.demo.pig.controller;

import com.bmn.haitang.demo.pig.service.PigService;
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
@RequestMapping(value = "/nour/pig")
public class PigController {
    private static final Logger logger = LoggerFactory.getLogger(PigController.class);

    @Autowired
    private PigService pigService;

    /**
     * 猪猪集市
     * @return
     */
    @GetMapping("/page")
    public ModelAndView page() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("page/pig/market");
        return mv;
    }

    @PostMapping("/grid")
    @ResponseBody
    public String grid() {
        String grid = pigService.grid();

        return grid;
    }


}
