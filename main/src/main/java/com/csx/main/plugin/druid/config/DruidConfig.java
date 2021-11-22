package com.csx.main.plugin.druid.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.csx.common.utils.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {



    /**
     * @method  getDruidDataSource
     * @params  
     * @return  DruidDataSource
     * @desc    初始化连接池
     **/
    @Bean
    @QuartzDataSource
    @ConfigurationProperties( prefix = "spring.datasource" )
    public DruidDataSource getDruidDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * @method  statViewServlet
     * @params
     * @return  ServletRegistrationBean
     * @desc    配置Druid 监控管理后天的Servlet
     *          内置Servlet 容器没有web.xml文件。所以使用StringBoot的方式注册Servlet
     **/
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean( new StatViewServlet() , "/druid/*");
        Map<String , String > initParams = new HashMap<String , String>();
        initParams.put("loginUsername" , "admin");
        initParams.put("loginPassword" , "admin");
        /** 默认允许所有访问    */
        initParams.put("allow" , "");
        /** deny：Druid 后台拒绝访问，表示禁止ip访问    */
//        initParams.put("deny" , "192.168.10.10");
        bean.setInitParameters(initParams);
        return bean;
    }
}
