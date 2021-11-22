package com.csx.main.plugin.beetl.config;

import cn.hutool.http.ContentType;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.nio.charset.Charset;

@Slf4j
@Configuration
public class BeetlGroupConfig {

    /** 模版根路径 */
    @Value("${beetl.templatesPath}")
    private String templatesPath;
    /** 配置文件路径 */
    @Value("${beetl.configsPath}")
    private String configPath;
    /** 页面路径 */
    @Value("${beetl.suffix}")
    private String pageSuffix;
    /**
     * 初始化beetl模版引擎
     * @return
     */
    @Bean( initMethod = "init" , name = "beetlConfig" )
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration(){
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = null;
        ClasspathResourceLoader classpathResourceLoader = null;
        ClassLoader classLoader = null;
        try {
            beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
            //获取ClassLoader
            classLoader  = Thread.currentThread().getContextClassLoader();
            if (ToolUtils.isNull(classLoader)){
                classLoader = BeetlGroupConfig.class.getClassLoader();
            }
            beetlGroupUtilConfiguration.setConfigProperties(PropertiesLoaderUtils.loadAllProperties(configPath));
            classpathResourceLoader = new ClasspathResourceLoader(classLoader , templatesPath);
            beetlGroupUtilConfiguration.setResourceLoader(classpathResourceLoader);
            beetlGroupUtilConfiguration.init();
            beetlGroupUtilConfiguration.getGroupTemplate().setClassLoader(classLoader);
        }catch (Exception e){
            log.error("初始化beetl引擎异常！" , e);
        }
        return beetlGroupUtilConfiguration;
    }

    @Bean(name = "beetlViewResolver")
    public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier( "beetlConfig" ) BeetlGroupUtilConfiguration beetlGroupUtilConfiguration){
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType(ContentType.build("text/html" , Charset.forName("utf-8")));
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        beetlSpringViewResolver.setSuffix("." + pageSuffix);
        return beetlSpringViewResolver;
    }
}
