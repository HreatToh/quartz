package com.csx.common.entity;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

@Slf4j
public class AuthRealm extends AuthorizingRealm {

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
        return null;
    }
}
