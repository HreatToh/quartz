package com.csx.common.entity;

import com.csx.base.entity.BaseEntity;

/**
 * @author  Chengshx
 * @create  2021/11/17 18:43
 * @desc    系统配置类
 **/
public class SysConfig extends BaseEntity {

    /**   系统id            */
    private String sysId            ;
    /**   配置id            */
    private String itemId           ;
    /**   配置项名称        */
    private String itemName         ;
    /**   配置值            */
    private String itemVal          ;
    /**   默认值            */
    private String itemDefval       ;
    /**   配置类型          */
    private String itemType         ;
    /**   配置信息          */
    private String itemConf         ;
    /**   配置描述          */
    private String itemDesc         ;
    /**   配置组            */
    private String itemGroup        ;
    /**   序号              */
    private String itemOrder        ;
    /**   是否启用          */
    private String itemEnable       ;

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemVal() {
        return itemVal;
    }

    public void setItemVal(String itemVal) {
        this.itemVal = itemVal;
    }

    public String getItemDefval() {
        return itemDefval;
    }

    public void setItemDefval(String itemDefval) {
        this.itemDefval = itemDefval;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemConf() {
        return itemConf;
    }

    public void setItemConf(String itemConf) {
        this.itemConf = itemConf;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public String getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(String itemOrder) {
        this.itemOrder = itemOrder;
    }

    public String getItemEnable() {
        return itemEnable;
    }

    public void setItemEnable(String itemEnable) {
        this.itemEnable = itemEnable;
    }

}
