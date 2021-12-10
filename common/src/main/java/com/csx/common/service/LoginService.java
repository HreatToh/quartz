package com.csx.common.service;

import cn.hutool.core.map.MapUtil;
import com.csx.base.service.BaseService;
import com.csx.common.config.AppCofig;
import com.csx.common.entity.SysUser;
import com.csx.common.exception.AppException;
import com.csx.common.mapper.SysUserMapper;
import com.csx.common.other.Constants;
import com.csx.common.other.ResultBody;
import com.csx.common.enums.AppEnum;
import com.csx.common.mapper.LoginMapper;
import com.csx.common.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author  Chengshx
 * @create  2021/12/1 11:18
 * @desc    LoginService 登录的服务层
 **/
@Slf4j
@Service
public class LoginService extends BaseService {

    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private CacheService cacheService;

    /**
     * @method  login
     * @params  Map<String,Object> params , ModelMap modelMap , HttpServletRequest request , HttpServletResponse response
     * @return  ResultBody
     * @desc    登录的逻辑
     **/
    public ResultBody login(Map<String,Object> params , ModelMap modelMap , HttpServletRequest request , HttpServletResponse response) {
        UsernamePasswordToken token = null;
        try{
            String username = MapUtil.getStr(params , "username");
            String password = MapUtil.getStr(params , "password");
            if (!ToolUtils.checkVerifyCode(request) && !ToolUtils.isDev){
                return ResultBody.success("验证码不正确！");
            }
            token = new UsernamePasswordToken( username , password );
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            onLoginSeccess(request ,response , token);
        } catch (UnknownAccountException e){
            return onUnknownAccountException( token , e );
        } catch (LockedAccountException e) {
            return onLockedAccountException( token , e );
        } catch (DisabledAccountException e){
            return onDisabledAccountException( token , e);
        } catch (ExpiredCredentialsException e){
            return onExpiredCredentialsException( token , e);
        } catch (ConcurrentAccessException e){
            return onConcurrentAccessException(token , e);
        } catch (IncorrectCredentialsException e){
            return onIncorrectCredentialsException(token , e);
        }
        return ResultBody.success("登录成功！" , (Object) response.getHeader(Constants.App.HEADER_AUTHORIZATION));
    }

    /**
     * @method  onLoginSeccess
     * @params  UsernamePasswordToken token
     * @return  
     * @desc    登录成功后的方法
     **/
    private void onLoginSeccess(HttpServletRequest request, HttpServletResponse response, UsernamePasswordToken token) {
        HttpSession session = null;
        String username = null;
        String _token = null;
        SysUser sysUser = null;
        try{
            session = request.getSession();
            username = token.getUsername();
            int update = JdbcUtils.update("update sys_user_info set user_error_num = ? , user_login_time = ?  where user_id = ?", "0", ToolUtils.nowTime(), username);
            if (update != 1){
                throw new AppException("更新用户信息失败！");
            }
            sysUser = sysUserMapper.selectById(username);
            sysUser.setUserPassword(Constants.App.$BLANK);
            _token = ToolUtils.getToken(sysUser);
            /** 设置Session    */
            session.setAttribute(Constants.Session.SESSION_USER_KEY , sysUser);
            session.setAttribute(Constants.Session.SESSION_TOKEN_KEY , _token);
            ThreadLocalUtils.initUser(sysUser);
            OnlineUtils.set(sysUser.getUserId() , session.getId());
            SYSLOGINFO("SYSMM" , "用户登录" , ToolUtils.format("[{}]用户：{} 登录IP：{} ，登录成功！" , ToolUtils.nowTime() , username , ToolUtils.localhost()));
        } catch (Exception e){
            log.error(ToolUtils.format("[{}]登录成功，初始化信息异常！" , ToolUtils.nowTime() ) , e);
        }
    }

