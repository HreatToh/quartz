package com.csx.common.service;

import com.csx.base.service.BaseService;
import com.csx.common.config.AppCofig;
import com.csx.common.entity.SysMenu;
import com.csx.common.entity.SysSystem;
import com.csx.common.other.Constants;
import com.csx.common.utils.ToolUtils;
import com.csx.licence.entity.Licence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author  Chengshx
 * @create  2021/12/4
 * @desc    初始化首页信息的 HomeService
 **/
@Slf4j
@Service
public class HomeService extends BaseService {

    @Autowired
    private CacheService cacheService;
    /**
     * @method  getHomeInfo
     * @params  
     * @return  Object
     * @desc    获取首页信息的方法
     **/
    public Object getHomeInfo(HttpServletRequest request) {
        log.info("******************************** 开始初始化首页信息 开始 ********************************");
        Map<String , Object> options = new HashMap<String , Object>();
        log.info("初始化用户信息 开始...");
        options.put("userInfo" , ToolUtils.getUser());
        log.info("初始化用户信息 结束。");
        log.info("初始化首页信息 开始...");
        options.put("homeInfo", initHomeInfo(request));
        log.info("初始化首页信息 结束。");
        log.info("初始化子系统信息 开始...");
        options.put("subSystemInfo", initSubSystemInfo());
        log.info("初始化子系统信息 结束。");
        log.info("******************************** 开始初始化首页信息 结束 ********************************");
        return options;
    }

    /**
     * @method  initSubSystemInfo
     * @params
     * @return  Object
     * @desc    初始化系统信息
     **/
    private Object initSubSystemInfo() {
        List<SysSystem> sysSystemList = cacheService.getSysSystemByUserId(ToolUtils.getUserId());
        List<SysMenu> sysMenuList = cacheService.getSysMenuByUserId(ToolUtils.getUserId());
        List<Map<String , Object>> list = new ArrayList<Map<String , Object>>();
        Map<String , Object> subSystemInfoOptions = null;
        for (SysSystem sysSystem: sysSystemList) {
            subSystemInfoOptions = sysSystem.map();
            subSystemInfoOptions.put("menus" , initSysMenuInfo(sysSystem.getSysId() , sysMenuList ));
            list.add(subSystemInfoOptions);
        }
        return list;
    }


    /**
     * @method  initSysMenuInfo
     * @params  String sys_id , List<SysMenu> sysMenuList
     * @return  Object
     * @desc    初始化菜单对象
     **/
    private Object initSysMenuInfo(String sys_id , List<SysMenu> sysMenuList){
        List<Map<String , Object>> list = new ArrayList<Map<String , Object>>();
        Map<String , Object> sysMenuOptions = null;
        for (SysMenu sysMenu:  sysMenuList) {
            if (ToolUtils.isBlank(sysMenu.getMenuParentId()) ) {
                if (ToolUtils.equals(sys_id , sysMenu.getSysId())) {
                    sysMenuOptions = sysMenu.map();
                    sysMenuOptions.put("menuChildren" , getMenuChildren(sys_id , sysMenu.getMenuId() , sysMenuList ));
                    list.add(sysMenuOptions);
                }
            }
        }
        return list;
    }

    /**
     * @method  getMenuChildren
     * @params  String sys_id, String menuId, List<SysMenu> sysMenuList
     * @return  Object
     * @desc    获取菜单的孩子
     **/
    private Object getMenuChildren(String sys_id, String menuId, List<SysMenu> sysMenuList) {
        List<Map<String , Object>> list = new ArrayList<Map<String , Object>>();
        Map<String , Object> sysMenuOptions = null;
        for (SysMenu sysMenu: sysMenuList) {
            if (ToolUtils.equals(sys_id , sysMenu.getSysId()) && ToolUtils.equals(menuId , sysMenu.getMenuParentId())) {
                sysMenuOptions = sysMenu.map();
                sysMenuOptions.put("menuChildren" , getMenuChildren(sys_id , sysMenu.getMenuId() , sysMenuList ));
                list.add(sysMenuOptions);
            }
        }
        return list;
    }

    /**
     * @method  initHomeInfo
     * @params  HttpServletRequest request
     * @return  Object
     * @desc    初始化首页信息
     **/
    private Object initHomeInfo(HttpServletRequest request) {
        Map<String , Object> homeInfoOptions = new HashMap<String , Object>();
        HttpSession session = request.getSession();
        /** 存入token    */
        homeInfoOptions.put("token" , session.getAttribute(Constants.Session.SESSION_TOKEN_KEY));
        /** 存入环境信息    */
        homeInfoOptions.put("environment" , AppCofig.evmentEnum.val());
        /** 存入页标题    */
        homeInfoOptions.put("homeName" , "首页");
        /** 存入页地址    */
        homeInfoOptions.put("homeUrl" , AppCofig.getSysConfig(Constants.App.SYS_HOME_PAGE_URL ,  "page/home" ));
        /** 存入窗口名称    */
        homeInfoOptions.put("AppName" , AppCofig.getSysConfig(Constants.App.SYS_HOME_PAGE_TITLE , "管理系统"));
        /** 左侧菜单信息    */
        homeInfoOptions.put("homeSideIcon", AppCofig.getSysConfig(Constants.App.SYS_HOME_SIDE_ICON , "/static/images/logo.png"));
        homeInfoOptions.put("homeSideName", AppCofig.getSysConfig(Constants.App.SYS_HOME_SIDE_TITLE , "Quartz"));
        /** 存入许可证信息    */
        String licenceInfo = ToolUtils.nvl(ToolUtils.getLicence() , Constants.App.NONE);
        if (ToolUtils.equalsIgnoreCase(Constants.App.NONE , licenceInfo)){
            homeInfoOptions.put("hasLicence" , false);
            licenceInfo = "检测到系统未授权，请授权！";
        }else{
            StringBuffer sb = new StringBuffer();
            homeInfoOptions.put("hasLicence" , true);
            Licence licence = Licence.decode(licenceInfo);
            licenceInfo = licence.Json();
        }
        homeInfoOptions.put("licenceInfo" , licenceInfo);
        return homeInfoOptions;
    }
}
