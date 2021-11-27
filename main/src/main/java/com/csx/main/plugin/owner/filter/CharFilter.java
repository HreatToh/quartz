package com.csx.main.plugin.owner.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author  Chengshx
 * @create  2021/11/23 10:09
 * @desc    CharFilter 字符过滤器
 **/
@Slf4j
public class CharFilter implements Filter {
    
    /**
     * @method  init
     * @params  
     * @return  
     * @desc    过滤器初始化
     **/
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * @method  doFilter
     * @params  ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain
     * @return  
     * @desc    开始过滤
     **/
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request , response);
    }

    /**
     * @method  destroy
     * @params  
     * @return  
     * @desc    过滤器销毁
     **/
    @Override
    public void destroy() {

    }
}
