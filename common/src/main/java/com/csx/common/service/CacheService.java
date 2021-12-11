package com.csx.common.service;


import com.csx.base.service.BaseService;
import com.csx.common.entity.SysConfig;
import com.csx.common.entity.SysDict;
import com.csx.common.entity.SysMenu;
import com.csx.common.entity.SysSystem;
import com.csx.common.handler.CacheHandler;
import com.csx.common.mapper.SysConfigMapper;
import com.csx.common.mapper.SysPermissionMapper;
import com.csx.common.other.Constants;
import com.csx.common.other.JsonMap;
import com.csx.common.other.Permission;
import com.csx.common.utils.CacheUtils;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private SysSystemService sysSystemService;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysMenuService sysMenuService;

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
     * @return  List<SysConfig>
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
     * @return  List<SysConfig>
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
     * @method  getSysSystemAll
     * @params  
     * @return  List<SysSystem>
     * @desc    获取所有系统信息
     **/
    public List<SysSystem> getSysSystemAll() {
        return (List<SysSystem>) CacheUtils.get(Constants.Cache.CACHE_SYS_SYSTEM, new CacheHandler() {
            @Override
            public Object handler() throws Exception {
                List<SysSystem> list = sysSystemService.getSysSystemAll();
                if (ToolUtils.isNull(list) ){
                    return new ArrayList<SysSystem>();
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
        return (List<SysMenu>) CacheUtils.get(Constants.Cache.CACHE_CURRENT_SYS_MENU + "." + userId, new CacheHandler() {
            @Override
            public Object handler() throws Exception {
                List<SysMenu> list = sysPermissionMapper.getSysMenuByUserId(userId);
                if (ToolUtils.isNull(list) ){
                    return new ArrayList<SysMenu>();
                }
                return list;
            }
        });
    }

    /**
     * @method  getSysMenuByUserId
     * @params  String userId
     * @return  List<SysMenu>
     * @desc    获取所有的菜单
     **/
    private List<SysMenu> getSysMenuAll() {
        return (List<SysMenu>) CacheUtils.get(Constants.Cache.CACHE_SYS_MENU , new CacheHandler() {
            @Override
            public Object handler() throws Exception {
                List<SysMenu> list = sysMenuService.getSysMenuAll();
                if (ToolUtils.isNull(list) ){
                    return new ArrayList<SysMenu>();
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
        return (List<SysSystem>) CacheUtils.get(Constants.Cache.CACHE_CURRENT_SYS_SYSTEM + "." + userId, new CacheHandler() {
            @Override
            public Object handler() throws Exception {
                List<SysMenu> list = sysPermissionMapper.getSysSystemByUserId(userId);
                if (ToolUtils.isNull(list) ){
                    return new ArrayList<SysMenu>();
                }
                return list;
            }
        });
    }


    /**
     * @method  getDictMap
     * @params
     * @return  Map
     * @desc    缓存获取字典Map
     **/
    public Map getDictMap() {
        return (Map) CacheUtils.get(Constants.Cache.CACHE_SYS_DICT_MAP, new CacheHandler() {
            @Override
            public Object handler() throws Exception {
                JsonMap jsonMap = JsonMap.newInstance();
                getSystemDictMap(jsonMap , getSysSystemAll());
                getSysMenuTypeMap(jsonMap);
                getSysMenuMap(jsonMap , getSysMenuAll());
                return jsonMap.get();
            }
        });
    }


    /**
     * 把所有的菜单信息转换成Map
     */
    private void getSysMenuMap(JsonMap jsonMap , List<SysMenu> menuList){
        for (SysMenu sysMenu: menuList) {
            jsonMap.append("SYSMM.MENU_NAME." + sysMenu.getMenuId() , sysMenu.getMenuName());
        }
    }
    /**
     * @method  getSysMenuTypeMap
     * @params  JsonMap jsonMap
     * @return
     * @desc    转换菜单类型成字典
     **/
    private void getSysMenuTypeMap(JsonMap jsonMap) {
        Map<String, SysDict> map = new HashMap<String, SysDict>();
        map.putAll(Permission.Type.map);
        for (Map.Entry<String , SysDict> entry: map.entrySet()) {
            jsonMap.append("SYSMM.MENU_TYPE." + entry.getKey() , entry.getValue().getDictName());
        }
        map.clear();
        map.putAll(Permission.resourceType.map);
        for (Map.Entry<String , SysDict> entry: map.entrySet()) {
            jsonMap.append("SYSMM.RESOURCE_TYPE." + entry.getKey() , entry.getValue().getDictName());
        }
    }


    /**
     * @method  getSystemDictMap
     * @params  JsonMap jsonMap , List<SysSystem> list
     * @return
     * @desc    系统信息转map
     **/
    private void getSystemDictMap(JsonMap jsonMap , List<SysSystem> list){
        for (SysSystem system: list) {
            jsonMap.append("SYSMM.SYSTEM_NAME." + system.getSysId() , system.getSysName());
        }
    }


    /**
     * @method  clear
     * @params  
     * @return  
     * @desc    清理缓存
     **/
    public void clear() {
        
    }



}
