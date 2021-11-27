package com.csx.sysmm.controller;

import com.csx.base.controller.BaseController;
import com.csx.common.entity.ResultBody;
import com.csx.sysmm.service.SysMenuService;
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
    private SysMenuService menuService;


    /**
     * @method getMenuInfo
     * @params @RequestParam Map<String ,Object> params
     * @return ResultBody
     * @desc   获取菜单信息
     **/
    @RequestMapping( name = "获取菜单信息" , value = "getMenuInfo" , method = RequestMethod.POST )
    public ResultBody getMenuInfo(@RequestParam Map<String ,Object> params){
        return menuService.getMenuInfo(params);
    }
    
    /**
     * @method  saveMenuInfo
     * @params  @RequestParam Map<String ,Object> params
     * @return  ResultBody
     * @desc    保存菜单信息
     **/
    @RequestMapping( name = "保存菜单信息" , value = "saveMenuInfo" , method = RequestMethod.POST )
    public ResultBody saveMenuInfo(@RequestParam Map<String ,Object> params){
        return menuService.saveMenuInfo(params);
    }

}
