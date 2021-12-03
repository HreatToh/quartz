package com.csx.common.factory;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csx.common.enums.QueryFormatEnum;
import com.csx.common.utils.ToolUtils;

import java.util.Map;
/**
 * @author  Chengshx
 * @create  2021/11/24 16:59
 * @desc    条件查询格式化
 **/
public abstract class QueryFormat {

    /**
     * 枚举对象
     */
    private QueryFormatEnum formatEnum;

    public QueryFormatEnum getFormatEnum() {
        return formatEnum;
    }

    public void setFormatEnum(QueryFormatEnum formatEnum) {
        this.formatEnum = formatEnum;
    }

    /**
     * @method  getField
     * @params  String key
     * @return  String
     * @desc    获取字段值
     **/
    public String getField( String key ){
        String[] split = key.split("|");
        String field = split[1];
        return field;
    }
    /**
     * @method  init
     * @params  String key , Map<String , Object> map
     * @return  Boolean
     * @desc    初始化的方法
     **/
    public Boolean init(String key , Map<String , Object> map ){
        return ToolUtils.isNull(map) || ToolUtils.isNull(map.get(key)) ;
    }
    /**
     * @method  init
     * @params  String key , Map<String , Object> map , QueryWrapper wrapper
     * @return  Boolean
     * @desc    初始化的方法
     **/
    public Boolean init(String key , Map<String , Object> map , QueryWrapper wrapper){
        return ToolUtils.isNull(map) || ToolUtils.isNull(map.get(key)) || ToolUtils.isNull(wrapper);
    }

    /**
     * @method  execute
     * @params  String key , Map<String , Object> map , QueryWrapper wrapper
     * @return  
     * @desc    执行的抽象方法
     **/
    public abstract void execute(String key , Map<String , Object> map , QueryWrapper wrapper);

    /**
     * @method  executeSql
     * @params  String key , Map<String , Object> map
     * @return  String
     * @desc    执行返回拼接的SQL
     **/
    public String executeSql(String key , Map<String , Object> map){
        if (init(key , map )) return "";
        String value = ToolUtils.nvl(map.get(key) , "");
        String format = formatEnum.getFormart();
        return format.replace("$field$" , getField(key)).replace("$value$" ,ToolUtils.format("'{}'" , value));
    };


    /** QueryFormat eq 等于的处理方式    */
    public static QueryFormat eq = new QueryFormat() {

        public void execute(String key, Map<String, Object> map, QueryWrapper wrapper) {
            if (init(key , map , wrapper )) return ;
            String value = ToolUtils.nvl(map.get(key) , "");
            wrapper.eq(getField(key) , value);
        }

    };

    /** QueryFormat neq 不等于的处理方式    */
    public static QueryFormat neq = new QueryFormat() {

        public void execute(String key, Map<String, Object> map, QueryWrapper wrapper) {
            if (init(key , map , wrapper )) return ;
            String value = ToolUtils.nvl(map.get(key) , "");
            wrapper.ne(getField(key) , value );
        }

    };

    /** QueryFormat gt 大于的处理方式    */
    public static QueryFormat gt = new QueryFormat() {

        public void execute(String key, Map<String, Object> map, QueryWrapper wrapper) {
            if (init(key , map , wrapper )) return ;
            String value = ToolUtils.nvl(map.get(key) , "");
            wrapper.gt(getField(key) , value );
        }

    };

    /** QueryFormat lt 小于的处理方式    */
    public static QueryFormat lt = new QueryFormat() {

        public void execute(String key, Map<String, Object> map, QueryWrapper wrapper) {
            if (init(key , map , wrapper )) return ;
            String value = ToolUtils.nvl(map.get(key) , "");
            wrapper.lt(getField(key) , value );
        }

    };

    /** QueryFormat gte 大于等于的处理方式    */
    public static QueryFormat gte = new QueryFormat() {

        public void execute(String key, Map<String, Object> map, QueryWrapper wrapper) {
            if (init(key , map , wrapper )) return ;
            String value = ToolUtils.nvl(map.get(key) , "");
            wrapper.ge(getField(key) , value );
        }

    };

    /** QueryFormat lte 小于等于的处理方式    */
    public static QueryFormat lte = new QueryFormat() {

        public void execute(String key, Map<String, Object> map, QueryWrapper wrapper) {
            if (init(key , map , wrapper )) return ;
            String value = ToolUtils.nvl(map.get(key) , "");
            wrapper.le(getField(key) , value );
        }

    };

    /** QueryFormat in 条件在集合内的处理方式    */
    public static QueryFormat in = new QueryFormat() {

        public void execute(String key, Map<String, Object> map, QueryWrapper wrapper) {
            if (init(key , map , wrapper )) return ;
            String value = ToolUtils.nvl(map.get(key) , "");
            wrapper.in(getField(key) , value.split(","));
        }

    };

    /** QueryFormat nin 条件不在集合内的处理方式    */
    public static QueryFormat nin = new QueryFormat() {

        public void execute(String key, Map<String, Object> map, QueryWrapper wrapper) {
            if (init(key , map , wrapper )) return ;
            String value = ToolUtils.nvl(map.get(key) , "");
            wrapper.notIn(getField(key) , value.split(","));
        }

    };

    /** QueryFormat lk 条件包含的处理方式    */
    public static QueryFormat lk = new QueryFormat() {

        public void execute(String key, Map<String, Object> map, QueryWrapper wrapper) {
            if (init(key , map , wrapper )) return ;
            String value = ToolUtils.nvl(map.get(key) , "");
            wrapper.like(getField(key) , value);
        }

    };

    /** QueryFormat nlk 条件不包含的处理方式    */
    public static QueryFormat nlk = new QueryFormat() {

        public void execute(String key, Map<String, Object> map, QueryWrapper wrapper) {
            if (init(key , map , wrapper )) return ;
            String value = ToolUtils.nvl(map.get(key) , "");
            wrapper.notLike(getField(key) , value);
        }

    };

    /** QueryFormat slk 条件包含前缀的处理方式    */
    public static QueryFormat slk = new QueryFormat() {

        public void execute(String key, Map<String, Object> map, QueryWrapper wrapper) {
            if (init(key , map , wrapper )) return ;
            String value = ToolUtils.nvl(map.get(key) , "");
            wrapper.likeLeft(getField(key) , value);
        }

    };

    /** QueryFormat elk 条件包含后缀的处理方式    */
    public static QueryFormat elk = new QueryFormat() {

        public void execute(String key, Map<String, Object> map, QueryWrapper wrapper) {
            if (init(key , map , wrapper )) return ;
            String value = ToolUtils.nvl(map.get(key) , "");
            wrapper.likeRight(getField(key) , value);
        }

    };
}
