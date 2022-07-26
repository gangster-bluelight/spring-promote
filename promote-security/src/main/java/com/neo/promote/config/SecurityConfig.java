package com.neo.promote.config;

import com.neo.promote.lang.Constant;
import com.neo.promote.security.CompoundAuthenticationFilter;
import com.neo.promote.security.JwtAccessDeniedHandler;
import com.neo.promote.security.JwtAuthenticationEntryPoint;
import com.neo.promote.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description:
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Resource
    private AuthService authService;

    @Resource
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Resource
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Resource
    private CompoundAuthenticationFilter compoundAuthenticationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors()
                .and()
                .csrf().disable()
                // 禁用session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 配置拦截规则
                .authorizeRequests()
                // 放行白名单请求
                .antMatchers(Constant.WHITE_STR).permitAll()
                .anyRequest().authenticated()
                .and()
                // 异常处理器
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                // 配置自定义的过滤器
                .addFilterBefore(compoundAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //省略HttpSecurity的配置
        httpSecurity.userDetailsService(authService);
        return httpSecurity.build();
    }
}
