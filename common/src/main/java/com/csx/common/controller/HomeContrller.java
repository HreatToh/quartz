package com.csx.common.controller;

import com.csx.base.controller.BaseController;
import com.csx.common.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( name = "初始化首页信息的 HomeContrller" , value = "home")
public class HomeContrller extends BaseController {

    @Autowired
    private HomeService homeService;

    /**
     * @method getHomeInfo
     * @params  
     * @return Object
     * @desc   获取首页信息
     **/
    @RequestMapping( name = "获取首页信息" , value = "getHomeInfo" , method = RequestMethod.POST )
    public Object getHomeInfo(){
        return homeService.getHomeInfo();
    }
}
