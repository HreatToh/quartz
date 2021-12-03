package com.csx.main.plugin.shiro.config;

import com.csx.common.auth.AuthRealm;
import com.csx.common.auth.CustomCredentialsMatcher;
import com.csx.common.config.AppCofig;
import com.csx.common.enums.EvmentEnum;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author  Chengshx
 * @create  2021/11/27 22:19
 * @desc    ShiroConfig 权限控制配置
 **/
@Slf4j
@Configuration
public class ShiroConfig {


    /** 受限地址    */
    @Value("${shiro.pathdefinitions.authc}")
    private String[] authc;
    /** 开放地址    */
    @Value("${shiro.pathdefinitions.anon}")
    private String[] anon;
    /** 使用hex算法还是bas64 ： true：hex false：base64    */
    @Value("${shiro.isStoredCredentialsHexEncoded}")
    private Boolean isStoredCredentialsHexEncoded;
    /** 散列迭代次数    */
    @Value("${shiro.hashiterations}")
    private Integer hashiterations;
    /**  散列算法，SHA-256 : 使用sha256算法  MD5 : 使用MD5算法 MD2 : 使用MD2算法  .. SHA-1 SHA-384 SHA-512    */
    @Value("${shiro.hashiterations}")
    private String hashalgorithmname;
    /** 环境    */
    @Value("${spring.profiles.active}")
    private String active;
    /**
     * @method  getHashedCredentialsMatcher
     * @params  
     * @return  HashedCredentialsMatcher
     * @desc    设置用于匹配密码的 HashedCredentialsMatcher
     **/
    @Bean
    public HashedCredentialsMatcher getHashedCredentialsMatcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 散列算法，使用sha256算法
        matcher.setHashAlgorithmName(hashalgorithmname);
        // 数据库存储密码字段使用HEX还是BASE64方式加密
        matcher.setStoredCredentialsHexEncoded(isStoredCredentialsHexEncoded);
        // 散列迭代次数
        matcher.setHashIterations(hashiterations);
        return matcher;
    }

    /**
     * @method  getCustomCredentialsMatcher
     * @params  
     * @return  CustomCredentialsMatcher
     * @desc    设置用于自定义匹配密码的 CustomCredentialsMatcher
     **/
    @Bean
    public CustomCredentialsMatcher getCustomCredentialsMatcher(){
        CustomCredentialsMatcher customCredentialsMatcher = new CustomCredentialsMatcher(hashalgorithmname , isStoredCredentialsHexEncoded , hashiterations);
        /** 设置环境信息    */
        customCredentialsMatcher.setDev(ToolUtils.equalsIgnoreCase(EvmentEnum.DEV.key() , active));
        return customCredentialsMatcher;
    }

    /**
     * @method  getSessionsSecurityManager
     * @params  
     * @return  SessionsSecurityManager
     * @desc    配置security 并设置AuthRealm 避免未找到authorizer 的错误
     **/
    @Bean
    public SessionsSecurityManager getSessionsSecurityManager(){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(getAuthRealm());
        return manager;
    }
    /**
     * @method  getAuthRealm
     * @params  
     * @return  AuthRealm
     * @desc    配置自定义 Realm
     **/
    @Bean
    public AuthRealm getAuthRealm(){
        AuthRealm authRealm = new AuthRealm();
        // 配置使用哈希密码匹配
//        authRealm.setCredentialsMatcher(getHashedCredentialsMatcher());
        authRealm.setCredentialsMatcher(getCustomCredentialsMatcher());
        return authRealm;
    }


    /**
     * @method  getShiroFilterChainDefinition
     * @params  
     * @return  ShiroFilterChainDefinition
     * @desc    配置url过滤器
     **/
    @Bean
    public ShiroFilterChainDefinition getShiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        for (int i = 0; i < anon.length ; i++) {
            chainDefinition.addPathDefinition(anon[i] , "anon");
        }
        for (int i = 0; i < authc.length ; i++) {
            chainDefinition.addPathDefinition(authc[i] , "authc");
        }

        return chainDefinition;
    }
}
