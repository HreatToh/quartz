package com.csx.base.entity;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class BaseEntity implements Serializable {

    /** '创建时间'                   */
    private String commCdate            ;
    /** '更新时间'                   */
    private String commUdate            ;
    /** '创建人'                     */
    private String commCuser            ;
    /** '修改人'                     */
    private String commUuser            ;
    /** '是否删除标记'               */
    private String commDelfalg          ;
    /** '扩展字段1'                  */
    private String commV1               ;
    /** '扩展字段2'                  */
    private String commV2               ;
    /** '扩展字段3'                  */
    private String commV3               ;
    /** '扩展字段4'                  */
    private String commV4               ;
    /** '扩展字段5'                  */
    private String commV5               ;

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
    /**
     * 转换成 json
     * @return
     */
    public String json() {
        return JSONUtil.toJsonStr(this);
    }

    /**
     * 转换成map
     * @return
     */
    public Map<String , Object > map(){
        return JSONUtil.toBean(JSONUtil.toJsonStr(this) , LinkedHashMap.class);
    }
    /**
     * 转换成 Bean
     * @return
     */
    public <T> T  parseJson(String json){
        return (T) JSONUtil.toBean(json , this.getClass());
    }
}
