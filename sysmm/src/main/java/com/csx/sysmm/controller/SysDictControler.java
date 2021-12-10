package com.csx.sysmm.controller;

import com.csx.base.controller.BaseController;
import com.csx.common.service.SysDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author  Chengshx
 * @create  2021/12/10 16:44
 * @desc    字典接口
 **/
@Slf4j
@RestController
@RequestMapping( name = "获取字典接口" , value = "/dict")
public class SysDictControler extends BaseController {

    @Autowired
    private SysDictService sysDictService;
    
    /**
     * @method  getDict
     * @params  @RequestParam Map<String , Object> params
     * @return  Object
     * @desc    获取字典信息的接口 提供前台缓存使用
     **/
    @RequestMapping( name = "获取字典信息的接口 提供前台缓存使用" , value = "/getDict" , method = RequestMethod.POST )
    public Object getDict(@RequestParam Map<String , Object> params){
        return sysDictService.getDictMap(params);
    }
}
