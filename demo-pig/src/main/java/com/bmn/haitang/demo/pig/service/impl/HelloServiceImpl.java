package com.bmn.haitang.demo.pig.service.impl;

import com.alibaba.fastjson.JSON;
import com.bmn.haitang.demo.pig.service.AuthorityService;
import com.bmn.haitang.demo.pig.service.HelloService;
import com.bmn.haitang.demo.pig.vo.ui.UIComponent;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/20
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    private AuthorityService authorityService;

    @Override
    public void hello(ModelAndView model) {
        List<UIComponent> accordions = null;
        try {
            accordions = authorityService.getExplorer(0);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        model.addObject("accordions", JSON.toJSONString(accordions));
    }
}
