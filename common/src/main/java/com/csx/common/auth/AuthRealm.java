package com.csx.common.auth;

import com.csx.common.config.AppCofig;
import com.csx.common.entity.SysUser;
import com.csx.common.mapper.SysUserMapper;
import com.csx.common.other.Constants;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Slf4j
public class AuthRealm extends AuthorizingRealm {

    @Override
    public String getName() {
        return "My_AuthRealm";
    }

    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * @method  doGetAuthorizationInfo
     * @params  PrincipalCollection principalCollection
     * @return  AuthorizationInfo
     * @desc    doGetAuthorizationInfo主要是获取用户的角色和权限，并交给Shiro去判断是否具有访问资源的权限。
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }

    /**
     * @method  doGetAuthenticationInfo
     * @params  AuthenticationToken authenticationToken
     * @return  AuthenticationInfo
     * @desc    doGetAuthenticationInfo 主要作用是获取用户输入的用户名、密码等信息并从数据库中取出保存的密码交给shiro，由shiro的密码匹配器进行匹配。
     *          密码验证以及认证
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userId = ToolUtils.nvl(authenticationToken.getPrincipal() , "");
        SysUser sysUser = sysUserMapper.selectById(userId);
        /** 密码过期天数    */
        Integer passwordOverDay = AppCofig.getSysConfig(Constants.App.SYS_LOGIN_PWDOVER, 365);
        /** 密码过期日期    */
        Date overDate = ToolUtils.offsetDay(sysUser.getCommCdate(), passwordOverDay);
        /** 验证账户是否存在    */
        if (ToolUtils.isNull(sysUser)) {
            throw new UnknownAccountException("账户不存在!");
        }
        /** 验证账户是否被禁用    */
        if (ToolUtils.isY(sysUser.getCommDelfalg())){
            throw new DisabledAccountException("账号已经被禁用！");
        }

        /** 验证账户是否被锁定    */
        if (ToolUtils.isY(sysUser.getUserIsLock())){
            throw new LockedAccountException("账号已经被锁定!");
        }

        /** 验证密码有没有过期    */
        if (!ToolUtils.compareDate(overDate)){
            throw new ExpiredCredentialsException("密码已经过期，请管理员重置密码！");
        }
        /** 判断是否是多用户登录 不是的话需要判定用户是否在线   */
        if (ToolUtils.isN(AppCofig.getSysConfig(Constants.App.SYS_LOGIN_ISMULTI , "Y")) && ToolUtils.isY(sysUser.getUserIsOnlion())){
            throw new ConcurrentAccessException("用户已经登录，请注销后重新登录！");
        }

        sysUser.setSalt(userId + "_salt");
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(sysUser, sysUser.getUserPassword(), ByteSource.Util.bytes(sysUser.getSalt()) , getName());
        return authenticationInfo;
    }

}
