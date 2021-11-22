package com.csx.main.plugin.owner.interceptor;

import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 初始化基本参数
     * @throws FileNotFoundException
     */
    private void initParams(HttpServletRequest request) throws FileNotFoundException {
        String basePath = ResourceUtils.getURL("classpath:").getPath();
        String ip = ToolUtils.localhost();
        String url = request.getRequestURI();
        log.info(ToolUtils.format("请求url : {}" , url ));
        log.info(ToolUtils.format("服务器ip : {}" , ip ));
        log.info(ToolUtils.format("基础路径 : {}" , basePath ));
    }
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {

            //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
//            User user=(User)request.getSession().getAttribute("USER");
//            if(user!=null){
//                return true;
//            }
//            response.sendRedirect(request.getContextPath()+"你的登陆页地址");
            log.debug(ToolUtils.format("请求地址：{}" ,  request.getRequestURI()));
        } catch (Exception e) {
        }
        return true;//如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
        //如果设置为true时，请求将会继续执行后面的操作
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
