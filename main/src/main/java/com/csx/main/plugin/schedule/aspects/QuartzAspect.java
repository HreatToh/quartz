package com.csx.main.plugin.schedule.aspects;

import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class QuartzAspect {

    @Pointcut("execution( * com.csx.main.plugin.schedule.jobs.RegisterJobBean.*(..))")
    public void log(){}

    @Before(value = "log()")
    public void before(){
        StringBuilder format = new StringBuilder();
        format.append("*******************************************").append("   ");
        format.append(ToolUtils.format("[{}] Quartz 任务开始 start." , ToolUtils.now()));
        format.append("   ").append("*******************************************");
        log.info(format.toString());
        log.info(ToolUtils.format("IP地址：{}", ToolUtils.localhost()));
        log.info(ToolUtils.format("切点：{}", "com.github.chengshx.quartz.schedule.jobs.RegisterJobBean.*(..)"));
        log.info("Quartz start.");
        format.delete(0,format.length());
    }

    @AfterReturning(value = "log()")
    public void after(){
        StringBuilder format = new StringBuilder();
        format.append("*******************************************").append("   ");
        format.append(ToolUtils.format("[{}] Quartz 任务结束 end." , ToolUtils.now()));
        format.append("   ").append("*******************************************");
        log.info(format.toString());
        format.delete(0,format.length());
    }
}
