package com.csx.common.base.entity;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
public class BaseEntity implements Serializable {

    /**
     * 转换成 json
     * @return
     */
    public String json() {
        return JSONUtil.toJsonStr(this);
    }
    /**
     * 转换成 Bean
     * @return
     */
    public <T> T  parseJson(String json){
        return (T) JSONUtil.toBean(json , this.getClass());
    }
}
