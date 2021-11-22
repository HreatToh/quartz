package com.csx.main;

import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.csx.common.config.AppCofig;
import com.csx.common.service.CacheService;
import com.csx.common.service.LoginService;
import com.csx.common.utils.JdbcUtils;
import com.csx.common.utils.SpringUtils;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author  Chengshx
 * @create  2021/11/18 18:50
 * @desc    程序启动完成时 初始化数据以及缓存
 **/

@Slf4j
@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    @Autowired
    private DruidDataSource druidDataSource ;

    @Autowired
    private Environment environment;

    @Autowired
    private CacheService cacheService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long startTime = DateUtil.current();
        log.info(ToolUtils.format("------------------------------[{}]服务已启动 , 开始初始化... ------------------------------" , DateUtil.now()));
        initJdbcUtils();
        initToolUtils();
        initCache();
        long endTime = DateUtil.current();
        log.info(ToolUtils.format("------------------------------[{}]服务已启动 , 初始化完成！   ------------------------------" , DateUtil.now()));
        log.info(ToolUtils.format("总共用时：{} 毫秒" , endTime - startTime ));
    }

    /**
     * 初始化 jdbc 工具类
     */
    private void initJdbcUtils() {
        JdbcUtils.setDataSource(druidDataSource);
        log.info("[初始化] JdbcUtils 初始化完成！");
    }

    /**
     * 初始化缓存
     */
    private void initCache() {
        cacheService.initCache();
        log.info("[初始化] Cache缓存 初始化完成！");
    }

    /**
     * 初始化 tool 工具类
     */
    private void initToolUtils() {
        /*************** 初始化环境信息 开始 ***************/
        String profilesActive = environment.getProperty("spring.profiles.active");
        ToolUtils.initEvment(profilesActive);
        AppCofig.evmentEnum = ToolUtils.getEvment();
        /*************** 初始化环境信息 结束 ***************/
        log.info("[初始化] ToolUtils 初始化完成！");
    }
}
