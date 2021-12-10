package com.csx.common.other;

import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author  Chengshx
 * @create  2021/12/5
 * @desc    JsonMap 返回json对象的Map
 **/
public class JsonMap<K,V> {

    private Map<K,V> map;

    private JsonMap(){
        map = new HashMap<K,V>();
    };

    private JsonMap(Boolean isLinked){
        if (isLinked) {
            map = new LinkedHashMap<K,V>();
        }else{
            map = new HashMap<K,V>();
        }
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
     * @method  newInstance
     * @params  
     * @return  JsonMap
     * @desc    初始化
     **/
    public static JsonMap newInstance(Boolean isLinked){
        return new JsonMap(isLinked);
    }


    /**
     * 构建map
     * @param objects
     * @return
     */
    public static JsonMap build(Object... objects){
        if (objects == null) {
            return newInstance(false);
        }
        JsonMap jsonMap = newInstance(false);
        for (int i = 0; i < (objects.length % 2 == 0 ? objects.length / 2 : objects.length / 2 + 1) ; i++) {
            if ((i * 2 + 1) > objects.length) {
                jsonMap.append(objects[i * 2] , "");
            }else {
                jsonMap.append(objects[i * 2] , objects[i * 2 + 1]);
            }
        }
        return jsonMap;
    }

    /**
     * 获取当前map
     * @return
     */
    public Map<K , V> get(){
        return map;
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
    /**
     * @method  append
     * @params  K key , V value
     * @return  JsonMap
     * @desc    putAll 的方法
     **/
    public JsonMap appendAll( Map<K, V> map ){
        this.map.putAll(map);
        return this;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this.map);
    }
}
