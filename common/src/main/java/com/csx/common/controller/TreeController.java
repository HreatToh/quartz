package com.csx.common.controller;

import com.csx.base.controller.BaseController;
import com.csx.common.service.TreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author  Chengshx
 * @create  2021/12/9 15:34
 * @desc    树形数据接口
 **/
@Slf4j
@RestController
@RequestMapping(name = "树形数据接口" , value = "/tree")
public class TreeController extends BaseController {
    @Autowired
    private TreeService treeService;

    /**
     * @method  getTreeMenu
     * @params  @RequestParam Map<String , Object> params
     * @return  Object
     * @desc    获取菜单树形结构
     **/
    @RequestMapping( name = "获取菜单树形结构" , value = "/menu" )
    public Object getTreeMenu(@RequestParam Map<String , Object> params){
        return treeService.getTreeMenu(params);
    }
}
