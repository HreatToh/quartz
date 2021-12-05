package com.csx.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csx.common.entity.SysMenu;
import com.csx.common.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  Chengshx
 * @create  2021/12/5
 * @desc    权限持久层 SysPermissionMapper
 **/
@Mapper
@Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    /** getSysMenuByUserId 获取用户菜单权限    */
    List<SysMenu> getSysMenuByUserId(@Param("userId") String userId) throws Exception;
    /** getSysSystemByUserId 获取用户的系统信息    */
    List<SysMenu> getSysSystemByUserId(@Param("userId") String userId) throws Exception;
}
