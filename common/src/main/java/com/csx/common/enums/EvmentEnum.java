package com.csx.common.enums;

/**
 * @author  Chengshx
 * @create  2021/11/18 12:24
 * @desc    环境枚举类
 **/
public enum EvmentEnum {

    DEV("dev" , "开发环境"),
    PROD("prod" , "生成环境"),
    TEST("test" , "测试环境");

    private final String key;
    private final String value;

    private EvmentEnum(String key , String value){
        this.key = key;
        this.value = value;
    }

    public String key() {
        return key;
    }

    public String val() {
        return value;
    }
}
