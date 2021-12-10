package com.csx.common.other;

import com.csx.common.utils.ToolUtils;

import java.io.File;

/**
 * @author  Chengshx
 * @create  2021/11/18 14:23
 * @desc    常量类
 **/
public class Constants {

    /**
     * Session 配置常量集
     */
    public static class Session{
        /** 管理员用户常量    */
        public static final String ADMIN = "admin";
        /** Session存储user的key值    */
        public static final String SESSION_USER_KEY = "QUARTZ_SESSION_USER";
        /** Session存储token的key值    */
        public static final String SESSION_TOKEN_KEY = "QUARTZ_SESSION_TOKEN";
    }

    /**
     * 系统配置常量集
     */
    public static class App{
        /** 存放在 Header 内的token键值    */
        public static final String HEADER_AUTHORIZATION = "Authorization";
        /** 登录页验证码键值    */
        public static final String KAPTCHA_CODE_KEY = "kaptchaCode";
        /** 删除标识 N 不删除    */
        public static final String DELFALG_N = "N";
        /** 删除标识 Y 删除    */
        public static final String DELFALG_Y = "Y";
        /** 标识 N     */
        public static final String N = "N";
        /** 标识 Y    */
        public static final String Y = "Y";
        /** 首页地址    */
        public static final String SYS_HOME_PAGE_URL = "SYS_HOME_PAGE_URL";
        /** 左侧Side图标    */
        public static final String SYS_HOME_SIDE_ICON = "SYS_HOME_SIDE_ICON";
        /** 左侧Side标题    */
        public static final String SYS_HOME_SIDE_TITLE = "SYS_HOME_SIDE_TITLE";
        /** 登录页标题    */
        public static final String SYS_LOGIN_PAGE_TITLE = "SYS_LOGIN_PAGE_TITLE";
        /** 密码过期时间    */
        public static final String SYS_LOGIN_PWDOVER = "SYS_LOGIN_PWDOVER";
        /** 密码是否加密认证    */
        public static final String SYS_LOGIN_PWDSIGN = "SYS_LOGIN_PWDSIGN";
        /** 密码错误次数    */
        public static final String SYS_LOGIN_PWDERRNUM = "SYS_LOGIN_PWDERRNUM";
        /** 是否是多用户登录 Y 是 N 否    */
        public static final String SYS_LOGIN_ISMULTI = "SYS_LOGIN_ISMULTI";
        /** 系统认证    */
        public static final String SYS_LICENCE = "SYS_LICENCE";

        /**  自定义的初始化接口          */
        public static final String SYS_HOME_OPTIONS_INIURL = "SYS_HOME_OPTIONS_INIURL";
        /**  自定义的缓存清理接口        */
        public static final String SYS_HOME_OPTIONS_CLEARURL = "SYS_HOME_OPTIONS_CLEARURL";
        /** 自定义的认证接口    */
        public static final String SYS_HOME_OPTIONS_LICENCEURL = "SYS_HOME_OPTIONS_LICENCEURL";
        /**  自定义的是否打开hash定位    */
        public static final String SYS_HOME_OPTIONS_URLHASHLOCATION = "SYS_HOME_OPTIONS_URLHASHLOCATION";
        /**  自定义的主题默认配置        */
        public static final String SYS_HOME_OPTIONS_BGCOLORDEFAULT = "SYS_HOME_OPTIONS_BGCOLORDEFAULT";
        /**  自定义的是否开启多模块      */
        public static final String SYS_HOME_OPTIONS_MULTIMODULE = "SYS_HOME_OPTIONS_MULTIMODULE";
        /**  自定义的是否默认展开菜单    */
        public static final String SYS_HOME_OPTIONS_MENUCHILDOPEN = "SYS_HOME_OPTIONS_MENUCHILDOPEN";
        /**  自定义的初始化加载时间      */
        public static final String SYS_HOME_OPTIONS_LOADINGTIME = "SYS_HOME_OPTIONS_LOADINGTIME";
        /**  自定义的iframe窗口动画      */
        public static final String SYS_HOME_OPTIONS_PAGEANIM = "SYS_HOME_OPTIONS_PAGEANIM";
        /**  自定义的最大的tab打开数量   */
        public static final String SYS_HOME_OPTIONS_MAXTABNUM = "SYS_HOME_OPTIONS_MAXTABNUM";
        /** 首页窗口标题    */
        public static final String SYS_HOME_PAGE_TITLE = "SYS_HOME_PAGE_TITLE";

