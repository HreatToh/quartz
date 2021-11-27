package com.csx.common.entity;

import com.csx.common.enums.QueryFormatEnum;

public class QueryFormatFactory {

    /**
     * @method  getQueryFormat
     * @params  String name
     * @return  QueryFormat
     * @desc    返回查询条件对象
     **/
    public static QueryFormatEnum getQueryFormatEnum(String name){
        return QueryFormatEnum.regex(name);
    }

}
