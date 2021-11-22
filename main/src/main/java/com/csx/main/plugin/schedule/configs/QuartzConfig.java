package com.csx.main.plugin.schedule.configs;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.csx.main.plugin.schedule.jobs.RegisterJobBean;
import org.quartz.*;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail getJobDetail(){
        return JobBuilder.newJob(RegisterJobBean.class).storeDurably().build();
    }

    @Bean
    public Trigger getTrigger(){
//        SimpleScheduleBuilder schedule = SimpleScheduleBuilder.simpleSchedule();
        /** 设置每5分钟执行一次*/
//        schedule.withIntervalInMinutes(5);
//        schedule.withIntervalInHours(1);
        /** 永久重复，一直执行下去*/
//        schedule.repeatForever();
//        return TriggerBuilder.newTrigger().forJob(getJobDetail()).withSchedule(schedule).build();
        return null;
    }

}
