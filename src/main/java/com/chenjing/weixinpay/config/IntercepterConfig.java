package com.chenjing.weixinpay.config;

import com.chenjing.weixinpay.intercepter.LoginIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * description：IntercepterConfig
 *
 * @author:chenjing
 * @version:1.0
 * @time:15:27
 */

/*
*拦截器配置
* */
@Configuration
public class IntercepterConfig implements WebMvcConfigurer{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        registry.addInterceptor(new LoginIntercepter()).addPathPatterns("/user/api/v1/*/**");

        WebMvcConfigurer.super.addInterceptors(registry);

    }
}
