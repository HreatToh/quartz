package com.csx.sysmm.controller;

import com.csx.base.controller.BaseController;
import com.csx.base.service.BaseService;
import com.csx.common.utils.SpringUtils;
import com.csx.common.utils.ToolUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("page")
public class SysPageController extends BaseController {

    /**
     * @method  gotoPage
     * @params  Map<String , Object> params , ModelMap modelMap , HttpServletRequest request , HttpServletResponse response
     * 例如：go/sysmm/menu/index  => sysmm/menu/index
     * @return  String
     * @desc    返回页面
     **/
    @GetMapping( name = "菜单反射页面" , value = "/{sysId}/{menu}/{mode}" )
    public String gotoPage(@PathVariable String sysId , @PathVariable String menu , @PathVariable String mode , @RequestParam Map<String , Object> params , ModelMap modelMap , HttpServletRequest request , HttpServletResponse response){
        String serviceName = ToolUtils.format("sysMenuService" , menu);
        BaseService service = SpringUtils.getBean(serviceName);
        return service.initPage(sysId , menu , mode , params , modelMap , request , response);
    }
}
