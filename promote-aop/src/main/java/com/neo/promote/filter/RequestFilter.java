package com.neo.promote.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description: 过滤器
 */
public class RequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("Execute cost="+(System.currentTimeMillis()-start));
    }

    @Override
    public void destroy() {

    }
}
