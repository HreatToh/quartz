package com.csx.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csx.base.entity.BaseEntity;

/**
 * @author  Chengshx
 * @create  2021/12/1 15:29
 * @desc    系统角色
 **/
@TableName("sys_role_info")
public class SysRole extends BaseEntity {
    /** 系统编号    */
    private String sysId;
    /** 角色ID    */
    @TableId
    private String roleId;
    /** 角色名称    */
    private String roleName;
    /** 角色描述    */
    private String roleDesc;
    /** 角色创建时间    */
    private String roleTime;

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getRoleTime() {
        return roleTime;
    }

    public void setRoleTime(String roleTime) {
        this.roleTime = roleTime;
    }
}
