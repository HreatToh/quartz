package com.csx.common.enums;


/**
 * @author  Chengshx
 * @create  2021/12/2 16:33
 * @desc    新建日志枚举
 **/
public enum LogEnum {
    /** OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL    */
    /** 关闭>致命>错误>警告>信息>调试>跟踪>全部    */
    OFF  ("OFF"  ,"关闭"),
    FATAL("FATAL","致命"),
    ERROR("ERROR","错误"),
    WARN ("WARN" ,"警告"),
    INFO ("INFO" ,"信息"),
    DEBUG("DEBUG","调试"),
    TRACE("TRACE","跟踪"),
    ALL  ("ALL"  ,"全部");

    private String level;
    private String name;

    LogEnum(String level , String name){
        this.level = level ;
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
