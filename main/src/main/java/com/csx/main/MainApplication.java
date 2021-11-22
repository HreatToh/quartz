package com.csx.main;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@EnableAspectJAutoProxy
@MapperScan("com.csx.*.mapper")
@ComponentScan( basePackages={ "com.csx" , "cn.hutool.extra.spring"})
@Import(com.csx.common.utils.SpringUtils.class)
@SpringBootApplication( exclude = SecurityAutoConfiguration.class )
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
