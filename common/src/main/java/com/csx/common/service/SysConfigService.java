package com.csx.common.service;

import com.csx.base.service.BaseService;
import com.csx.common.other.Constants;
import com.csx.common.entity.SysConfig;
import com.csx.common.handler.CacheHandler;
import com.csx.common.mapper.SysConfigMapper;
import com.csx.common.utils.CacheUtils;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  Chengshx
 * @create  2021/11/29 18:36
 * @desc    系统配置服务层
 **/
@Slf4j
@Service
@Scope( proxyMode = ScopedProxyMode.TARGET_CLASS )
public class SysConfigService extends BaseService {

    @Autowired
    private SysConfigMapper sysConfigMapper;
    @Autowired
    private CacheService cacheService;

    /**
     * @method  getSysConfigAll
     * @params
     * @return
     * @desc    获取所有的系统配置信息
     **/
    public List<SysConfig> getSysConfigAll(){
        return cacheService.getSysConfigAll();
    }
    /**
     * @method  getSysConfigAll
     * @params
     * @return
     * @desc    获取所有的子系统配置信息
     **/
    public List<SysConfig> getSysSubConfigAll() {
        return cacheService.getSysSubConfigAll();
    }
}
