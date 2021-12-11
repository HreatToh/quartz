package com.csx.common.utils;

import com.csx.common.config.AppCofig;
import com.csx.common.handler.CacheHandler;
import com.csx.common.other.Constants;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  Chengshx
 * @create  2021/12/9 12:06
 * @desc    在线工具类
 **/
@Slf4j
public class OnlineUtils {

    /** 最大的在线人数    */
    private final static Integer MAX_ONLINE_NUM = AppCofig.getSysConfig(Constants.App.SYS_LOGIN_MAX_ONLINE_NUM , 10);
    /** 是否允许多用户登录    */
    private final static Boolean ONLINE_ISMULTI = ToolUtils.isY(AppCofig.getSysConfig(Constants.App.SYS_LOGIN_ISMULTI , "Y"));
    /** 缓存键值前缀 在线数    */
    private final static String PREFIX_NUM = "ONLINE_NUM";
    /** 缓存键值前缀 SessionId    */
    private final static String PREFIX_SESSION = "ONLINE_SESSION";
    /**
     * 是否超过最大在线人数
     * @return
     */
    public synchronized static Boolean isExceed( String userId ){
        Integer onlineNum = getOnlineNum(userId);
        if (!ONLINE_ISMULTI){
            return onlineNum >= 1;
        }
        return onlineNum >= MAX_ONLINE_NUM;
    }

    /**
     * 获取当前用户在线数
     * @param userId
     * @return
     */
    public synchronized static Integer getOnlineNum(String userId){
        String key = ToolUtils.format("{}.{}" ,PREFIX_NUM , userId);
        return (Integer) CacheUtils.get(key, new CacheHandler() {
            @Override
            public Object handler() throws Exception {
                return 0;
            }
        });
    }

    /**
     * 获取当前用户在线数
     * @param userId
     * @return
     */
    public synchronized static void setOnlineNum(String userId , Integer onlineNum){
        String key = ToolUtils.format("{}.{}" ,PREFIX_NUM , userId);
        CacheUtils.put(key , onlineNum);
    }

    /**
     * 设置用户信息
     * @param userId
     * @param sessionId
     */
    public synchronized static void set(String userId , String sessionId){
        get(userId , sessionId);
    };
    /**
     * 获取用户信息
     * @param userId
     * @param sessionId
     * @return
     */
    public synchronized static Object get(String userId , String sessionId){
        String key = ToolUtils.format("{}.{}.{}" ,PREFIX_SESSION , userId , sessionId);
        return CacheUtils.get(key, new CacheHandler() {
            @Override
            public Object handler() throws Exception {
                Map<String , String > map = new HashMap<String , String>();
                map.put("host" , ToolUtils.localhost());
                map.put("time" , ToolUtils.nowTime());
                OnlineUtils.setOnlineNum(userId ,OnlineUtils.getOnlineNum(userId) + 1);
                log.info(ToolUtils.format("用户：{} , SessionId: {} , 当前时间：{} 登录成功！" , userId , sessionId , ToolUtils.nowTime()));
                return map;
            }
        });
    }

    /**
     * 下线
     * @param userId
     * @param sessionId
     */
    public synchronized static void remove(String userId , String sessionId){

        if (ToolUtils.isNull(userId) || ToolUtils.isNull(sessionId)){
            return;
        }

        String numKey = ToolUtils.format("{}.{}" ,PREFIX_NUM , userId);
        String sessionKey = ToolUtils.format("{}.{}.{}" ,PREFIX_SESSION , userId , sessionId);
        CacheUtils.remove(sessionKey);
        Integer num = (Integer) CacheUtils.get(numKey);
        if (ToolUtils.isNotNull(num)){
            if (num == 0) {
                CacheUtils.remove(numKey);
            }else{
                CacheUtils.put(numKey , num - 1 );
            }
        }
        log.info(ToolUtils.format("用户：{} , SessionId: {} , 当前时间：{} 退出登录！" , userId , sessionId , ToolUtils.nowTime()));
    }
}
