package com.csx.main.plugin.owner.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    /**
     * 过滤器初始化
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 开始过滤
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest hrequest = (HttpServletRequest)servletRequest;
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
//        if(hrequest.getRequestURI().indexOf("/index") != -1 ||
//                hrequest.getRequestURI().indexOf("/asd") != -1 ||
//                hrequest.getRequestURI().indexOf("/online") != -1 ||
//                hrequest.getRequestURI().indexOf("/login") != -1
//        ) {
        filterChain.doFilter(servletRequest, servletResponse);
//        }else {
//            wrapper.sendRedirect("/login");
//        }
    }

    /**
     * 销毁
     */
    @Override
    public void destroy() {

    }
}
