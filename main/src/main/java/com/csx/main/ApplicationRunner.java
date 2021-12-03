package com.csx.main;

import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.csx.common.config.AppCofig;
import com.csx.common.entity.SysConfig;
import com.csx.common.service.CacheService;
import com.csx.common.utils.CacheUtils;
import com.csx.common.utils.JdbcUtils;
import com.csx.common.utils.ToolUtils;
import com.csx.common.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

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
    @Autowired
    private SysConfigService sysConfigService;


    /** ehcache 缓存配置路径    */
    @Value("${spring.cache.ehcache.config}")
    private String EHCACHE_CONF_PATH;
    /** ehcache 本地缓存名称    */
    @Value("${spring.cache.ehcache.localname}")
    private String LOCAL_CACHE_NAME;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long startTime = DateUtil.current();
        log.info(ToolUtils.format("------------------------------[{}]服务已启动 , 开始初始化... ------------------------------" , DateUtil.now()));
        initCacheUtils();
        initJdbcUtils();
        initToolUtils();
        initAppCofig();
        initCache();
        long endTime = DateUtil.current();
        log.info(ToolUtils.format("------------------------------[{}]服务已启动 , 初始化完成！   ------------------------------" , DateUtil.now()));
        log.info(ToolUtils.format("总共用时：{} 毫秒" , endTime - startTime ));
    }

    /**
     * 初始化Cache 工具类
     */
    private void initCacheUtils() {
        /*************** 初始化缓存 开始 ***************/
        CacheUtils.EHCACHE_CONF_PATH = EHCACHE_CONF_PATH;
        CacheUtils.LOCAL_CACHE_NAME = LOCAL_CACHE_NAME;
        InputStream is = null;
        CacheManager cacheManager = null;
        try{
            is = CacheUtils.class.getResourceAsStream(EHCACHE_CONF_PATH);
            cacheManager = CacheManager.create(is);
            CacheUtils.initCacheManager(cacheManager);
        } catch (Exception e){
            log.error(ToolUtils.format("初始化缓存异常！" ) , e);
        } finally {
            ToolUtils.closeIO(is);
        }
        /*************** 初始化缓存 结束 ***************/
    }


    /**
     * 初始化 jdbc 工具类
     */
    private void initJdbcUtils() {
        /*************** 初始化连接池 开始 ***************/
        JdbcUtils.setDataSource(druidDataSource);
        log.info("[初始化] JdbcUtils 初始化完成！");
        /*************** 初始化连接池 结束 ***************/
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

    /**
     * 初始化Application 配置
     */
    private void initAppCofig() {
        /*************** 初始化系统配置 开始 ***************/
        List<SysConfig> sysConfigAll = sysConfigService.getSysConfigAll();
        List<SysConfig> sysSubConfigAll = sysConfigService.getSysSubConfigAll();
        AppCofig.init(sysConfigAll , sysSubConfigAll);
        /*************** 初始化系统配置 结束 ***************/
    }

    /**
     * 初始化缓存
     */
    private void initCache() {

        cacheService.initCache();
        log.info("[初始化] Cache缓存 初始化完成！");
    }


}
