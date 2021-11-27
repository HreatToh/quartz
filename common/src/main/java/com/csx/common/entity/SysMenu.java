package com.csx.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csx.base.entity.BaseEntity;

/**
 * @author  Chengshx
 * @create  2021/11/22 17:21
 * @desc    SysMenu 菜单对象
 **/
@TableName("SYS_MENU_INFO")
public class SysMenu extends BaseEntity {
    /** '系统编号'                   */
    private String sysId                ;
    /** '菜单编号'                   */
    @TableId
    private String menuId               ;
    /** '菜单名称'                   */
    private String menuName             ;
    /** '菜单类型'                   */
    private String menuType             ;
    /** '菜单图标'                   */
    private String menuIcon             ;
    /** '请求地址'                   */
    private String menuUrl              ;
    /** '上级菜单编号'               */
    private String menuParentId         ;
    /** '上级路径'                   */
    private String menuPath             ;
    /** '序号'                       */
    private String menuOrder            ;
    /** '菜单初始化Service名称'      */
    private String menuClass            ;
    /** '窗口渲染模式'               */
    private String menuTarget           ;

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(String menuParentId) {
        this.menuParentId = menuParentId;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public String getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(String menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getMenuClass() {
        return menuClass;
    }

    public void setMenuClass(String menuClass) {
        this.menuClass = menuClass;
    }

    public String getMenuTarget() {
        return menuTarget;
    }

    public void setMenuTarget(String menuTarget) {
        this.menuTarget = menuTarget;
    }

}
