package com.csx.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csx.base.entity.BaseEntity;

/**
 * @author  Chengshx
 * @create  2021/11/26 15:18
 * @desc    SysSystem 创建子系统类
 **/
@TableName("sys_system_info")
public class SysSystem extends BaseEntity {

    /**   系统名称       */
    @TableId
    private String sysId       ;
    /**   系统名称       */
    private String sysName     ;
    /**   系统接口地址   */
    private String sysUrl      ;
    /**   系统父级菜单id */
    private String sysParentId ;
    /**   系统依赖路径   */
    private String sysPath     ;
    /**   系统图标       */
    private String sysIcon     ;
    /**   系统开窗口模式 */
    private String sysTarget   ;
    /**   系统排序       */
    private String sysOrder    ;

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getSysUrl() {
        return sysUrl;
    }

    public void setSysUrl(String sysUrl) {
        this.sysUrl = sysUrl;
    }

    public String getSysParentId() {
        return sysParentId;
    }

    public void setSysParentId(String sysParentId) {
        this.sysParentId = sysParentId;
    }

    public String getSysPath() {
        return sysPath;
    }

    public void setSysPath(String sysPath) {
        this.sysPath = sysPath;
    }

    public String getSysIcon() {
        return sysIcon;
    }

    public void setSysIcon(String sysIcon) {
        this.sysIcon = sysIcon;
    }

    public String getSysTarget() {
        return sysTarget;
    }

    public void setSysTarget(String sysTarget) {
        this.sysTarget = sysTarget;
    }

    public String getSysOrder() {
        return sysOrder;
    }

    public void setSysOrder(String sysOrder) {
        this.sysOrder = sysOrder;
    }
}
