package com.bmn.haitang.demo.pig.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/14
 */
@Configuration
public class WebConfig {

    @Bean
    public EmbeddedVelocityViewResolver velocityViewResolver(VelocityProperties properties) {
        EmbeddedVelocityViewResolver resolver = new EmbeddedVelocityViewResolver();
        properties.applyToViewResolver(resolver);
        resolver.setRedirectHttp10Compatible(false);
        return resolver;
    }
}
