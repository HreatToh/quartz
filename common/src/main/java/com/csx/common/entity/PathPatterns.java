package com.csx.common.entity;

import com.csx.base.entity.BaseEntity;

/**
 * @author  Chengshx
 * @create  2021/11/23 10:18
 * @desc    PathPatterns 路径过滤器
 **/
public class PathPatterns extends BaseEntity {

    /** include 拦截路径    */
    private String[] include;
    /** exclude 放行路径    */
    private String[] exclude;

    public String[] getInclude() {
        return include;
    }

    public void setInclude(String[] include) {
        this.include = include;
    }

    public String[] getExclude() {
        return exclude;
    }

    public void setExclude(String[] exclude) {
        this.exclude = exclude;
    }
}
