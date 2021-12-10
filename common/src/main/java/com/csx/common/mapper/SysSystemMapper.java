package com.csx.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csx.common.entity.SysSystem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author  Chengshx
 * @create  2021/12/10 17:16
 * @desc    获取所有子系统持久层
 **/
@Mapper
@Repository
public interface SysSystemMapper extends BaseMapper<SysSystem> {
}