    /**
     * @method  onIncorrectCredentialsException
     * @params  UsernamePasswordToken token, IncorrectCredentialsException e
     * @return  ResultBody
     * @desc    处理密码验证错误的方法！
     **/
    private ResultBody onIncorrectCredentialsException(UsernamePasswordToken token, IncorrectCredentialsException e) {
        String username = token.getUsername();
        /** 密码错误次数    */
        Integer passwordErrorNum = AppCofig.getSysConfig(Constants.App.SYS_LOGIN_PWDERRNUM, 3);
        SysUser sysUser = sysUserMapper.selectById(username);
        /** 现在密码已经错误次数    */
        Integer nowPasswordErrorNum = ToolUtils.nvl(sysUser.getUserErrorNum() , 0 ) + 1;

        /** 如果错误次数已经大于等于定义的次数则用户做锁定处理 如果是Admin 次数则设置成 99 */
        if ( ( passwordErrorNum = ToolUtils.isAdmin(username) ? 99 : passwordErrorNum ) <= nowPasswordErrorNum ){
            JdbcUtils.update("update sys_user_info set user_error_num = ? , user_is_lock = ? where user_id = ? " , nowPasswordErrorNum , Constants.App.Y , username);
        } else{
            JdbcUtils.update("update sys_user_info set user_error_num = ? where user_id = ? " , nowPasswordErrorNum , username);
        }
        log.error(ToolUtils.format("[{}]登录失败，密码错误，重试[{}]次后账号将被锁定！" , ToolUtils.nowTime() , passwordErrorNum - nowPasswordErrorNum ) , e);
        return ResultBody.success(AppEnum.PASSWORDERROR.getCode() , ToolUtils.format("密码错误，重试[{}]次后账号将被锁定！" , passwordErrorNum - nowPasswordErrorNum ));
    }

    /**
     * @method  onConcurrentAccessException
     * @params  UsernamePasswordToken token, ConcurrentAccessException e
     * @return  ResultBody
     * @desc    处理多用户登录的情况
     **/
    private ResultBody onConcurrentAccessException(UsernamePasswordToken token, ConcurrentAccessException e) {
        log.error(ToolUtils.format("[{}]登录失败，{}！" , ToolUtils.nowTime() , e.getMessage() ) , e);
        return ResultBody.success(AppEnum.CONCURRENTACCESS);
    }

    /**
     * @method  onExpiredCredentialsException
     * @params  UsernamePasswordToken token, ExpiredCredentialsException e
     * @return  ResultBody
     * @desc    处理凭证过期的方法
     **/
    private ResultBody onExpiredCredentialsException(UsernamePasswordToken token, ExpiredCredentialsException e) {
        log.error(ToolUtils.format("[{}]登录失败，{}！" , ToolUtils.nowTime() , e.getMessage() ) , e);
        return ResultBody.success(AppEnum.EXPIREDCREDENTIALS);
    }

    /**
     * @method  onDisabledAccountException
     * @params  UsernamePasswordToken token, DisabledAccountException e
     * @return  ResultBody
     * @desc    处理用户被禁用的方法
     **/
    private ResultBody onDisabledAccountException(UsernamePasswordToken token, DisabledAccountException e) {
        log.error(ToolUtils.format("[{}]登录失败，{}！" , ToolUtils.nowTime() , e.getMessage() ) , e);
        return ResultBody.success(AppEnum.DISABLEDACCOUNT);
    }

    /**
     * @method  onUnknownAccountException
     * @params  UsernamePasswordToken token, UnknownAccountException e
     * @return  ResultBody
     * @desc    处理用户被锁住的问题
     **/
    private ResultBody onUnknownAccountException(UsernamePasswordToken token, UnknownAccountException e) {
        log.error(ToolUtils.format("[{}]登录失败，{}！" , ToolUtils.nowTime() , e.getMessage() ) , e);
        return ResultBody.success(AppEnum.UNKNOWNACOUNT);
    }

    /**
     * @method  onLockedAccountException
     * @params  UsernamePasswordToken token, LockedAccountException e
     * @return  ResultBody
     * @desc    处理用户被锁住的方法
     **/
    private ResultBody onLockedAccountException(UsernamePasswordToken token, LockedAccountException e) {
        log.error(ToolUtils.format("[{}]登录失败，{}！" , ToolUtils.nowTime() , e.getMessage() ) , e);
        return ResultBody.success(AppEnum.LOCKEDACCOUNT);
    }

    /**
     * @method  logout
     * @params  Map<String, Object> params, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response
     * @return  ResultBody
     * @desc    登出接口
     **/
    public ResultBody logout(Map<String, Object> params, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        /** 清理缓存在线信息    */
        OnlineUtils.remove(ToolUtils.getUserId() , request.getSession().getId());
        /** 清理Session   SESSION_USER_KEY 用户信息 */
        session.removeAttribute(Constants.Session.SESSION_USER_KEY);
        /** 清理Session   SESSION_TOKEN_KEY 用户token信息 */
        session.removeAttribute(Constants.Session.SESSION_TOKEN_KEY);
        /** 设置session失效    */
        session.invalidate();
        return ResultBody.success("清理成功！");
    }
}
