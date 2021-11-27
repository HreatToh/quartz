package com.csx.main.plugin.kaptcha.config;

import com.csx.common.utils.ToolUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author  Chengshx
 * @create  2021/11/27 20:15
 * @desc    验证码的配置类
 **/
@Slf4j
@Configuration
public class KaptchaConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${kaptcha.config}")
    private String kaptchaConfigPath;

    @Bean("kaptcha_config")
    public Properties getConfig(){
        Properties properties = null ;
        try {
            properties = PropertiesLoaderUtils.loadAllProperties(kaptchaConfigPath);
        } catch (IOException e) {
            log.error(ToolUtils.format("[{}]加载配置文件异常！" , kaptchaConfigPath) , e);
        }
        return properties;
    }
    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        DefaultKaptcha captchaProducer = new DefaultKaptcha();
        Properties properties = (Properties) applicationContext.getBean("kaptcha_config");
        Config config = new Config(properties);
        captchaProducer.setConfig(config);
        return captchaProducer;
    }
}
