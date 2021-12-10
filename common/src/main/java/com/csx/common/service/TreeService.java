package com.csx.common.service;

import com.csx.base.service.BaseService;
import com.csx.common.mapper.TreeMapper;
import com.csx.common.other.TreeNode;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author  Chengshx
 * @create  2021/12/9 15:47
 * @desc    树形服务层
 **/
@Slf4j
@Service
public class TreeService extends BaseService {

    @Autowired
    private TreeMapper treeMapper;
    /**
     * @method  getTreeMenu
     * @params  Map<String, Object> params
     * @return  Object
     * @desc    获取菜单树形结构
     **/
    public List<TreeNode> getTreeMenu(Map<String, Object> params) {
        List<TreeNode> list = new ArrayList<TreeNode>();
        try{
            params.put("userId" , ToolUtils.getUserId());
            list = treeMapper.getTreeMenu(params);
        } catch (Exception e){
            log.error(ToolUtils.format("获取菜单树形结构异常！" , ToolUtils.nowTime() ) , e);
        }
        return list;
    }
}
