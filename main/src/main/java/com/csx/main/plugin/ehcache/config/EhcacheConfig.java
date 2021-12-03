package com.csx.main.plugin.ehcache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author  Chengshx
 * @create  2021/11/19 11:26
 * @desc    初始化缓存配置类
 **/
@Configuration
public class EhcacheConfig {
    /** ehcache 配置文件路径    */
    @Value("${spring.cache.ehcache.config}")
    private String ehcacheConfigPath;
    
    /**
     * @method  getEhCacheManagerFactoryBean
     * @params  
     * @return  EhCacheManagerFactoryBean
     * @desc    实例化EhCacheManagerFactoryBean对象
     **/
    @Bean
    public EhCacheManagerFactoryBean getEhCacheManagerFactoryBean(){
        EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
        bean.setConfigLocation(new ClassPathResource(ehcacheConfigPath));
        bean.setShared(true);
        return bean;
    }

    /**
     * @method getEhCacheCacheManager
     * @params  
     * @return  EhCacheCacheManager
     * @desc    实例化 EhCacheCacheManager 对象
     **/
    @Bean
    public EhCacheCacheManager getEhCacheCacheManager(@Autowired EhCacheManagerFactoryBean bean) {
        return new EhCacheCacheManager(bean.getObject());
    }
}
