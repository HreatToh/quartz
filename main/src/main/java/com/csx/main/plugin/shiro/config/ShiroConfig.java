package com.csx.main.plugin.shiro.config;

import com.csx.common.entity.AuthRealm;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;


/**
 * @author  Chengshx
 * @create  2021/11/27 22:19
 * @desc    ShiroConfig 权限控制配置
 **/
@Slf4j
@Configuration
public class ShiroConfig {


    @Value("#{${shiro.pathdefinitions}}")
    private Map<String , String > pathdefinitions;

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
        matcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        // 数据库存储密码字段使用HEX还是BASE64方式加密
        matcher.setStoredCredentialsHexEncoded(false);
        // 散列迭代次数
        matcher.setHashIterations(1024);
        return matcher;
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
        authRealm.setCredentialsMatcher(getHashedCredentialsMatcher());
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
        chainDefinition.addPathDefinitions(pathdefinitions);
        return chainDefinition;
    }
}
