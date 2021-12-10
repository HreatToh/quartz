package com.csx.common.auth;

import com.csx.common.config.AppCofig;
import com.csx.common.other.Constants;
import com.csx.common.utils.ToolUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author  Chengshx
 * @create  2021/12/1 17:30
 * @desc    CustomCredentialsMatcher 自定义密码验证
 **/
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    /** 使用hex算法还是bas64 ： true：hex false：base64    */
    private Boolean isStoredCredentialsHexEncoded;
    /** 散列迭代次数    */
    private Integer hashiterations;
    /**  散列算法，SHA-256 : 使用sha256算法  MD5 : 使用MD5算法 MD2 : 使用MD2算法  .. SHA-1 SHA-384 SHA-512    */
    private String hashalgorithmname;
    /** 设置是否测试环境    */
    private Boolean isDev;

    /**
     * @method  CustomCredentialsMatcher
     * @params  String hashalgorithmname ,Boolean isStoredCredentialsHexEncoded ,Integer hashiterations
     * @return  
     * @desc
     **/
    public CustomCredentialsMatcher(String hashalgorithmname ,Boolean isStoredCredentialsHexEncoded ,Integer hashiterations){
        this.hashalgorithmname = hashalgorithmname;
        this.isStoredCredentialsHexEncoded = isStoredCredentialsHexEncoded;
        this.hashiterations = hashiterations;
    }
    /**
     * @method  doCredentialsMatch
     * @params  AuthenticationToken token, AuthenticationInfo info
     * @return  boolean
     * @desc    自定义密码验证
     **/
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        Boolean isMatch = false;
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());
        String realppassword = ToolUtils.nvl(info.getCredentials() , "");
        /** 如果是测试环境则不用验证密码    */
        if (getDev()){
            return true;
        }
        /** 是否是Admin 如果是Admin 则不进行加密验证    */
        if (ToolUtils.isAdmin(username) ){
            return ToolUtils.equals(password , realppassword);
        }

        /** 密码是否加密认证    不是则直接比*/
        if (ToolUtils.isN(AppCofig.getSysConfig( Constants.App.SYS_LOGIN_PWDSIGN , "Y"))){
            return ToolUtils.equals(password , realppassword);
        }

        //加密类型，密码，盐值，迭代次数
        Object tokenCredentials = getTokenCredentials(token);
        //数据库存储密码
        Object accountCredentials = getCredentials(info);
        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
        return equals(tokenCredentials, accountCredentials);
    }

    /**
     * @method  getTokenCredentials
     * @params  UsernamePasswordToken token
     * @return  Object
     * @desc    获取加密字符
     **/
    public Object getTokenCredentials(UsernamePasswordToken token){
        SimpleHash simpleHash = new SimpleHash(hashalgorithmname, token.getPassword(), token.getUsername() + "_salt", hashiterations);
        return isStoredCredentialsHexEncoded ? simpleHash.toHex() : simpleHash.toBase64();
    }

    public Boolean getDev() {
        return isDev;
    }

    public void setDev(Boolean dev) {
        isDev = dev;
    }
}
