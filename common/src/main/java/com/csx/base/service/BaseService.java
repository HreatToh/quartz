package com.csx.base.service;

import cn.hutool.core.lang.Console;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csx.common.entity.QueryFormatFactory;
import com.csx.common.enums.AppEnum;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author  Chengshx
 * @create  2021/11/19 18:51
 * @desc    BaseService 基础服务
 **/
@Slf4j
public class BaseService {

    /** 最小页号    */
    public static final Integer MIN_PAGE_NUM = 1;
    public static final Integer MAX_PAGE_SIZE_NUM = Integer.MAX_VALUE;
    /** 请求返回码    */
    public static final String SUCCESS = AppEnum.SUCCESS.getCode();
    public static final String FAIL = AppEnum.FAIL.getCode();
    /**
     * @method  initPage
     * @params  @PathVariable String sysId , @PathVariable String menu , @PathVariable String mode , @RequestParam Map<String , Object> params , ModelMap modelMap , HttpServletRequest request , HttpServletResponse response
     * @return  String
     * @desc    初始化页面的方法
     **/
    public String initPage(String sysId , String menu , String mode , Map<String , Object> params , ModelMap modelMap , HttpServletRequest request , HttpServletResponse response) {
        modelMap.addAllAttributes(params);
        return ToolUtils.format("/{}/{}/{}" , sysId , menu , mode);
    }


    /**
     * @method  getPage
     * @params  Map<String, Object> params
     * @return  <T> Page<T>
     * @desc    获取分页对象
     **/
    public <T> Page<T> getPage(Map<String, Object> params) {
        if (ToolUtils.isNull(params)){
            return  new Page<T>();
        }
        Integer page = MIN_PAGE_NUM ;
        Integer size = MAX_PAGE_SIZE_NUM;

        if (ToolUtils.isNotNull(params.get("page_num")) && params.get("page_num") instanceof Integer){
            page = Integer.parseInt(ToolUtils.nvl(params.get("page_num") , "1"));
        }
        if (ToolUtils.isNotNull(params.get("page_size_num")) && params.get("page_size_num") instanceof Integer){
            size = Integer.parseInt(ToolUtils.nvl(params.get("page_size_num") , "1"));
        }
        return new Page<T>(page ,size);
    }

    /**
     * @method  getWrapper
     * @params  Map<String , Object> params
     * @return  <T> Wrapper<T>
     * @desc    获取查询条件
     * fn|field|eq ： 条件等于
     * fn|field|neq ： 条件不等于
     * fn|field|gt ： 条件大于
     * fn|field|lt ： 条件小于
     * fn|field|gte ： 条件大于等于
     * fn|field|lte ： 条件小于等于
     * fn|field|in ： 条件在集合内
     * fn|field|nin ： 条件不在集合内
     * fn|field|lk ： 条件包含
     * fn|field|nlk ： 条件不包含
     * fn|field|slk ： 条件前缀
     * fn|field|elk ： 条件后缀
     **/
    public <T> Wrapper<T> getWhereQueryWrapper( Map<String , Object> params ){
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        for (Map.Entry<String , Object> entry: params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (ToolUtils.isNull(value)){
                continue;
            }
            if (ToolUtils.startWithIgnoreCase(key , "fn|")){
                QueryFormatFactory.getQueryFormatEnum(key).getQueryFormat().execute( key , params , queryWrapper);
            }

            if (ToolUtils.startWithIgnoreCase(key , "sort_")){
                String order = ToolUtils.nvl( value , "");
                String field = key.replace("sort_" , "");
                queryWrapper.orderBy(true ,ToolUtils.equalsIgnoreCase( order , "ASC") , field );
            }
        }
        return queryWrapper;
    }

}
