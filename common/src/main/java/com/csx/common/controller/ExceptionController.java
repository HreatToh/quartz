package com.csx.common.controller;

import cn.hutool.core.lang.Assert;
import com.csx.base.controller.BaseController;
import com.csx.common.utils.ToolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author  Chengshx
 * @create  2021/12/5
 * @desc    ExceptionController 统一处理异常接口
 **/
@Controller
public class ExceptionController extends BaseController implements ErrorController {
    /** 错误属性    */
    private ErrorAttributes errorAttributes;
    @Autowired
    private ServerProperties serverProperties;

    /**
     * 初始化构造方法
     * @param errorAttributes
     */
    public ExceptionController(ErrorAttributes errorAttributes){
        Assert.notNull(errorAttributes , "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }
    /**
     * 定义404的ModelAndView
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(name = "404 错误处理" , produces = "text/html",value = "/error/404")
    public ModelAndView errorHtml404(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(getStatus(request).value());
        Map<String, Object> model = getErrorAttributes(request,  getErrorAttributeOptions(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/404", model);
    }

    /**
     * 定义500的ModelAndView
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(name = "500 错误处理" , produces = "text/html",value = "/error/500")
    public ModelAndView errorHtml500(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(getStatus(request).value());
        Map<String, Object> model = getErrorAttributes(request,  getErrorAttributeOptions(request, MediaType.TEXT_HTML));
        return new ModelAndView("error/500", model);
    }

    /**
     * 定义所有异常的错误JSON信息
     * @param request
     * @return
     */
    @RequestMapping(name = "异常处理 返回JSON处理" , value = "/error" )
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error500(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,  getErrorAttributeOptions(request, MediaType.APPLICATION_JSON));
        HttpStatus status = getStatus(request);
        return new ResponseEntity<Map<String, Object>>(body, status);
    }

    /**
     * 获取错误的信息
     * @param request
     * @param options
     * @return
     */
    private Map<String, Object> getErrorAttributes(HttpServletRequest request, ErrorAttributeOptions options) {
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return this.errorAttributes.getErrorAttributes((WebRequest) requestAttributes, options);
    }

    /**
     * 获取错误选项
     * @param request
     * @param type
     * @return
     */
    private ErrorAttributeOptions getErrorAttributeOptions(HttpServletRequest request, MediaType type) {
        return ErrorAttributeOptions.of(ErrorAttributeOptions.Include.values());
    }

    /**
     * 获取错误编码
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (ToolUtils.isNull(statusCode)) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
