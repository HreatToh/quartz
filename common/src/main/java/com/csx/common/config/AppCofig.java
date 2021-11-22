package com.csx.common.config;

import com.csx.common.entity.SysConfig;
import com.csx.common.enums.EvmentEnum;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class AppCofig {

    /**
     * 系统主配置
     */
    private static final ThreadLocal<Map<String , SysConfig>> sysConfig = new ThreadLocal<Map<String , SysConfig>>();

    /**
     * 子系统配置
     */
    private static final ThreadLocal<Map<String , SysConfig>> sysSubConfig = new ThreadLocal<Map<String , SysConfig>>();

    /**
     * 环境信息
     */
    public static EvmentEnum evmentEnum;
    /**
     * @method  getSysConfigList
     * @params  []
     * @return  SysConfig
     * @desc    返回系统主配置信息
     **/
    public static List<SysConfig> getSysConfigList(){
        List<SysConfig> lists = new ArrayList<SysConfig>();
        Map<String, SysConfig> map = sysConfig.get();

        if (ToolUtils.isNull(map)){
            log.warn("系统配置集合为：[]");
            return lists;
        }

        for (Map.Entry<String , SysConfig> entry : map.entrySet()) {
            lists.add(entry.getValue());
        }
        return lists;
    }
    /**
     * @method  getSysConfigMap
     * @params
     * @return  Map<String , String>
     * @desc    获取配置map
     **/
    public static Map<String , String> getSysConfigMap(){
        Map<String , String > configs = new HashMap<String , String >();
        Map<String, SysConfig> map = sysConfig.get();

        if (ToolUtils.isNull(map)){
            log.warn("系统配置集合为：[]");
            return configs;
        }

        for (Map.Entry<String , SysConfig> entry : map.entrySet()) {
            String itemId = entry.getValue().getItemId();
            String itemVal = ToolUtils.nvl(entry.getValue().getItemVal() , entry.getValue().getItemDefval());
            configs.put(itemId , itemVal);
        }
        return configs;
    }

    /**
     * @method  getSysConfig
     * @params  itemId
     * @return  String
     * @desc    返回系统配置信息
     **/
    public static String getSysConfig(String itemId ){
        return getSysConfig(itemId , "");
    }

    /**
     * @method  getSysConfig
     * @params  itemId
     * @return  String
     * @desc    返回系统配置信息
     **/
    public static String getSysConfig(String itemId , String itemDefVal){
        String value = "";
        Map<String, SysConfig> configMap = sysConfig.get();
        if (ToolUtils.isNull(configMap) || ToolUtils.isNull(configMap.get(itemId))){
            value = ToolUtils.nvl( itemDefVal , "");
            log.warn(ToolUtils.format("配置项[{}]不存在，取自定义默认值：{}" , itemId , value));
            return value;
        }

        value = configMap.get(itemId).getItemVal();
        if (ToolUtils.isBlank(value)){
            value = ToolUtils.nvl( configMap.get(itemId).getItemDefval() , itemDefVal);
            value = ToolUtils.nvl( value , "" );
            log.warn(ToolUtils.format("配置项[{}]存在，但值为空，取系统默认值：{}" , itemId , value));
            return value;
        }
        return value;
    }

    /**
     * @method  getSysSubConfigList
     * @params  []
     * @return  SysConfig
     * @desc    返回子系统配置信息
     **/
    public static List<SysConfig> getSysSubConfigList(String sysId){
        List<SysConfig> list = new ArrayList<SysConfig>();
        Map<String , SysConfig> configMap = sysSubConfig.get();
        if ( ToolUtils.isNull(configMap) ){
            log.warn("子系统配置集合为：[]");
            return list;
        }
        if ( ToolUtils.isNull(sysId) ){
            log.warn("子系统编号不能为空.");
            return list;
        }
        for (Map.Entry<String , SysConfig> entry : configMap.entrySet() ) {
            String key = entry.getKey();
            SysConfig value = entry.getValue();
            if (ToolUtils.startWith(key , sysId , true)) {
                list.add(value);
            }
        }

        if (list.isEmpty()){
            log.warn(ToolUtils.format("该系统编号[{}] 没有配置信息." , sysId));
            return list;
        }

        return list;
    }

    /**
     * @method  getSysSubConfigMap
     * @params  []
     * @return  SysConfig
     * @desc    返回子系统配置信息
     **/
    public static Map< String , String > getSysSubConfigMap(String sysId){
        Map<String,String> configs = new HashMap<String , String>();
        Map<String, SysConfig> map = sysSubConfig.get();

        if ( ToolUtils.isNull(map) ){
            log.warn("子系统配置集合为：[]");
            return configs;
        }
        if ( ToolUtils.isNull(sysId) ){
            log.warn("子系统编号不能为空.");
            return configs;
        }

        for (Map.Entry<String , SysConfig> entry : map.entrySet() ) {
            String key = entry.getKey();
            SysConfig value = entry.getValue();
            if (ToolUtils.startWith(key , sysId , true)) {
                String itemId = entry.getValue().getItemId();
                String itemVal = ToolUtils.nvl(entry.getValue().getItemVal() , entry.getValue().getItemDefval());
                configs.put(itemId , itemVal);
            }
        }

        if (configs.isEmpty()){
            log.warn(ToolUtils.format("该系统编号[{}] 没有配置信息." , sysId));
            return configs;
        }
        return configs;
    }

    /**
     * 根据Key获取对应的配置信息
     * @param sysId
     * @param itemId
     * @return
     */
    public static String getSysSubConfig(String sysId , String itemId){
        return getSysSubConfig( sysId , itemId , "" );
    }

    /**
     * 根据Key获取对应的配置信息
     * @param sysId
     * @param itemId
     * @param itemDefVal
     * @return
     */
    public static String getSysSubConfig(String sysId , String itemId , String itemDefVal){
        String key = ToolUtils.format("{}.{}" , sysId , itemId );
        String value = "";
        Map<String, SysConfig> configs = sysSubConfig.get();

        if ( ToolUtils.isNull(configs) || ToolUtils.isNull(sysId) || ToolUtils.isNull(itemId) || ToolUtils.isNull(configs.get(key))){
            value = ToolUtils.nvl( itemDefVal , "");
            log.warn(ToolUtils.format("配置项[{}]不存在，取自定义默认值：{}" , key , itemDefVal));
            return value;
        }
        value = configs.get(key).getItemVal();

        if (ToolUtils.isBlank(value)){
            value = ToolUtils.nvl( configs.get(key).getItemDefval() , itemDefVal);
            value = ToolUtils.nvl( value , "" );
            log.warn(ToolUtils.format("配置项[{}]存在，但值为空，取默认值：{}" , itemId , value));
            return value;
        }

        return value;
    }
}
