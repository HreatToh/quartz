package com.csx.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csx.common.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author  Chengshx
 * @create  2021/12/5
 * @desc    角色持久层 SysRoleMapper
 **/
@Mapper
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

}
