package com.csx.common.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import com.csx.base.controller.BaseController;
import com.csx.common.config.AppCofig;
import com.csx.common.other.Constants;
import com.csx.common.other.JsonMap;
import com.csx.common.utils.ToolUtils;
import com.csx.licence.entity.Licence;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
    public String login(HttpServletRequest request , HttpServletResponse response , ModelMap modelMap){
        modelMap.addAttribute("kaptcha" , Constants.App.KAPTCHA_CODE_KEY);
        modelMap.addAttribute("login_title" , AppCofig.getSysConfig(Constants.App.SYS_LOGIN_PAGE_TITLE , "欢迎登录！"));
        modelMap.addAttribute("isTimeOut" , ToolUtils.isNull(request.getSession().getAttribute(Constants.Session.SESSION_TOKEN_KEY)));
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
        /** 初始化Customer配置    */
        initCustomerSetting(modelMap);
        return "/index";
    }

    /**
     * @method  initCustomerSetting
     * @params  ModelMap modelMap
     * @return  
     * @desc    初始化Customer 配置
     **/
    private void initCustomerSetting(ModelMap modelMap) {
        JsonMap<String , Object> options = JsonMap.newInstance(false);
        options.append("iniUrl",AppCofig.getSysConfig(Constants.App.SYS_HOME_OPTIONS_INIURL , ""));
        options.append("licenceUrl",AppCofig.getSysConfig(Constants.App.SYS_HOME_OPTIONS_LICENCEURL , ""));
        options.append("clearUrl",AppCofig.getSysConfig(Constants.App.SYS_HOME_OPTIONS_CLEARURL , ""));
        options.append("urlHashLocation",AppCofig.getSysConfig(Constants.App.SYS_HOME_OPTIONS_URLHASHLOCATION , true));
        options.append("bgColorDefault",AppCofig.getSysConfig(Constants.App.SYS_HOME_OPTIONS_BGCOLORDEFAULT , false));
        options.append("multiModule",AppCofig.getSysConfig(Constants.App.SYS_HOME_OPTIONS_MULTIMODULE , true));
        options.append("menuChildOpen",AppCofig.getSysConfig(Constants.App.SYS_HOME_OPTIONS_MENUCHILDOPEN , false));
        options.append("loadingTime",AppCofig.getSysConfig(Constants.App.SYS_HOME_OPTIONS_LOADINGTIME , 1));
        options.append("pageAnim",AppCofig.getSysConfig(Constants.App.SYS_HOME_OPTIONS_PAGEANIM , true));
        options.append("maxTabNum",AppCofig.getSysConfig(Constants.App.SYS_HOME_OPTIONS_MAXTABNUM , 20));
        modelMap.addAttribute("customerOptions" , options);
    }

}
