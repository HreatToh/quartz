package com.csx.common.utils;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

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

    /**
     * @method  getJdbcTemplate
     * @params  
     * @return  JdbcTemplate
     * @desc    获取 getJdbcTemplate 对象
     **/
    public static JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }
    
    public static <T> T execute(ConnectionCallback<T> connectionCallback) throws DataAccessException {
        return jdbcTemplate.execute(connectionCallback);
    }

    public static <T> T execute(StatementCallback<T> statementCallback) throws DataAccessException {
        return jdbcTemplate.execute(statementCallback);
    }

    public static void execute(String sql) throws DataAccessException {
        jdbcTemplate.execute(sql);
    }

    public static <T> T query(String sql, ResultSetExtractor<T> resultSetExtractor) throws DataAccessException {
        return jdbcTemplate.query(sql , resultSetExtractor);
    }

    public static void query(String sql, RowCallbackHandler rowCallbackHandler) throws DataAccessException {
        jdbcTemplate.query(sql, rowCallbackHandler);
    }

    public static <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.query(sql , rowMapper);
    }

    public static <T> Stream<T> queryForStream(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.queryForStream(sql , rowMapper);
    }

    public static <T> T queryForObject(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.queryForObject(sql , rowMapper);
    }

    public static <T> T queryForObject(String sql, Class<T> requiredType) throws DataAccessException {
        return jdbcTemplate.queryForObject(sql , requiredType);
    }

    public static Map<String, Object> queryForMap(String sql) throws DataAccessException {
        return jdbcTemplate.queryForMap(sql);
    }

    public static <T> List<T> queryForList(String sql, Class<T> elementType) throws DataAccessException {
        return jdbcTemplate.queryForList( sql , elementType );
    }

    public static List<Map<String, Object>> queryForList(String sql) throws DataAccessException {
        return jdbcTemplate.queryForList(sql);
    }

    public static SqlRowSet queryForRowSet(String sql) throws DataAccessException {
        return jdbcTemplate.queryForRowSet(sql);
    }

    public static int update(String sql) throws DataAccessException {
        return jdbcTemplate.update(sql);
    }

    public static int update(String sql , Object... args) throws DataAccessException {
        return jdbcTemplate.update(sql , args);
    }

    public static int[] batchUpdate(String... sqls) throws DataAccessException {
        return jdbcTemplate.batchUpdate(sqls);
    }

    public static <T> T execute(PreparedStatementCreator preparedStatementCreator, PreparedStatementCallback<T> preparedStatementCallback) throws DataAccessException {
        return jdbcTemplate.execute(preparedStatementCreator , preparedStatementCallback);
    }

    public static <T> T execute(String sql, PreparedStatementCallback<T> preparedStatementCallback) throws DataAccessException {
        return jdbcTemplate.execute(sql , preparedStatementCallback);
    }

    public static <T> T query(PreparedStatementCreator preparedStatementCreator, ResultSetExtractor<T> resultSetExtractor) throws DataAccessException {
        return jdbcTemplate.query(preparedStatementCreator , resultSetExtractor);
    }

    public static <T> T query(String sql, PreparedStatementSetter preparedStatementSetter, ResultSetExtractor<T> resultSetExtractor) throws DataAccessException {
        return jdbcTemplate.query(sql , preparedStatementSetter , resultSetExtractor);
    }


    public static <T> T query(String sql, Object[] args, int[] argTypes, ResultSetExtractor<T> resultSetExtractor) throws DataAccessException {
        return jdbcTemplate.query(sql , args , argTypes , resultSetExtractor );
    }

    @Deprecated
    public static <T> T query(String sql, Object[] args, ResultSetExtractor<T> resultSetExtractor) throws DataAccessException {
        return jdbcTemplate.query(sql , args , resultSetExtractor);
    }

    public static <T> T query(String sql, ResultSetExtractor<T> resultSetExtractor, Object... args) throws DataAccessException {
        return jdbcTemplate.query(sql , resultSetExtractor , args);
    }

}
