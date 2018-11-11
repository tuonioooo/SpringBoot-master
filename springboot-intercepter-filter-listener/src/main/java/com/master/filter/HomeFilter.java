package com.master.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by daizhao.
 * User: tony
 * Date: 2018-11-8
 * Time: 14:07
 * info:
 */
@Component
@WebFilter(urlPatterns = "/home/*", filterName = "home")
public class HomeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("HomeFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("HomeFilter doFilter");
    }

    @Override
    public void destroy() {
        System.out.println("HomeFilter destory");
    }
}
