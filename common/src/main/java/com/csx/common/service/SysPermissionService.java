package com.csx.common.service;

import com.csx.base.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * @author  Chengshx
 * @create  2021/12/5
 * @desc    权限服务层 SysPermissionService
 **/
@Slf4j
@Service
@Scope( proxyMode = ScopedProxyMode.TARGET_CLASS )
public class SysPermissionService extends BaseService {

}
