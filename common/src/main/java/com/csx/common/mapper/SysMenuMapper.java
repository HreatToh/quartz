package com.csx.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csx.common.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author  Chengshx
 * @create  2021/11/22 17:19
 * @desc    SysMenuMapper 菜单管理持久层
 **/
@Mapper
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}
