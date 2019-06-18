package com.bmn.haitang.demo.pig.spring.config;

import com.jfinal.template.ext.spring.JFinalViewResolver;
import com.jfinal.template.source.ClassPathSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/18
 */
@Configuration
public class EnjoyConfig {

    @Bean(name = "jfinalViewResolver")
    public JFinalViewResolver getJFinalViewResolver() {
        JFinalViewResolver resolver = new JFinalViewResolver();
        resolver.setDevMode(true);
        resolver.setSessionInView(true);
        resolver.setSourceFactory(new ClassPathSourceFactory());
        JFinalViewResolver.engine.setBaseTemplatePath("/templates/");

        resolver.setExposeRequestAttributes(true);
        resolver.setSuffix(".html");
        resolver.setOrder(1);
        resolver.setContentType("text/html; charset=utf-8");

        resolver.setRequestContextAttribute("ctx");

        return resolver;
    }
}
