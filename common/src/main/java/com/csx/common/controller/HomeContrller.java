package com.csx.common.controller;

import com.csx.base.controller.BaseController;
import com.csx.common.service.HomeService;
import com.csx.licence.entity.Licence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author  Chengshx
 * @create  2021/12/4
 * @desc    初始化首页信息的 HomeContrller
 **/
@RestController
@RequestMapping( name = "初始化首页信息的 HomeContrller" , value = "/home")
public class HomeContrller extends BaseController {

    @Autowired
    private HomeService homeService;

    /**
     * @method getHomeInfo
     * @params  
     * @return Object
     * @desc   获取首页信息
     **/
    @RequestMapping( name = "获取首页信息" , value = "/getHomeInfo" , method = RequestMethod.POST )
    public Object getHomeInfo(HttpServletRequest request ){
        return homeService.getHomeInfo(request);
    }

    /**
     * @method clearCache
     * @params @RequestParam Map<String , Object > params
     * @return Object
     * @desc   清理缓存信息
     **/
    @RequestMapping( name = "清理缓存信息" , value = "/clearCache" , method = RequestMethod.POST )
    public Object clearCache(@RequestParam Map<String , Object > params){
        return homeService.clearCache(params);
    }

    /**
     * @method hasLicence
     * @params
     * @return Object
     * @desc   认证系统接口
     **/
    @RequestMapping( name = "认证系统接口" , value = "/hasLicence" , method = RequestMethod.POST )
    public Object hasLicence(HttpServletRequest request){
        return homeService.hasLicence(request);
    }
    /**
     * @method authLicence
     * @params
     * @return Object
     * @desc   验证认证系统接口
     **/
    @RequestMapping( name = "验证认证系统接口" , value = "/authLicence" , method = RequestMethod.POST )
    public Object authLicence(HttpServletRequest request , @RequestParam("licenceCode") String licenceCode){
        return homeService.authLicence(request , licenceCode);
    }
}
