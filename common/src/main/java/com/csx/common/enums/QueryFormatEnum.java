package com.csx.common.enums;

import com.csx.common.factory.QueryFormat;
import com.csx.common.utils.ToolUtils;

import java.util.regex.Pattern;

/**
 * @author  Chengshx
 * @create  2021/11/24 16:29
 * @desc    QueryFormatEnum 查询条件枚举
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
public enum QueryFormatEnum{
    /**  条件等于          fn\\|[a-zA-Z1-9_]+\\|eq     */
    EQ  ("$field$ = $value$"  ,"fn\\|[a-zA-Z1-9_]+\\|eq"  , QueryFormat.eq  ),
    /**  条件不等于        fn\\|[a-zA-Z1-9_]+\\|neq    */
    NEQ ("$field$ != $value$" ,"fn\\|[a-zA-Z1-9_]+\\|neq" , QueryFormat.neq ),
    /**  条件大于          fn\\|[a-zA-Z1-9_]+\\|gt     */
    GT  ("$field$ > $value$"  ,"fn\\|[a-zA-Z1-9_]+\\|gt"  , QueryFormat.gt  ),
    /**  条件小于          fn\\|[a-zA-Z1-9_]+\\|lt     */
    LT  ("$field$ < $value$"  ,"fn\\|[a-zA-Z1-9_]+\\|lt"  , QueryFormat.lt  ),
    /**  条件大于等于      fn\\|[a-zA-Z1-9_]+\\|gte    */
    GTE ("$field$ >= $value$" ,"fn\\|[a-zA-Z1-9_]+\\|gte" , QueryFormat.gte ),
    /**  条件小于等于      fn\\|[a-zA-Z1-9_]+\\|lte    */
    LTE ("$field$ <= $value$" ,"fn\\|[a-zA-Z1-9_]+\\|lte" , QueryFormat.lte ),
    /**  条件在集合内      fn\\|[a-zA-Z1-9_]+\\|in     */
    IN  ("$field$ IN( $value$ )"  ,"fn\\|[a-zA-Z1-9_]+\\|in"  , QueryFormat.in  ),
    /**  条件不在集合内    fn\\|[a-zA-Z1-9_]+\\|nin    */
    NIN ("$field$ NOT IN ( $value$ )" ,"fn\\|[a-zA-Z1-9_]+\\|nin" , QueryFormat.nin ),
    /**  条件包含          fn\\|[a-zA-Z1-9_]+\\|lk     */
    LK  ("$field$ LIKE ( $value$ )"  ,"fn\\|[a-zA-Z1-9_]+\\|lk"  , QueryFormat.lk  ),
    /**  条件不包含        fn\\|[a-zA-Z1-9_]+\\|nlk    */
    NLK ("$field$ NOT LIKE $value$" ,"fn\\|[a-zA-Z1-9_]+\\|nlk" , QueryFormat.nlk ),
    /**  条件前缀          fn\\|[a-zA-Z1-9_]+\\|slk    */
    SLK ("$field$ LIKE $value$" ,"fn\\|[a-zA-Z1-9_]+\\|slk" , QueryFormat.slk ),
    /**  条件后缀          fn\\|[a-zA-Z1-9_]+\\|elk    */
    ELK ("$field$ LIKE $value$" ,"fn\\|[a-zA-Z1-9_]+\\|elk" , QueryFormat.elk );

    private String formart;
    private String regex;
    private QueryFormat queryFormat;

    QueryFormatEnum(String formart , String regex , QueryFormat queryFormat){
        this.formart = formart;
        this.regex = regex;
        this.queryFormat = queryFormat;
        this.queryFormat.setFormatEnum(this);
    }

    /**
     * @method  regex
     * @params  String name
     * @return  QueryFormatEnum
     * @desc    获取对应的枚举类型
     **/
    public static QueryFormatEnum regex(String name){
        String[] split = name.split("|");
        if (ToolUtils.isNotNull(split) && split.length == 3 ){
            QueryFormatEnum formatEnum = QueryFormatEnum.valueOf(split[2].toUpperCase());
            if (ToolUtils.isNotNull(formatEnum) && Pattern.matches(formatEnum.getRegex() , name)) {
                return formatEnum;
            }
        }
        return EQ;
    }

    public String getFormart() {
        return formart;
    }

    public void setFormart(String formart) {
        this.formart = formart;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public QueryFormat getQueryFormat() {
        return queryFormat;
    }

    public void setQueryFormat(QueryFormat queryFormat) {
        this.queryFormat = queryFormat;
    }
}
