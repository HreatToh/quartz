package com.csx.common.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import com.csx.base.controller.BaseController;
import com.csx.common.config.AppCofig;
import com.csx.common.other.Constants;
import com.csx.common.utils.ToolUtils;
import com.csx.licence.entity.Licence;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/page")
public class PageController extends BaseController {

    /**
     * @method  login
     * @params
     * @return  String
     * @desc    去往登录页的方法
     **/
    @GetMapping( name = "登录页" , value = "/login")
    public String login(ModelMap modelMap){
        modelMap.addAttribute("kaptcha" , Constants.App.KAPTCHA_CODE_KEY);
        modelMap.addAttribute("login_title" , AppCofig.getSysConfig(Constants.App.SYS_LOGIN_PAGE_TITLE , "欢迎登录！"));
        return "/login";
    }

    /**
     * @method  index
     * @params  
     * @return  String
     * @desc    去往首页的方法
     **/
    @GetMapping( name = "首页" , value = "/index")
    public String index(HttpServletRequest request , HttpServletResponse response , ModelMap modelMap){
        String licenceJson = ToolUtils.nvl(request.getAttribute(Constants.App.SYS_LICENCE) , Constants.App.NONE);
        if (ToolUtils.equalsIgnoreCase(Constants.App.NONE , licenceJson)){
            modelMap.addAttribute("isLicence" , false);
        }else{
            modelMap.addAttribute("isLicence" , true);
            modelMap.addAttribute("licence" , Licence.decode(licenceJson));
        }
        modelMap.addAttribute("home_url" , AppCofig.getSysConfig(Constants.App.SYS_HOME_PAGE_URL ,  "page/home" ));
        modelMap.addAttribute("side_icon" , AppCofig.getSysConfig(Constants.App.SYS_HOME_SIDE_ICON , "/static/images/logo.png"));
        modelMap.addAttribute("side_title" , AppCofig.getSysConfig(Constants.App.SYS_HOME_SIDE_TITLE , "Quartz "));
        modelMap.addAttribute(Constants.App.HEADER_AUTHORIZATION , request.getSession().getAttribute(Constants.App.HEADER_AUTHORIZATION));
        return "/index";
    }

}
