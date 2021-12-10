package com.csx.common.controller;

import com.csx.base.controller.BaseController;
import com.csx.common.other.ResultBody;
import com.csx.common.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RestController
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService ;

    /**
     * @method  login
     * @params  @RequestParam Map<String,Object> params , ModelMap modelMap , HttpServletRequest request , HttpServletResponse response
     * @return  ResultBody
     * @desc    登录接口
     **/
    @RequestMapping( name = "/login 登录验证接口" , value = "/login"  )
    public ResultBody login(@RequestParam Map<String,Object> params , ModelMap modelMap , HttpServletRequest request , HttpServletResponse response){
        return loginService.login(params , modelMap , request , response);
    }
    /**
     * @method  logout
     * @params  @RequestParam Map<String,Object> params , ModelMap modelMap , HttpServletRequest request , HttpServletResponse response
     * @return  ResultBody
     * @desc    登出接口
     **/
    @RequestMapping( name = "/logout 登出接口需要清除Session等信息" , value = "/logout" )
    public ResultBody logout(@RequestParam Map<String,Object> params , ModelMap modelMap , HttpServletRequest request , HttpServletResponse response){
        return loginService.logout(params , modelMap , request , response);
    }

}
