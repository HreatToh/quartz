package com.csx.common.other;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import com.csx.common.entity.SysDict;

import java.util.Map;

/**
 * @author  Chengshx
 * @create  2021/12/10 12:06
 * @desc    权限的常量
 **/
public class Permission {

    /** 权限常量 默认系统编号     */
    public static final String sysId = "SYSMM";

    /** 权限类型  map键值  */
    public static final String TYPE = "PERMISSION_TYPE";
    /** 权限资源类型 map键值    */
    public static final String RESOURCE_TYPE = "PERMISSION_RESOURCE_TYPE";

    /**
     * 对应 sys_permission_info 的 permission_type 字段键值
     */
    public static class Type{
        /** 代表用户    */
        public static final String user = "user";
        /** 代表角色    */
        public static final String role = "role";
        /** 获取type的字典map    */
        public static final Map<String , SysDict> map = JsonMap .build(
                user , new SysDict(sysId , user , "用户权限") ,
                role , new SysDict(sysId , role , "角色权限")
        ).get();
    }


    /**
     * 对应 sys_permission_info 的 permission_resource_type 字段键值
     */
    public static class resourceType{
        /** 代表该资源是menu类型的    */
        public static final String menu = "menu";
        /** 代表该资源是menu里面的按钮类型的    */
        public static final String menuBtn = "menuBtn";
        /** 代表该资源是role类型的    */
        public static final String role = "role";
        /** 获取resourceType的字典map    */
        public static final Map<String , SysDict> map = JsonMap .build(
                menu , new SysDict(sysId , menu    , "菜单资源") ,
                menuBtn , new SysDict(sysId , menuBtn , "按钮资源") ,
                role , new SysDict(sysId , role    , "角色资源")
        ).get();
        /** 构建   resourceType 如果多个type以逗号分隔  */
        public static String build(String... type){
            return ArrayUtil.join(type , ",");
        }
    }
}
