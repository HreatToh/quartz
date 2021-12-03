package com.csx.common.service;

import com.csx.base.service.BaseService;
import com.csx.common.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * @author  Chengshx
 * @create  2021/12/1 16:48
 * @desc    
 **/
@Slf4j
@Service
@Scope( proxyMode = ScopedProxyMode.TARGET_CLASS )
public class SysUserService extends BaseService {

    @Autowired
    private SysUserMapper sysUserMapper;

}
