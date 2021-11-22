package com.csx.common.utils;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author  Chengshx
 * @create  2021/11/18 16:21
 * @desc    操作数据库工具类
 **/
@Slf4j
public class JdbcUtils {

    /** 获取 JdbcTemplate用来操作数据库   */
    private static JdbcTemplate jdbcTemplate;
    /** 获取 Druid连接池    */
    private static DruidDataSource dataSource;


    /**
     * 设置连接池方法
     * @param druidDataSource
     */
    public static void  setDataSource( DruidDataSource druidDataSource ){
        dataSource = druidDataSource;
        if (ToolUtils.isNotNull(dataSource)){
            initJdbcTemplate(dataSource);
        }
    }
    /**
     * @method  initJdbcTemplate
     * @params  初始化连接池
     * @return
     * @desc    初始化连接池
     **/
    private static void initJdbcTemplate(DruidDataSource dataSource) {
        try{
            jdbcTemplate = new JdbcTemplate(dataSource);
        }catch (Exception e) {
            log.error("初始化 jdbcTemplate 失败！" , e);
        }
    }
}
