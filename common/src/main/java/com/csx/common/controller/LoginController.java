package com.csx.common.controller;

import com.csx.common.base.controller.BaseController;
import com.csx.common.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(name = "登录接口" , value = "/login")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService ;

    /**
     * @method  index
     * @params  []
     * @return java.lang.String
     * @desc
     **/
    @RequestMapping( name = "index" , value = "index" , method = RequestMethod.POST )
    public String index(){
        return "index";
    }
}