        /** 回车换行符 \n\r   */
        public static final String $N$R = "\n\r";
        /** 斜杠 /   */
        public static final String $I$R = "/";
        /** 反斜杠 \   */
        public static final String $A$I$R = "\\";
        /** 空白字符 ""   */
        public static final String $BLANK = "";
        /** NONE 无    */
        public static final String $NONE = "NONE";
        /** 允许登录的最大在线人数    */
        public static final String SYS_LOGIN_MAX_ONLINE_NUM = "SYS_LOGIN_MAX_ONLINE_NUM";
    }


    /**
     * 缓存键值
     */
    public static class Cache{
        /** 缓存系统配置    */
        public static final String CACHE_SYS_CONFIG = "CACHE_SYS_CONFIG";
        /** 缓存子系统配置    */
        public static final String CACHE_SYS_SUBCONFIG = "CACHE_SYS_SUBCONFIG";
        /** 缓存当前用户的菜单    */
        public static final String CACHE_CURRENT_SYS_MENU = "CACHE_CURRENT_SYS_MENU";
        /** 缓存当前用户的系统信息    */
        public static final String CACHE_CURRENT_SYS_SYSTEM = "CACHE_CURRENT_SYS_SYSTEM";
        /** 缓存所有子系统信息    */
        public static final String CACHE_SYS_SYSTEM = "CACHE_SYS_SYSTEM";
        /** 缓存所有字典信息    */
        public static final String CACHE_DICT_MAP = "CACHE_DICT_MAP";
    }


