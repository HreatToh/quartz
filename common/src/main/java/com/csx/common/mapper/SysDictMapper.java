package com.csx.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csx.common.entity.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author  Chengshx
 * @create  2021/12/10 17:00
 * @desc    字典持久层
 **/
@Mapper
@Repository
public interface SysDictMapper extends BaseMapper<SysDict> {
}
