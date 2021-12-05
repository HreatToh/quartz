package com.csx.common.other;

import cn.hutool.json.JSONUtil;

import java.util.HashMap;

/**
 * @author  Chengshx
 * @create  2021/12/5
 * @desc    JsonMap 返回json对象的Map
 **/
public class JsonMap<K,V> {

    private HashMap<K,V> map;

    private JsonMap(){
        map = new HashMap<K,V>();
    };

    /**
     * @method  newInstance
     * @params  
     * @return  JsonMap
     * @desc    初始化
     **/
    public static JsonMap newInstance(){
        return new JsonMap();
    }

    /**
     * @method  append
     * @params  K key , V value
     * @return  JsonMap
     * @desc    put 的方法
     **/
    public JsonMap append(K key , V value ){
        this.map.put(key , value);
        return this;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this.map);
    }
}
