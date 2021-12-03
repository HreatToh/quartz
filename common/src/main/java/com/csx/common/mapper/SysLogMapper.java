package com.csx.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csx.common.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author  Chengshx
 * @create  2021/12/2 17:01
 * @desc    系统日志持久层
 **/
@Mapper
@Repository
public interface SysLogMapper extends BaseMapper<SysLog> {

}
