package com.csx.common.other;

import java.util.List;

/**
 * @author  Chengshx
 * @create  2021/12/9 15:35
 * @desc    TreeNode 树形结构
 **/
public class TreeNode {
    /** 节点Id    */
    private String id ;
    /** 节点名称    */
    private String name;
    /** 节点标题    */
    private String title;
    /** 父节点Id    */
    private String pid;
    /** 节点图标    */
    private String icon;
    /** 节点自定义图标的 className    */
    private String iconSkin;
    /** 节点跳转地址    */
    private String url;
    /** 节点类型    */
    private String type;
    /** 节点序号    */
    private Integer sort;
    /** 记录 treeNode 节点的 展开 / 折叠 状态。    */
    private Boolean open;
    /** 是否选中    */
    private Boolean checked;
    /** 所有子集        */
    private List<TreeNode> children;

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
