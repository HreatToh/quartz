package com.csx.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csx.common.entity.SysException;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author  Chengshx
 * @create  2021/11/19 18:51
 * @desc    ExcetionMapper
 **/
@Mapper
@Repository
public interface ExcetionMapper extends BaseMapper<SysException> {
}
