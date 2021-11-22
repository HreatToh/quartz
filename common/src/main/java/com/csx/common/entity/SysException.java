package com.csx.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csx.common.base.entity.BaseEntity;

/**
 * @author  Chengshx
 * @create  2021/11/19 18:42
 * @desc    异常处理类
 **/
@TableName("SYS_EXCEPTION_INFO")
public class SysException extends BaseEntity {
    /**   主键id                */
    @TableId
    private String expId            ;
    /**   服务器                */
    private String expIp            ;
    /**   端口                  */
    private String expPort          ;
    /**   类名                  */
    private String expClassName    ;
    /**   名称                  */
    private String expName          ;
    /**   错误详情              */
    private String expMsg           ;
    /**   错误时间              */
    private String expTime          ;
    /**   创建时间              */
    private String commCdate        ;
    /**   更新时间              */
    private String commUdate        ;
    /**   创建人                */
    private String commCuser        ;
    /**   修改人                */
    private String commUuser        ;
    /**   是否删除标记          */
    private String commDelfalg      ;
    /**   扩展字段1             */
    private String commV1           ;
    /**   扩展字段1             */
    private String commV2           ;
    /**   扩展字段1             */
    private String commV3           ;
    /**   扩展字段1             */
    private String commV4           ;
    /**   扩展字段1'            */
    private String commV5           ;

    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }

    public String getExpIp() {
        return expIp;
    }

    public void setExpIp(String expIp) {
        this.expIp = expIp;
    }

    public String getExpPort() {
        return expPort;
    }

    public void setExpPort(String expPort) {
        this.expPort = expPort;
    }

    public String getExpClassName() {
        return expClassName;
    }

    public void setExpClassName(String expClassName) {
        this.expClassName = expClassName;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpMsg() {
        return expMsg;
    }

    public void setExpMsg(String expMsg) {
        this.expMsg = expMsg;
    }

    public String getExpTime() {
        return expTime;
    }

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }

    public String getCommCdate() {
        return commCdate;
    }

    public void setCommCdate(String commCdate) {
        this.commCdate = commCdate;
    }

    public String getCommUdate() {
        return commUdate;
    }

    public void setCommUdate(String commUdate) {
        this.commUdate = commUdate;
    }

    public String getCommCuser() {
        return commCuser;
    }

    public void setCommCuser(String commCuser) {
        this.commCuser = commCuser;
    }

    public String getCommUuser() {
        return commUuser;
    }

    public void setCommUuser(String commUuser) {
        this.commUuser = commUuser;
    }

    public String getCommDelfalg() {
        return commDelfalg;
    }

    public void setCommDelfalg(String commDelfalg) {
        this.commDelfalg = commDelfalg;
    }

    public String getCommV1() {
        return commV1;
    }

    public void setCommV1(String commV1) {
        this.commV1 = commV1;
    }

    public String getCommV2() {
        return commV2;
    }

    public void setCommV2(String commV2) {
        this.commV2 = commV2;
    }

    public String getCommV3() {
        return commV3;
    }

    public void setCommV3(String commV3) {
        this.commV3 = commV3;
    }

    public String getCommV4() {
        return commV4;
    }

    public void setCommV4(String commV4) {
        this.commV4 = commV4;
    }

    public String getCommV5() {
        return commV5;
    }

    public void setCommV5(String commV5) {
        this.commV5 = commV5;
    }
}
