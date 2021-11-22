package com.csx.main.plugin.owner.config;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.druid.support.http.WebStatFilter;
import com.csx.main.plugin.owner.filter.LoginFilter;
import com.csx.main.plugin.owner.interceptor.LoginInterceptor;
import com.csx.main.plugin.owner.listener.LoginListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
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

    /**
     * 初始化环境信息
     */
    @Value("spring.profiles.active")
    private String activeProfiles;

    String[] URL_PATTERNS = { "/*" };
    String[] EXCLUSIONS = { "/static/*" , "/druid/*" };
    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /***************************** 添加登录拦截器 start *****************************/
        /** 初始化不被拦截的URL */
        String[] LOGIN_EXCLUDEPATHPATTERNS = new String[]{  "/static/*" , "*.js" };
        /** 初始化被拦截的URL */
        String[] LOGIN_INCLUDEPATHPATTERNS = new String[]{  "/**" };
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns(LOGIN_INCLUDEPATHPATTERNS).excludePathPatterns(LOGIN_EXCLUDEPATHPATTERNS);
        log.debug("注册拦截器[" + LoginInterceptor.class + "]成功");
        /***************************** 添加登录拦截器 end   *****************************/
    }


    /**
     * 添加登录过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean loginFilter() {
        /***************************** 添加登录过滤器 start *****************************/
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.addUrlPatterns(URL_PATTERNS);
        log.debug("注册过滤器[ loginFilter ]成功");
        /***************************** 添加登录过滤器 end   *****************************/
        return filterRegistrationBean;
    }

    /**
     * 添加Druid过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean druidFilter() {
        /***************************** 添加Druid过滤器 start *****************************/
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        Map<String , String> initParams = new HashMap<String , String >();
        initParams.put("exclusions" , ArrayUtil.join(EXCLUSIONS , ","));
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.addUrlPatterns(URL_PATTERNS);
        log.debug("注册过滤器[ druidFilter ]成功");
        /***************************** 添加Druid过滤器 end   *****************************/
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
