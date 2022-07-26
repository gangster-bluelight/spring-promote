package com.neo.promote.config;

import com.neo.promote.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description:
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private RequestInterceptor interceptor;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
         * addInterceptor 注册拦截器
         * addPathPatterns 配置拦截规则
         */
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }
}
