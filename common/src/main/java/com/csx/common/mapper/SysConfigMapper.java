package com.csx.common.mapper;

import com.csx.common.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  Chengshx
 * @create  2021/11/30 10:20
 * @desc    系统配置接口
 **/
@Mapper
@Repository
public interface SysConfigMapper {

    /**
     * @method  getSysConfigAll
     * @params  
     * @return  List<SysConfig>
     * @desc    获取所有系统配置信息
     **/
    List<SysConfig> getSysConfigAll() throws Exception;

    /**
     * @method  getSysSubConfigAll
     * @params  
     * @return  List<SysConfig>
     * @desc    获取所有子系统配置信息
     **/
    List<SysConfig> getSysSubConfigAll() throws Exception;
}
