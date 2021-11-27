package com.csx.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csx.base.entity.BaseEntity;

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

}
