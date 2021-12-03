package com.csx.main;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** 开启异步    */
@EnableAsync(proxyTargetClass = true)
/** 开启缓存    */
@EnableCaching(proxyTargetClass = true)
/** 开启aop    */
@EnableAspectJAutoProxy
@MapperScan("com.csx.*.mapper")
@ComponentScan( basePackages={ "com.csx" , "cn.hutool.extra.spring"})
@Import(com.csx.common.utils.SpringUtils.class)
/** 开启事务支持    */
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication( exclude = SecurityAutoConfiguration.class )
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
