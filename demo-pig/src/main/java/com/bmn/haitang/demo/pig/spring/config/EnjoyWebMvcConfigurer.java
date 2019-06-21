package com.bmn.haitang.demo.pig.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/18
 */

@Configuration
public class EnjoyWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/images/**").addResourceLocations("classpath:/static/images/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/zoo");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
