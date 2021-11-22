package com.csx.common.utils;


import com.csx.common.handler.CacheHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class CacheUtils {

    /** ehcache 缓存配置路径    */
    @Value("${spring.cache.ehcache.config}")
    private static String EHCACHE_CONF_PATH;
    /** ehcache 本地缓存名称    */
    @Value("${spring.cache.ehcache.localname}")
    private static String LOCAL_CACHE_NAME;

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
        if (ToolUtils.isNull(manager)){
            cacheManager = CacheManager.create(CacheUtils.class.getResourceAsStream(EHCACHE_CONF_PATH));
        } else {
            cacheManager = manager ;
        }
        if (ToolUtils.isNotNull(cacheManager)) {
            cache = cacheManager.getCache(LOCAL_CACHE_NAME);
        }
    }


    /**
     * @method  put
     * @params  String key, Object value
     * @return
     * @desc    把一个元素添加到Cache中
     **/
    public void put(String key, Object value) {
        try {
            initCacheManager();
            cache.put(new Element(key, value));
        } catch ( Exception e ){
            log.error(ToolUtils.format("key : {} , value : {} put发生异常！" , key , value) , e);
        } finally {
            flushAndShutdown();
        }
    }

    /**
     * @method  get
     * @params  String key
     * @return  Object
     * @desc    获取缓存元素
     **/
    public Object get(String key) {
        Object o = null;
        try {
            initCacheManager();
            Element ele = cache.get(key);// 根据Key获取缓存元素
            o = ToolUtils.isNotNull(ele) ? ele.getObjectValue() : null;
        } catch (Exception e){
            log.error(ToolUtils.format("key : {} , value : {} get发生异常！" , key , o ) , e);
        } finally {
            flushAndShutdown();
        }
        return o;
    }

    /**
     * @method  get
     * @params  String key , CacheHandler handler
     * @return  Object
     * @desc    获取缓存元素
     **/
    public Object get(String key , CacheHandler handler ) {
        Object o = null;
        try {
            initCacheManager();
            Element ele = cache.get(key);// 根据Key获取缓存元素
            o = ToolUtils.isNotNull(ele) ? ele.getObjectValue() : null;

            if (ToolUtils.isNull( o ) && ToolUtils.isNotNull( handler )){
                o = handler.handler();
                cache.put(new Element(key, o));
            }
        } catch (Exception e){
            log.error(ToolUtils.format("key : {} , value : {} get发生异常！" , key , o ) , e);
        } finally {
            flushAndShutdown();
        }
        return o;
    }
    /**
     * @method  remove
     * @params  String key
     * @return  
     * @desc    删除缓存
     **/
    public void remove(String key) {
        try {
            initCacheManager();
            cache.remove(key);//移除缓存
        } catch (Exception e){
            log.error(ToolUtils.format("key : {} , value : {} remove发生异常！" , key ) , e);
        } finally {
            flushAndShutdown();
        }
    }


    /**
     * @method  removeAll
     * @params  
     * @return  
     * @desc    移除所有缓存
     **/
    public void removeAll() {
        try {
            initCacheManager();
            cache.removeAll();//移除缓存
        } catch (Exception e){
            log.error(ToolUtils.format("key : {} , value : {} removeAll发生异常！" ) , e);
        } finally {
            flushAndShutdown();
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
