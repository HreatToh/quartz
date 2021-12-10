package com.csx.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csx.base.entity.BaseEntity;

/**
 * @author  Chengshx
 * @create  2021/12/1 15:29
 * @desc    SysUser 系统用户
 **/
@TableName("sys_user_info")
public class SysUser extends BaseEntity {

    /**   用户id                      */
    @TableId
    private String userId           ;
    /**   用户英文名                  */
    private String userEnname       ;
    /**   用户中文名                  */
    private String userChname       ;
    /**   用户密码                    */
    private String userPassword     ;
    /**   性别                        */
    private String userSex          ;
    /**   出生年月                    */
    private String userBirthdate    ;
    /**   电话号码                    */
    private String userTel          ;
    /**   用户邮箱                    */
    private String userEmail        ;
    /**   用户地址                    */
    private String userAddr         ;
    /**   是否第一次登录: Y-是 N-否   */
    private String userIsFirst      ;
    /**   登录时间                    */
    private String userLoginTime;
    /**   密码错误次数                */
    private String userErrorNum     ;
    /**   是否锁定: Y-是 N-否         */
    private String userIsLock       ;
    /**   是否在线: Y-是 N-否         */
    private String userIsOnline;
    /**   用户密码加盐                */
    @TableField( exist = false )
    private String salt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEnname() {
        return userEnname;
    }

    public void setUserEnname(String userEnname) {
        this.userEnname = userEnname;
    }

    public String getUserChname() {
        return userChname;
    }

    public void setUserChname(String userChname) {
        this.userChname = userChname;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserBirthdate() {
        return userBirthdate;
    }

    public void setUserBirthdate(String userBirthdate) {
        this.userBirthdate = userBirthdate;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getUserIsFirst() {
        return userIsFirst;
    }

    public void setUserIsFirst(String userIsFirst) {
        this.userIsFirst = userIsFirst;
    }

    public String getUserLoginTime() {
        return userLoginTime;
    }

    public void setUserLoginTime(String userLoginTime) {
        this.userLoginTime = userLoginTime;
    }

    public String getUserErrorNum() {
        return userErrorNum;
    }

    public void setUserErrorNum(String userErrorNum) {
        this.userErrorNum = userErrorNum;
    }

    public String getUserIsLock() {
        return userIsLock;
    }

    public void setUserIsLock(String userIsLock) {
        this.userIsLock = userIsLock;
    }

    public String getUserIsOnline() {
        return userIsOnline;
    }

    public void setUserIsOnline(String userIsOnline) {
        this.userIsOnline = userIsOnline;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
