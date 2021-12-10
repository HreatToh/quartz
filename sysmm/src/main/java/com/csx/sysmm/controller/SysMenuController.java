package com.csx.sysmm.controller;

import com.csx.base.controller.BaseController;
import com.csx.common.other.ResultBody;
import com.csx.common.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author  Chengshx
 * @create  2021/11/22 17:17
 * @desc    SysMenuController 菜单管理
 **/
@RestController
@RequestMapping( name = "菜单管理" ,value = "/sysmm/menu" )
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;



    /**
     * @method  saveMenuInfo
     * @params  @RequestParam Map<String ,Object> params
     * @return  ResultBody
     * @desc    保存菜单信息
     **/
    @RequestMapping( name = "保存菜单信息" , value = "saveMenuInfo" , method = RequestMethod.POST )
    public ResultBody saveMenuInfo(@RequestParam Map<String ,Object> params){
        return sysMenuService.saveMenuInfo(params);
    }

    /**
     * @method getMenuList
     * @params @RequestParam Map<String ,Object> params
     * @return ResultBody
     * @desc   获取菜单信息
     **/
    @RequestMapping( name = "获取菜单列表 根据权限" , value = "getMenuList" , method = RequestMethod.POST)
    public ResultBody getMenuList(@RequestParam Map<String ,Object> params){
        return sysMenuService.getMenuList(params);
    }
}
