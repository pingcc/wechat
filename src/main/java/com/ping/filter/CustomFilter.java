package com.ping.filter;

import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * Created  on 2019/2/19.
 *
 * @author CPing
 * Email yy_cping@163.com
 * edit ideaIU
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "customFilter")
//这里的“/*” 表示的是需要拦截的请求路径
public class CustomFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        httpResponse.addHeader("Access-Control-Max-Age", "1728000");
        httpResponse.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8020");
        filterChain.doFilter(servletRequest, httpResponse);
    }

    @Override
    public void destroy() {
    }

}
