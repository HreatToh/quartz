package com.csx.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csx.base.entity.BaseEntity;

/**
 * @author  Chengshx
 * @create  2021/12/2 15:46
 * @desc    系统日志实体
 **/
@TableName("sys_log_info")
public class SysLog extends BaseEntity {
    /**      系统编号         */
    private String sysId    ;
    /**      日志主键         */
    @TableId
    private String logId 	;
    /**      日志级别         */
    private String logLevel ;
    /**      日志类型         */
    private String logType  ;
    /**      日志信息         */
    private String logMsg   ;
    /**      日志其他信息     */
    private String logOther ;
    /**      日志创建时间     */
    private String logTime  ;

    public String getSysId() {
        return sysId;
    }

    private SysLog(){

    }

    public static SysLog newInstance() {
        return new SysLog();
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public String getLogOther() {
        return logOther;
    }

    public void setLogOther(String logOther) {
        this.logOther = logOther;
    }
}
