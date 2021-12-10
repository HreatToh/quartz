package com.csx.common.mapper;

import com.csx.common.other.TreeNode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author  Chengshx
 * @create  2021/12/9 15:48
 * @desc    树形结构持久层
 **/
@Mapper
@Repository
public interface TreeMapper {
    /**
     * @method  getTreeMenu
     * @params  Map<String, Object> params
     * @return  List<TreeNode>
     * @desc    获取菜单数据
     **/
    List<TreeNode> getTreeMenu(Map<String, Object> params) throws Exception;
}
