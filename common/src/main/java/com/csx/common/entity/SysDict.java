package com.csx.common.entity;

import com.csx.base.entity.BaseEntity;

/**
 * @author  Chengshx
 * @create  2021/12/10 12:11
 * @desc    字典对象
 **/
public class SysDict extends BaseEntity {
    /** 系统编号    */
    private String sysId;
    /** 字典类型    */
    private String dictType;
    /** 字典类型名称    */
    private String dictTypeName;
    /** 字典key值    */
    private String dictId;
    /** 字典名称    */
    private String dictName;
    /** 字典父级节点    */
    private String dictParentId;
    /** 字典父级路径    */
    private String dictPath;
    /** 字典是否启用    */
    private String dictEnable;

    public SysDict(){
        super();
    }

    /** 三参构造    */
    public SysDict(String sysId , String dictId , String dictName){
        this.sysId = sysId;
        this.dictId = dictId;
        this.dictName = dictName;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictTypeName() {
        return dictTypeName;
    }

    public void setDictTypeName(String dictTypeName) {
        this.dictTypeName = dictTypeName;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictParentId() {
        return dictParentId;
    }

    public void setDictParentId(String dictParentId) {
        this.dictParentId = dictParentId;
    }

    public String getDictPath() {
        return dictPath;
    }

    public void setDictPath(String dictPath) {
        this.dictPath = dictPath;
    }

    public String getDictEnable() {
        return dictEnable;
    }

    public void setDictEnable(String dictEnable) {
        this.dictEnable = dictEnable;
    }
}
