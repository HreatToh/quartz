package com.csx.common.service;

import com.csx.base.service.BaseService;
import com.csx.common.mapper.SysRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;


/**
 * @author  Chengshx
 * @create  2021/12/5
 * @desc    角色服务提供层 SysRoleService
 **/
@Slf4j
@Service
@Scope( proxyMode = ScopedProxyMode.TARGET_CLASS )
public class SysRoleService extends BaseService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
}
