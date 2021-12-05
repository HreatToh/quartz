package com.csx.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csx.base.entity.BaseEntity;
import com.csx.common.utils.ToolUtils;

/**
 * @author  Chengshx
 * @create  2021/12/1 15:31
 * @desc    SysPermission 权限
 **/
@TableName("sys_permission_info")
public class SysPermission extends BaseEntity {
    /** 系统编号    */
    private String sysId;
    /** 权限类型：user代表用户 ，role代表角色    */
    private String permissionType;
    /** 对象ID    */
    private String permissionObjectId;
    /** 资源ID    */
    private String permissionResourceId;
    /** 资源类型    */
    private String permissionResourceType;
    /** 资源权限：add-新增,del-删除,update-修改,view-查看,deal-处理...    */
    private String permissionResourceHandle;
    /** 资源权限：add-新增,del-删除,update-修改,view-查看,deal-处理...  例如：menu:add  */
    @TableField(exist = false)
    private String[] permissionResourceHandles;
    /** 权限创建时间    */
    private String permissionTime;

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public String getPermissionObjectId() {
        return permissionObjectId;
    }

    public void setPermissionObjectId(String permissionObjectId) {
        this.permissionObjectId = permissionObjectId;
    }

    public String getPermissionResourceId() {
        return permissionResourceId;
    }

    public void setPermissionResourceId(String permissionResourceId) {
        this.permissionResourceId = permissionResourceId;
    }

    public String getPermissionResourceType() {
        return permissionResourceType;
    }

    public void setPermissionResourceType(String permissionResourceType) {
        this.permissionResourceType = permissionResourceType;
    }

    public String getPermissionTime() {
        return permissionTime;
    }

    public void setPermissionTime(String permissionTime) {
        this.permissionTime = permissionTime;
    }

    public String getPermissionResourceHandle() {
        return permissionResourceHandle;
    }

    public void setPermissionResourceHandle(String permissionResourceHandle) {
        /** 设置权限组    */
        if (ToolUtils.isNotNull(permissionResourceHandle) && ToolUtils.isNotNull(getPermissionResourceType())) {
            String[] permissionResourceHandles = permissionResourceHandle.split(",");
            for (int i = 0; i < permissionResourceHandles.length; i++) {
                permissionResourceHandles[i] = getPermissionResourceType() + ":" + permissionResourceHandles[i];
            }
            setPermissionResourceHandles(permissionResourceHandles);
        }
        this.permissionResourceHandle = permissionResourceHandle;
    }

    public String[] getPermissionResourceHandles() {
        return permissionResourceHandles;
    }

    public void setPermissionResourceHandles(String[] permissionResourceHandles) {
        this.permissionResourceHandles = permissionResourceHandles;
    }
}
