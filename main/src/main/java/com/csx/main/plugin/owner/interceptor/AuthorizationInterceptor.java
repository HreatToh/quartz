package com.csx.main.plugin.owner.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.csx.common.entity.SysUser;
import com.csx.common.other.Constants;
import com.csx.common.utils.ThreadLocalUtils;
import com.csx.common.utils.ToolUtils;
import com.csx.licence.entity.Licence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        try {
            HttpSession session = request.getSession();
            String token = request.getHeader(Constants.App.HEADER_AUTHORIZATION);
            SysUser user = (SysUser) session.getAttribute(Constants.Session.SESSION_USER_KEY);
            String uri = request.getRequestURI();
            String licence = ToolUtils.getLicence();

            /** 如果请求的token为空则从seesion中获取    */
            if (ToolUtils.isBlank(token)){
                token = (String) session.getAttribute(Constants.Session.SESSION_TOKEN_KEY);
            }

            /** 如果token为空 则返回登录页    */
            if (ToolUtils.isBlank(token) ){
                log.warn("token不能为空，返回登录页！");
                return gotoLoginPage(request , response);
            }

            //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
            if(ToolUtils.isNull(user)){
                log.warn("session已经过期，返回登录页！");
                return gotoLoginPage(request , response);
            }

            try {
                /** 验证token是否合法    */
                JWTVerifier jwt = JWT.require(Algorithm.HMAC256(licence)).build();
                jwt.verify(token);
            } catch (JWTVerificationException e) {
                log.error("token验证不通过，返回登录页！" , e);
                return gotoLoginPage(request , response);
            }


            try{
                /** 获取token信息 存储在Request域中    */
                if (!ToolUtils.equalsIgnoreCase(licence , Constants.App.$NONE)){
                    licence =  Licence.decodeJson(licence);
                }
            } catch (Exception e){
                log.error(ToolUtils.format("[{}]解析License异常！" , ToolUtils.nowTime() ) , e);
                return gotoLoginPage(request , response);
            }
            ThreadLocalUtils.initUser(user);
            /** 如果是登录页 并且认证通过则重定向到首页    */
            if (ToolUtils.endWith(uri , "/page/login")){
                response.sendRedirect(request.getContextPath() + "/page/index");
            }
            /** 如果访问首页 则打印初始化信息    */
            if (ToolUtils.endWith(uri , "/page/index")){
                initParams(request);
            }

        } catch (Exception e) {
            log.error("拦截器，拦截出现异常！" , e );
            return gotoLoginPage(request , response);
        }
        return true;//如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
        //如果设置为true时，请求将会继续执行后面的操作
    }

    /**
     * @method  goLoginPage
     * @params  HttpServletResponse response
     * @return  boolean
     * @desc    返回登录页
     **/
    private boolean gotoLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (ToolUtils.endWith(request.getRequestURI() , "/page/login")){
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/page/login");
        return false;
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
        /** 调用结束之后移出user    */
        ThreadLocalUtils.removeUser();
    }
}
