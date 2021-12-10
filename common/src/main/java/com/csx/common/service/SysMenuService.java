package com.csx.common.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csx.base.service.BaseService;
import com.csx.common.other.Constants;
import com.csx.common.other.Permission;
import com.csx.common.other.ResultBody;
import com.csx.common.entity.SysMenu;
import com.csx.common.mapper.SysMenuMapper;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author  Chengshx
 * @create  2021/11/22 17:18
 * @desc    SysMenuService 菜单管理服务层
 **/
@Slf4j
@Service
@Scope( proxyMode = ScopedProxyMode.TARGET_CLASS )
public class SysMenuService extends BaseService {

    @Autowired
    private SysMenuMapper menuMapper;


    /**
     * @method  saveMenuInfo
     * @params  Map<String, Object> params
     * @return  ResultBody
     * @desc    保存菜单信息
     **/
    @Transactional
    public ResultBody saveMenuInfo(Map<String, Object> params) {
        SysMenu sysMenu = null;
        String msg = "";
        try{
            sysMenu = new SysMenu();
            sysMenu.setMenuId(ToolUtils.nvl(params.get("menuId") , ""));
            sysMenu = menuMapper.selectById(sysMenu);
            if (ToolUtils.isNotNull(sysMenu)){
                msg = ToolUtils.format("菜单ID与菜单[{}]冲突请修改！" , sysMenu.getMenuId() + "-" + sysMenu.getMenuName());
                log.warn(msg);
                return ResultBody.success(SUCCESS , msg);
            }
            sysMenu = new SysMenu();
            sysMenu.setSysId(ToolUtils.nvl(params.get("sysId") , ""));
            sysMenu.setMenuId(ToolUtils.nvl(params.get("menuId") , ""));
            sysMenu.setMenuName(ToolUtils.nvl(params.get("menuName") , ""));
            sysMenu.setMenuIcon(ToolUtils.nvl(params.get("menuIcon") , ""));
            sysMenu.setMenuClass(ToolUtils.nvl(params.get("menuClass") , ""));
            sysMenu.setMenuOrder(ToolUtils.nvl(params.get("menuOrder") , ""));
            sysMenu.setMenuParentId(ToolUtils.nvl(params.get("menuParentId") , ""));
            sysMenu.setMenuPath(ToolUtils.nvl(params.get("menuPath") , ""));
            sysMenu.setMenuTarget(ToolUtils.nvl(params.get("menuTarget") , ""));
            sysMenu.setMenuUrl(ToolUtils.nvl(params.get("menuUrl") , ""));
            sysMenu.setMenuType(ToolUtils.nvl(params.get("menuType") , "_self"));
            sysMenu.setCommCdate(DateUtil.today());
            sysMenu.setCommCuser(Constants.Session.ADMIN);
            sysMenu.setCommDelfalg(Constants.App.DELFALG_N);
            sysMenu.setCommCdate(DateUtil.today());
            sysMenu.setCommUuser(Constants.Session.ADMIN);
            msg = menuMapper.insert(sysMenu) != 1 ? "菜单保存失败，详情请看日志！" : "菜单保存成功！";
        } catch (Exception e){
            log.error(ToolUtils.format("保存菜单信息异常！" , e.getMessage() ) , e);
            return ResultBody.error(e);
        }
        return ResultBody.success(SUCCESS , msg);
    }
    /**
     * @method  getMenuInfo
     * @params  Map<String, Object> params
     * @return  ResultBody
     * @desc    获取菜单信息
     **/
    public ResultBody getMenuList(Map<String, Object> params) {
        Page<SysMenu> page = null;
        try{
            if (!ToolUtils.isAdmin()){
                params = initPermission(params , Permission.Type.user , Permission.resourceType.build(Permission.resourceType.menu , Permission.resourceType.menuBtn ));
            }
            page = menuMapper.selectPage(getPage(params), getWhereQueryWrapper(params));
        } catch (Exception e){
            log.error(ToolUtils.format("菜单分页查询发生异常！" ) , e);
            return ResultBody.error(e);
        }
        return ResultBody.success(page);
    }
}
