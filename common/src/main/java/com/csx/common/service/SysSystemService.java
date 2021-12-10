package com.csx.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csx.base.service.BaseService;
import com.csx.common.entity.SysSystem;
import com.csx.common.mapper.SysSystemMapper;
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
 * @create  2021/12/10 17:15
 * @desc    获取子系统信息服务层
 **/
@Slf4j
@Service
@Scope( proxyMode = ScopedProxyMode.TARGET_CLASS )
public class SysSystemService extends BaseService {

    @Autowired
    private SysSystemMapper sysSystemMapper;

    /**
     * @method  getSysSystemAll
     * @params  List<SysSystem>
     * @return
     * @desc    获取所有的子系统信息
     **/
    public List<SysSystem> getSysSystemAll() {
        List<SysSystem> list = new ArrayList<SysSystem>();
        try{
            list = sysSystemMapper.selectList(new QueryWrapper<SysSystem>());
        } catch (Exception e){
            log.error(ToolUtils.format("获取所有的子系统信息异常" , ToolUtils.nowTime() ) , e);
        }
        return list;
    }
}
