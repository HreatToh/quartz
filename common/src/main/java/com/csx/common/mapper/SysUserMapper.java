package com.csx.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csx.common.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author  Chengshx
 * @create  2021/12/1 16:50
 * @desc    SysUserMapper 用户持久层
 **/
@Mapper
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

}
