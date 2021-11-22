package com.csx.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import com.csx.common.entity.Constants;
import com.csx.common.enums.EvmentEnum;

public class ToolUtils extends StrUtil{

    /** 是否是管理员    */
    private static Boolean isAdmin = false;
    /** 是否开发环境    */
    private static Boolean isDev = false;
    /** 是否生产环境    */
    private static Boolean isProd = false;
    /** 是否测试环境    */
    private static Boolean isTest = false;



    /**
     * 初始化Admin参数
     */
    public static void initAdmin(String userId){
        isAdmin = equalsAnyIgnoreCase(userId , Constants.ADMIN);
    }

    /**
     * 初始化环境信息
     * @param avtive
     */
    public static void initEvment(String avtive){
        isDev = equalsAnyIgnoreCase(avtive , EvmentEnum.DEV.key());
        isProd = equalsAnyIgnoreCase(avtive , EvmentEnum.PROD.key());
        isTest = equalsAnyIgnoreCase(avtive , EvmentEnum.TEST.key());
    }

    /**
     * 获取环境信息
     * @return
     */
    public static EvmentEnum getEvment(){
        if (isDev){
            return EvmentEnum.DEV;
        }
        if (isProd){
            return EvmentEnum.PROD;
        }
        if (isTest){
            return EvmentEnum.TEST;
        }
        return null;
    }
    /**
     * 获取当前日期
     * 格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String now(){
        return DateUtil.now();
    }

    /**
     * 获取当前日期
     * 格式 yyyy-MM-dd
     * @return
     */
    public static String today(){
        return DateUtil.today();
    }

    /**
     * 获取本机ip地址
     * @return
     */
    public static String localhost(){
        return NetUtil.getLocalhostStr();
    }

    /**
     * 判断是否为NULL
     * @return
     */
    public static Boolean isNull( Object o ){
        return o == null ;
    }
    /**
     * 判断是否不为NULL
     * @return
     */
    public static Boolean isNotNull( Object o ){
        return !isNull(o);
    }

    /**
     * 如果那么语法
     * @param itemVal
     * @param itemDefval
     * @return
     */
    public static String nvl(String itemVal, String itemDefval) {
        return nvl(itemVal , itemDefval , true);
    }

    /**
     * 如果那么语法
     * @param itemVal
     * @param itemDefval
     * @param trim true : 返回空串 false : 返回null
     * @return
     */
    public static String nvl(String itemVal, String itemDefval , Boolean trim){
        String val = isBlank(itemVal) ? itemDefval : itemVal;
        return isBlank(val) && trim ? "" : val;
    }

}