    /**
     * 日期格式
     */
    public static class Date{
        /** G 日期或时间元素：Era标志符               类型：Text               例：AD                                         */
        public static String FORMAT_JAVA_G = "G";
        /** y 日期或时间元素：年                      类型：Year               例： 1996;96                                   */
        public static String FORMAT_JAVA_y = "y";
        /** M 日期或时间元素：年中的月份              类型：Month              例：July;Jul;07                                */
        public static String FORMAT_JAVA_M = "M";
        /** w 日期或时间元素：年中的周数              类型：Number             例：27                                         */
        public static String FORMAT_JAVA_w = "w";
        /** W 日期或时间元素：月份中的周数            类型：Number             例：2                                          */
        public static String FORMAT_JAVA_W = "W";
        /** D 日期或时间元素：年中的天数              类型：Number             例：189                                        */
        public static String FORMAT_JAVA_D = "D";
        /** d 日期或时间元素：月份中的天数            类型：Number             例：10                                         */
        public static String FORMAT_JAVA_d = "d";
        /** F 日期或时间元素：月份中的星期            类型：Number             例：2                                          */
        public static String FORMAT_JAVA_F = "F";
        /** E 日期或时间元素：星期中的天数            类型：Text               例：Tuesday;Tue                                */
        public static String FORMAT_JAVA_E = "E";
        /** a 日期或时间元素：Am/pm 标记              类型：PM                 例： PM                                        */
        public static String FORMAT_JAVA_a = "a";
        /** H 日期或时间元素：一天中的小时数（0-23）  类型：Number             例：0                                          */
        public static String FORMAT_JAVA_H = "H";
        /** k 日期或时间元素：一天中的小时数（1-24）  类型：Number             例：24                                         */
        public static String FORMAT_JAVA_k = "k";
        /** K 日期或时间元素：am/pm中的小时数（0-11） 类型：Number             例：0                                          */
        public static String FORMAT_JAVA_K = "K";
        /** h 日期或时间元素：am/pm中的小时数（1-12） 类型：Number             例：12                                         */
        public static String FORMAT_JAVA_h = "h";
        /** m 日期或时间元素：小时中的分钟数          类型：Number             例：30                                         */
        public static String FORMAT_JAVA_m = "m";
        /** s 日期或时间元素：分钟中的秒数            类型：Number             例：55                                         */
        public static String FORMAT_JAVA_s = "s";
        /** S 日期或时间元素：毫秒数                  类型：Number             例： 978                                       */
        public static String FORMAT_JAVA_S = "S";
        /** z 日期或时间元素：时区                    类型：General time	zone  例： Pacific	Standard Time; PST;GMT-08:00  */
        public static String FORMAT_JAVA_z = "z";
        /** Z 日期或时间元素：时区                    类型：RFC 822 time zone  例：-800                                       */
        public static String FORMAT_JAVA_Z = "Z";
        /** 日期格式 yyyy  年  */
        public static final String FORMAT_JAVA_YYYY = "yyyy";
        /** 日期格式 MM 月    */
        public static final String FORMAT_JAVA_MM = "MM";
        /** 日期格式 dd 日    */
        public static final String FORMAT_JAVA_dd = "dd";
        /** 时间格式 HH 小时    */
        public static final String FORMAT_JAVA_HH = "HH";
        /** 时间格式 mm 分钟    */
        public static final String FORMAT_JAVA_mm = "mm";
        /** 时间格式 ss 秒    */
        public static final String FORMAT_JAVA_ss = "ss";
        /** 日期格式  yyyyMM   */
        public static final String FORMAT_JAVA_06 = ToolUtils.format("{}{}" , FORMAT_JAVA_YYYY, FORMAT_JAVA_MM);
        /** 日期格式  yyyyMMdd   */
        public static final String FORMAT_JAVA_08 = ToolUtils.format("{}{}{}" , FORMAT_JAVA_YYYY, FORMAT_JAVA_MM, FORMAT_JAVA_dd);
        /** 日期格式  yyyy-MM-dd   */
        public static final String FORMAT_JAVA_10 = ToolUtils.format("{}-{}-{}" , FORMAT_JAVA_YYYY, FORMAT_JAVA_MM, FORMAT_JAVA_dd);
        /** 日期格式  yyyy年MM月dd日   */
        public static final String FORMAT_JAVA_11 = ToolUtils.format("{}年{}月{}日" , FORMAT_JAVA_YYYY, FORMAT_JAVA_MM, FORMAT_JAVA_dd);
        /** 日期格式  yyyyMMddHHmmss   */
        public static final String FORMAT_JAVA_14 = ToolUtils.format("{}{}{}{}{}{}" , FORMAT_JAVA_YYYY, FORMAT_JAVA_MM, FORMAT_JAVA_dd, FORMAT_JAVA_HH , FORMAT_JAVA_mm , FORMAT_JAVA_ss);
        /** 日期格式  yyyyMMddHHmmssS   */
        public static final String FORMAT_JAVA_15 = ToolUtils.format("{}{}{}{}{}{}{}" , FORMAT_JAVA_YYYY, FORMAT_JAVA_MM, FORMAT_JAVA_dd, FORMAT_JAVA_HH , FORMAT_JAVA_mm , FORMAT_JAVA_ss , FORMAT_JAVA_S);
        /** 日期格式  yyyy-MM-dd HH:mm:ss   */
        public static final String FORMAT_JAVA_19 = ToolUtils.format("{}-{}-{} {}:{}:{}" , FORMAT_JAVA_YYYY, FORMAT_JAVA_MM, FORMAT_JAVA_dd, FORMAT_JAVA_HH , FORMAT_JAVA_mm , FORMAT_JAVA_ss );
        /** 日期格式  yyyy-MM-dd HH:mm:ss.S   */
        public static final String FORMAT_JAVA_20 = ToolUtils.format("{}-{}-{} {}:{}:{}.{}" , FORMAT_JAVA_YYYY, FORMAT_JAVA_MM, FORMAT_JAVA_dd, FORMAT_JAVA_HH , FORMAT_JAVA_mm , FORMAT_JAVA_ss , FORMAT_JAVA_S);
        /** 时间格式  HH:mm:ss   */
        public static final String FORMAT_JAVA_08T = ToolUtils.format("{}{}{}" , FORMAT_JAVA_HH, FORMAT_JAVA_mm, FORMAT_JAVA_ss);
        /** 时间格式  HH时mm分ss秒   */
        public static final String FORMAT_JAVA_09T = ToolUtils.format("{}时{}分{}秒" , FORMAT_JAVA_HH, FORMAT_JAVA_mm, FORMAT_JAVA_ss);
        /** 时间格式  HH时mm分ss秒S毫秒   */
        public static final String FORMAT_JAVA_12T = ToolUtils.format("{}时{}分{}秒{}毫秒" , FORMAT_JAVA_HH, FORMAT_JAVA_mm, FORMAT_JAVA_ss , FORMAT_JAVA_S);
    }


    /**
     * 分页常量
     */
    public static class Pager{

        /** 分页 当前页    */
        public static final String PAGE_NUM = "PAGE_NUM";

        /** 分页 当前页的数据量    */
        public static final String PAGE_SIZE_NUM = "PAGE_SIZE_NUM";

    }
}
