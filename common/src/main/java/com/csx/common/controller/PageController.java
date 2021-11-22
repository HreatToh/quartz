package com.csx.common.controller;

import com.csx.common.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("page")
public class PageController extends BaseController {

    /**
     * @method  login
     * @params
     * @return  String
     * @desc    去往登录页的方法
     **/
    @GetMapping( name = "登录页" , value = "login")
    public String login(){
        return "/login";
    }

    /**
     * @method  index
     * @params  
     * @return  String
     * @desc    去往首页的方法
     **/
    @GetMapping( name = "首页" , value = "index")
    public String index(){
        return "/index";
    }
}
