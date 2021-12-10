package com.csx.common.utils;

import com.csx.common.entity.SysUser;
import lombok.extern.slf4j.Slf4j;

/**
 * @author  Chengshx
 * @create  2021/12/9 10:31
 * @desc    工具类操作ThreadLocal（存放，获取，删除用户信息）
 **/
@Slf4j
public class ThreadLocalUtils {

    /**
     * 存放用户
     */
    private static final ThreadLocal<SysUser> userLocal = new ThreadLocal<SysUser>();

    /**
     * 添加当前登录用户方法  在拦截器方法执行前调用设置获取用户
     * @param user
     */
    public static void initUser(SysUser user){
        userLocal.set(user);
    }

    /**
     * 获取当前登录用户方法
     */
    public static SysUser getUser(){
        return userLocal.get();
    }


    /**
     * 删除当前登录用户方法  在拦截器方法执行后 移除当前用户对象
     */
    public static void removeUser(){
        userLocal.remove();
    }
}
