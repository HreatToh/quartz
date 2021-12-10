package com.csx.common.service;

import com.csx.base.service.BaseService;
import com.csx.common.entity.SysSystem;
import com.csx.common.mapper.SysDictMapper;
import com.csx.common.other.JsonMap;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author  Chengshx
 * @create  2021/12/10 16:59
 * @desc    字典服务层
 **/
@Slf4j
@Service
public class SysDictService extends BaseService {

    @Autowired
    private SysDictMapper sysDictMapper;
    @Autowired
    private CacheService cacheService;


    /**
     * @method  getDict
     * @params  Map<String, Object> params
     * @return  Object
     * @desc    获取字典信息的接口 提供前台缓存使用
     **/
    public Object getDictMap(Map<String, Object> params) {
        JsonMap jsonMap = JsonMap.newInstance();
        try{
            if (ToolUtils.isNull(params) || params.isEmpty()) {
                jsonMap.appendAll(cacheService.getDictMap());
            }

        } catch (Exception e){
            log.error(ToolUtils.format("获取字典信息的接口异常！" , ToolUtils.nowTime() ) , e);
        }
        return jsonMap.toString();
    }


}
