package com.csx.common.service;


import com.csx.base.service.BaseService;
import com.csx.common.entity.SysConfig;
import com.csx.common.entity.SysMenu;
import com.csx.common.entity.SysSystem;
import com.csx.common.handler.CacheHandler;
import com.csx.common.mapper.SysConfigMapper;
import com.csx.common.mapper.SysMenuMapper;
import com.csx.common.mapper.SysPermissionMapper;
import com.csx.common.other.Constants;
import com.csx.common.utils.CacheUtils;
import com.csx.common.utils.JdbcUtils;
import com.csx.common.utils.SpringUtils;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Chengshx
 * @create  2021/11/19 12:18
 * @desc    CacheService 缓存服务
 **/
@Slf4j
@Service
public class CacheService extends BaseService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    /**
     * 初始化缓存信息
     */
    public void initCache() {
        Method[] methods = this.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            String methodName = methods[i].getName();
            if (ToolUtils.startWith(methodName , "get") && !ToolUtils.equals(methodName , "getClass") && methods[i].getParameterCount() == 0){
                try {
                    methods[i].invoke(this);
                    log.info(ToolUtils.format("{}.{} 该方法执行成功，初始化缓存成功！" , this.getClass().getName() , methodName));
                } catch (IllegalAccessException e) {
                    log.error(ToolUtils.format("{}.{} 该方法无访问权限，初始化该缓存失败！" , this.getClass().getName() , methodName) , e);
                } catch (InvocationTargetException e) {
                    log.error(ToolUtils.format("{}.{} 该方法执行期间发生异常，初始化该缓存失败！" , this.getClass().getName() , methodName) , e);
                }
            }
        }
    }


    /**
     * @method  getSysConfigAll
     * @params
     * @return
     * @desc    获取所有的系统配置信息
     **/
    public List<SysConfig> getSysConfigAll(){
        return (List<SysConfig>) CacheUtils.get( Constants.Cache.CACHE_SYS_CONFIG , new CacheHandler() {
            @Override
            public Object handler() throws Exception {
                List<SysConfig> list = sysConfigMapper.getSysConfigAll();
                if (ToolUtils.isNull(list) ){
                    return new ArrayList<SysConfig>();
                }
                return list;
            }
        });
    }
    /**
     * @method  getSysConfigAll
     * @params
     * @return
     * @desc    获取所有的子系统配置信息
     **/
    public List<SysConfig> getSysSubConfigAll() {
        return (List<SysConfig>) CacheUtils.get( Constants.Cache.CACHE_SYS_SUBCONFIG , new CacheHandler() {
            @Override
            public Object handler() throws Exception {
                List<SysConfig> list = sysConfigMapper.getSysSubConfigAll();
                if (ToolUtils.isNull(list) ){
                    return new ArrayList<SysConfig>();
                }
                return list;
            }
        });
    }

    /**
     * @method  getSysMenuByUserId
     * @params  String userId
     * @return  List<SysMenu>
     * @desc    获取用户的菜单
     **/
    public List<SysMenu> getSysMenuByUserId(String userId){
        return (List<SysMenu>) CacheUtils.get(Constants.Cache.CACHE_CURRENT_SYS_MENU, new CacheHandler() {
            @Override
            public Object handler() throws Exception {
                List<SysMenu> list = sysPermissionMapper.getSysMenuByUserId(userId);
                if (ToolUtils.isNull(list) ){
                    return new ArrayList<SysConfig>();
                }
                return list;
            }
        });
    }

    /**
     * @method  getSysSystemByUserId
     * @params  String userId
     * @return  List<SysSystem>
     * @desc    获取用户的系统信息
     **/
    public List<SysSystem> getSysSystemByUserId(String userId){
        return (List<SysSystem>) CacheUtils.get(Constants.Cache.CACHE_CURRENT_SYS_SYSTEM, new CacheHandler() {
            @Override
            public Object handler() throws Exception {
                List<SysMenu> list = sysPermissionMapper.getSysSystemByUserId(userId);
                if (ToolUtils.isNull(list) ){
                    return new ArrayList<SysConfig>();
                }
                return list;
            }
        });
    }
}
