package com.csx.sysmm.controller;

import com.csx.common.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author  Chengshx
 * @create  2021/11/30 10:21
 * @desc    系统配置接口
 **/
@Controller
@RequestMapping(name = "系统配置接口" , value = "/sysmm/config")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

}
