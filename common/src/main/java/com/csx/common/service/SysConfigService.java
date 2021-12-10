package com.csx.common.service;

import com.csx.base.service.BaseService;
import com.csx.common.entity.SysConfig;
import com.csx.common.mapper.SysConfigMapper;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

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

    /**
     * @method  setConfig
     * @params  SysConfig sysConfig
     * @return  Integer
     * @desc    修改系统配置的方法
     **/
    public Integer setConfig(SysConfig sysConfig) {
        Integer update = 0;
        try{
            update = sysConfigMapper.setConfig(sysConfig);
        } catch (Exception e){
            log.error(ToolUtils.format("[{}]修改系统配置异常！" , ToolUtils.nowTime() ) , e);
        }
        return update;
    }

    /**
     * @method  setSubConfig
     * @params  SysConfig sysConfig
     * @return  Integer
     * @desc    修改子系统配置的方法
     **/
    public Integer setSubConfig(SysConfig sysConfig) {
        Integer update = 0;
        try{
            update = sysConfigMapper.setSubConfig(sysConfig);
        } catch (Exception e){
            log.error(ToolUtils.format("[{}]修改系统配置异常！" , ToolUtils.nowTime() ) , e);
        }
        return update;
    }
}
