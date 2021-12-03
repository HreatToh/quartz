package com.csx.common.utils;


import com.csx.common.handler.CacheHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;

import java.io.InputStream;

@Slf4j
public class CacheUtils {

    /** ehcache 缓存配置路径    */
    public static String EHCACHE_CONF_PATH;
    /** ehcache 本地缓存名称    */
    public static String LOCAL_CACHE_NAME;

    /** 缓存管理器    */
    private static CacheManager cacheManager;
    /** 缓存对象 */
    private static Cache cache;
    
    /**
     * @method  initCacheManager
     * @params
     * @return
     * @desc    初始化缓存管理器
     **/
    public static void initCacheManager(){
        initCacheManager(null);
    }
    /**
     * @method  initCacheManager
     * @params  CacheManager
     * @return
     * @desc    初始化缓存管理器
     **/
    public static void initCacheManager(CacheManager manager){
        try{
            if (ToolUtils.isNull(manager)){
                cacheManager = CacheManager.create();
            } else {
                cacheManager = manager ;
            }
            if (ToolUtils.isNotNull(cacheManager)) {
                cache = cacheManager.getCache(LOCAL_CACHE_NAME);
            }
            cache.removeAll();
        } catch (Exception e){
            log.error(ToolUtils.format("初始化[{}]异常！" , cacheManager ) , e);
        }
    }


    /**
     * @method  put
     * @params  String key, Object value
     * @return
     * @desc    把一个元素添加到Cache中
     **/
    public static void put(String key, Object value) {
        try {
            cache.put(new Element(key, value));
        } catch ( Exception e ){
            log.error(ToolUtils.format("key : {} , value : {} put发生异常！" , key , value) , e);
        } finally {
            cache.flush();
        }
    }

    /**
     * @method  get
     * @params  String key
     * @return  Object
     * @desc    获取缓存元素
     **/
    public static Object get(String key) {
        Object o = null;
        try {
            Element ele = cache.get(key);// 根据Key获取缓存元素
            o = ToolUtils.isNotNull(ele) ? ele.getObjectValue() : null;
        } catch (Exception e){
            log.error(ToolUtils.format("key : {} , value : {} get发生异常！" , key , o ) , e);
        } finally {
            cache.flush();
        }
        return o;
    }

    /**
     * @method  get
     * @params  String key , CacheHandler handler
     * @return  Object
     * @desc    获取缓存元素
     **/
    public static Object get(String key , CacheHandler handler ) {
        Object o = null;
        try {
            Element ele = cache.get(key);// 根据Key获取缓存元素
            o = ToolUtils.isNotNull(ele) ? ele.getObjectValue() : null;

            if (ToolUtils.isNull( o ) && ToolUtils.isNotNull( handler )){
                o = handler.handler();
                cache.put(new Element(key, o));
                log.debug(ToolUtils.format("缓存[{}]加载成功！" , key ));
            }
        } catch (Exception e){
            log.error(ToolUtils.format("key : {} , value : {} get发生异常！" , key , o ) , e);
        } finally {
            cache.flush();
        }
        return o;
    }
    /**
     * @method  remove
     * @params  String key
     * @return  
     * @desc    删除缓存
     **/
    public static void remove(String key) {
        try {
            cache.remove(key);//移除缓存
        } catch (Exception e){
            log.error(ToolUtils.format("key : {} , value : {} remove发生异常！" , key ) , e);
        } finally {
            cache.flush();
        }
    }


    /**
     * @method  removeAll
     * @params  
     * @return  
     * @desc    移除所有缓存
     **/
    public static void removeAll() {
        try {
            cache.removeAll();//移除缓存
        } catch (Exception e){
            log.error(ToolUtils.format("key : {} , value : {} removeAll发生异常！" ) , e);
        } finally {
            cache.flush();
        }
    }

    /**
     * @method flushAndShutdown
     * @params
     * @return
     * @desc 构建内存与磁盘的关系，每次get、put、remove都要调用一次
     *       注意：需要和init方法成双对执行
     **/
    public static void flushAndShutdown() {
        //构建内存与磁盘索引
        if (ToolUtils.isNotNull(cache)){
            cache.flush();
            cache = null;
        }
        //关闭缓存管理器，调用后，manager对象需要重新create
        if (ToolUtils.isNotNull(cacheManager) && Status.STATUS_ALIVE.equals(cacheManager.getStatus())) {
            cacheManager.shutdown();
            cacheManager = null;
        }
    }
}
