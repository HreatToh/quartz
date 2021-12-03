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
@RequestMapping(name = "登录接口" , value = "/login")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService ;

    /**
     * @method  login
     * @params  @RequestParam Map<String,Object> params , ModelMap modelMap , HttpServletRequest request , HttpServletResponse response
     * @return  ResultBody
     * @desc    登录接口
     **/
    @RequestMapping( name = "登录验证接口" )
    public ResultBody login(@RequestParam Map<String,Object> params , ModelMap modelMap , HttpServletRequest request , HttpServletResponse response){
        return loginService.login(params , modelMap , request , response);
    }
    /**
     * @method  index
     * @params  []
     * @return java.lang.String
     * @desc
     **/
    @RequestMapping( name = "index" , value = "/index" , method = RequestMethod.POST )
    public String index(){
        return "index";
    }
}
