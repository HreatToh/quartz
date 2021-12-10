package com.csx.main.plugin.owner.config;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.druid.support.http.WebStatFilter;
import com.csx.common.other.PathPatterns;
import com.csx.main.plugin.owner.filter.CharFilter;
import com.csx.main.plugin.owner.filter.LoginFilter;
import com.csx.main.plugin.owner.filter.ParamFilter;
import com.csx.main.plugin.owner.interceptor.AuthorizationInterceptor;
import com.csx.main.plugin.owner.listener.LoginListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class GlobalConfig implements WebMvcConfigurer {

    @Autowired
    private ApplicationContext applicationContext;

    /** 登录拦截器 拦截对象    */
    @ConfigurationProperties( prefix = "application.interceptor.authorizationinterceptor")
    @Bean("loginIt")
    public PathPatterns getLoginIt() {
        return new PathPatterns();
    }

    /** 登录拦截器 拦截对象    */
    @ConfigurationProperties( prefix = "application.filter.loginfilter")
    @Bean("loginFt")
    public PathPatterns getLoginFt() {
        return new PathPatterns();
    }
    /** 字符过滤器 拦截对象    */
    @ConfigurationProperties( prefix = "application.filter.charfilter")
    @Bean("charFt")
    public PathPatterns getCharFt() {
        return new PathPatterns();
    }
    /** 连接池过滤器 拦截对象    */
    @ConfigurationProperties( prefix = "application.filter.druidfilter")
    @Bean("druidFt")
    public PathPatterns getDruidFt() {
        return new PathPatterns();
    }
    /** 参数过滤器 拦截对象    */
    @ConfigurationProperties( prefix = "application.filter.paramfilter")
    @Bean("paramFt")
    public PathPatterns getParamFt() {
        return new PathPatterns();
    }

    /**
     * @method  getPathPatterns
     * @params  String name
     * @return  PathPatterns
     * @desc    获取拦截路径
     **/
    private PathPatterns getPathPatterns(String name){
        return (PathPatterns) applicationContext.getBean(name);
    }
    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /***************************** 添加登录拦截器 start *****************************/
        PathPatterns loginIt = getPathPatterns("loginIt");
        /** 初始化不被拦截的URL */
//        String[] LOGIN_EXCLUDEPATHPATTERNS = new String[]{  "/static/*" , "*.js" };
        /** 初始化被拦截的URL */
//        String[] LOGIN_INCLUDEPATHPATTERNS = new String[]{  "/**" };
        registry.addInterceptor(new AuthorizationInterceptor()).addPathPatterns(loginIt.getInclude()).excludePathPatterns(loginIt.getExclude());
        log.debug("注册拦截器[" + AuthorizationInterceptor.class + "]成功");
        /***************************** 添加登录拦截器 end   *****************************/
    }


    /**
     * 添加登录过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean loginFilter() {
        /***************************** 添加登录过滤器 start *****************************/
        PathPatterns loginFt = getPathPatterns("loginFt");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        Map<String , String> initParams = new HashMap<String , String >();
        initParams.put("exclusions" , ArrayUtil.join(loginFt.getExclude() , ","));
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.addUrlPatterns(loginFt.getInclude());
        log.debug("注册过滤器[ loginFilter ]成功");
        /***************************** 添加登录过滤器 end   *****************************/
        return filterRegistrationBean;
    }

    /**
     * 添加登录过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean charFilter() {
        /***************************** 添加字符过滤器 start *****************************/
        PathPatterns charFt = getPathPatterns("charFt");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        Map<String , String> initParams = new HashMap<String , String >();
        initParams.put("exclusions" , ArrayUtil.join(charFt.getExclude() , ","));
        filterRegistrationBean.setFilter(new CharFilter());
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.addUrlPatterns(charFt.getInclude());
        log.debug("注册过滤器[ charFilter ]成功");
        /***************************** 添加字符过滤器 end   *****************************/
        return filterRegistrationBean;
    }

    /**
     * 添加Druid过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean druidFilter() {
        /***************************** 添加Druid过滤器 start *****************************/
        PathPatterns druidFt = getPathPatterns("druidFt");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        Map<String , String> initParams = new HashMap<String , String >();
        initParams.put("exclusions" , ArrayUtil.join(druidFt.getExclude() , ","));
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.addUrlPatterns(druidFt.getInclude());
        log.debug("注册过滤器[ druidFilter ]成功");
        /***************************** 添加Druid过滤器 end   *****************************/
        return filterRegistrationBean;
    }

    /**
     * 添加参数过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean paramFilter() {
        /***************************** 添加参数过滤器 start *****************************/
        PathPatterns paramFt = getPathPatterns("paramFt");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        Map<String , String> initParams = new HashMap<String , String >();
        initParams.put("exclusions" , ArrayUtil.join(paramFt.getExclude() , ","));
        filterRegistrationBean.setFilter(new ParamFilter());
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.addUrlPatterns(paramFt.getInclude());
        log.debug("注册过滤器[ paramFilter ]成功");
        /***************************** 添加参数过滤器 end   *****************************/
        return filterRegistrationBean;
    }
    /**
     * 添加监听器
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean loginListener() {
        /***************************** 添加登录监听器 start *****************************/
        ServletListenerRegistrationBean listenerRegistrationBean = new ServletListenerRegistrationBean();
        listenerRegistrationBean.setListener(new LoginListener());
        log.debug("注册监听器[ loginListener ]成功");
        /***************************** 添加登录监听器 end *****************************/
        return listenerRegistrationBean;
    }


    /**
     * 添加默认页面
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("page/login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    /**
     * 添加静态文件映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
