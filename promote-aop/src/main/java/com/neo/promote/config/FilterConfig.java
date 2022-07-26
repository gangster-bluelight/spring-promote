package com.neo.promote.config;

import com.neo.promote.filter.RequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description:
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<RequestFilter> registerFilter() {
        FilterRegistrationBean<RequestFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RequestFilter());
        registration.addUrlPatterns("/*");
        registration.setName("LogCostFilter");
        registration.setOrder(1);
        return registration;
    }
}
