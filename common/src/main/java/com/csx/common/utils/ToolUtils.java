package com.csx.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.csx.common.config.AppCofig;
import com.csx.common.entity.SysUser;
import com.csx.common.other.Constants;
import com.csx.common.enums.EvmentEnum;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Slf4j
public class ToolUtils extends StrUtil{

    /** 是否开发环境    */
    public static Boolean isDev = false;
    /** 是否生产环境    */
    public static Boolean isProd = false;
    /** 是否测试环境    */
    public static Boolean isTest = false;



    /**
     * 获取当前登录的 用户Id
     * @return String
     */
    public static String getUserId(){
        return ThreadLocalUtils.getUser().getUserId();
    }

    /**
     * 获取当前登录的用户对象
     * @return SysUser
     */
    public static SysUser getUser(){
        return ThreadLocalUtils.getUser();
    }

    /**
     * 判断是否admin
     * @param
     * @return
     */
    public static Boolean isAdmin(){
        return equalsAnyIgnoreCase(getUserId() , Constants.Session.ADMIN);
    }
    /**
     * 判断是否admin
     * @param userId
     * @return
     */
    public static Boolean isAdmin(String userId){
        return equalsAnyIgnoreCase(userId , Constants.Session.ADMIN);
    }
    /**
     * 初始化环境信息
     * @param avtive
     */
    public static void initEvment(String avtive){
        isDev = equalsAnyIgnoreCase(avtive , EvmentEnum.DEV.key());
        isProd = equalsAnyIgnoreCase(avtive , EvmentEnum.PROD.key());
        isTest = equalsAnyIgnoreCase(avtive , EvmentEnum.TEST.key());
    }

    /**
     * 获取环境信息
     * @return
     */
    public static EvmentEnum getEvment(){
        if (isDev){
            return EvmentEnum.DEV;
        }
        if (isProd){
            return EvmentEnum.PROD;
        }
        if (isTest){
            return EvmentEnum.TEST;
        }
        return null;
    }
    /**
     * 获取当前时间
     * 格式 yyyy-MM-dd HH:mm:ss
     * @return String
     */
    public static String nowTime(){
        return DateUtil.now();
    }

    /**
     * 获取当前日期 格式 yyyy-MM-dd
     * @return String
     */
    public static String nowDate() {
        return DateUtil.today();
    }

    /**
     * 获取Id 的方法 默认是uuid不带 -
     * @return
     */
    public static String getId(){
        return IdUtil.fastSimpleUUID();
    }

//    public static void main(String[] args) {
//        Console.log(getId());
//    }

    /**
     * 获取当前日期
     * 格式 yyyy-MM-dd
     * @return
     */
    public static String today(){
        return DateUtil.today();
    }

    /**
     * 获取本机ip地址
     * @return
     */
    public static String localhost(){
        return NetUtil.getLocalhostStr();
    }

    /**
     * 判断是否为NULL
     * @return
     */
    public static Boolean isNull( Object o ){
        return o == null ;
    }
    /**
     * 判断是否不为NULL
     * @return
     */
    public static Boolean isNotNull( Object o ){
        return !isNull(o);
    }

    /**
     * 判断是否是Y
     * @param s
     * @return
     */
    public static Boolean isY(Object s){
        if (s instanceof String && isNotNull(s) ){
            return equals(Constants.App.Y , s.toString());
        }
        return false;
    }

    /**
     * 判断是否是N
     * @param s
     * @return
     */
    public static Boolean isN(Object s){
        return !isY(s);
    }

    /**
     * 如果那么语法
     * @param itemVal
     * @param itemDefval
     * @return
     */
    public static String nvl(String itemVal, String itemDefval) {
        return nvl(itemVal , itemDefval , true);
    }

    /**
     * 如果那么语法
     * @param itemVal
     * @param itemDefval
     * @return
     */
    public static Boolean nvl(Object itemVal, Boolean itemDefval) {
        String value = nvl(itemVal, String.valueOf(itemDefval));
        Boolean newValue = false;
        try{
            newValue = Boolean.parseBoolean(value);
        } catch (Exception e){
            log.error(ToolUtils.format("值[{}]不是合法的 Boolean 类型" , itemVal ) , e);
            newValue = itemDefval;
        }
        return newValue;
    }

    /**
     * 如果那么语法
     * @param itemVal
     * @param itemDefval
     * @return
     */
    public static Integer nvl(Object itemVal, Integer itemDefval) {
        String value = nvl(itemVal , String.valueOf(itemDefval));
        Integer newValue = 0;
        try{
            newValue = Integer.parseInt(value);
        } catch (Exception e){
            log.error(ToolUtils.format("值[{}]不是合法的 Integer 类型" , itemVal ) , e);
            newValue = itemDefval;
        }
        return newValue;
    }

    /**
     * 如果那么语法
     * @param itemVal
     * @param itemDefval
     * @return
     */
    public static BigDecimal nvl(Object itemVal, BigDecimal itemDefval) {
        String value = nvl(itemVal, String.valueOf(itemDefval));
        BigDecimal newValue = null;
        try{
            newValue = BigDecimal.valueOf(Double.parseDouble(value));
        } catch (Exception e){
            log.error(ToolUtils.format("值[{}]不是合法的 BigDecimal 类型" , itemVal ) , e);
            newValue = itemDefval;
        }
        return newValue;
    }

    /**
     * 如果那么语法
     * @param itemVal
     * @param itemDefval
     * @return
     */
    public static String nvl(Object itemVal, String itemDefval) {
        String val = isNotNull(itemVal) && (itemVal instanceof String ) ? itemVal.toString() : itemDefval;
        return val;
    }
    /**
     * 如果那么语法
     * @param itemVal
     * @param itemDefval
     * @param trim true : 返回空串 false : 返回null
     * @return
     */
    public static String nvl(String itemVal, String itemDefval , Boolean trim){
        String val = isBlank(itemVal) ? itemDefval : itemVal;
        return isBlank(val) && trim ? "" : val;
    }


    /**
     * @method  offset
     * @params  String date , Integer dayOffset
     * @return  String
     * @desc    日期偏移量 yyyy-MM-dd
     **/
    public static Date offsetDay(String date , Integer dayOffset ){
        return DateUtil.offsetDay(DateUtil.parseDate(date) , dayOffset);
    }

    /**
     * @method  compare
     * @params  Date date
     * @return  Boolean
     * @desc    比较日期 date > 当前日期 ? true : false
     **/
    public static Boolean compareDate( Date date ) {
        return DateUtil.compare( date , DateUtil.date() ) > 0 ;
    }

    /**
     * @method  compare
     * @params  Date date1 , Date date2
     * @return  Boolean
     * @desc    比较日期 date1 > date2 ? true : false
     **/
    public static Boolean compareDate(Date date1 , Date date2) {
        return DateUtil.compare(date1 , date2) > 0 ;
    }

    /**
     * @method  compare
     * @params  Date date1 , Date date2
     * @return  Boolean
     * @desc    比较日期 date1 > date2 ? true : false
     **/
    public static Boolean compareDate(String date1 , String date2) {
        return DateUtil.compare(DateUtil.parseDate(date1) , DateUtil.parseDate(date2)) > 0 ;
    }

    /**
     * @method  compare
     * @params  String date1 , String format1 , String date2, String format2
     * @return  Boolean
     * @desc    比较日期 date1 > date2 ? true : false
     **/
    public static Boolean compareDate(String date1 , String format1 , String date2, String format2) {
        return DateUtil.compare(DateUtil.parse(date1 , format1) , DateUtil.parse(date2 , format2)) > 0 ;
    }

    /**
     * @method  closeIO
     * @params  Closeable closeable
     * @return  
     * @desc    关闭IO的方法
     **/
    public static void closeIO(Closeable closeable){
        if (isNotNull(closeable)){
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * @method  getParam
     * @params  HttpServletRequest request , String key
     * @return  String
     * @desc    获取参数值
     **/
    public static String getParam(HttpServletRequest request , String key){
        return nvl(request.getParameter(key) , "");
    }

    /**
     * @method  checkVerifyCode
     * @params  HttpServletRequest request
     * @return  boolean
     * @desc    校验验证码
     **/
    public static boolean checkVerifyCode(HttpServletRequest request) {
        //获取生成的验证码
        String verifyCodeExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //获取用户输入的验证码
        String verifyCodeActual = getParam(request, Constants.App.KAPTCHA_CODE_KEY);
        if (isNull(verifyCodeActual) || !equalsIgnoreCase(verifyCodeActual,verifyCodeExpected)){
            return false;
        }
        return true;
    }

    /**
     * @method  getToken
     * @params
     * @return  String
     * @desc    获取token的方法
     **/
    public static String getToken( SysUser user ){
        return JWT.create().withAudience(user.json() , localhost() , nowTime() , getEvment().toString() ).sign(Algorithm.HMAC256(getLicence()));
    }

    /**
     * @method  getLicence
     * @params  
     * @return  String
     * @desc    获取当前系统的 License
     **/
    public static String getLicence(){
        return AppCofig.getSysConfig(Constants.App.SYS_LICENCE , Constants.App.$NONE);
    }

    /**
     * @method  encodeBase64
     * @params  String text
     * @return  String
     * @desc    base64加密
     **/
    public static String encodeBase64(String text){
        return Base64.encode(text);
    }

    /**
     * @method  decodeBase64
     * @params  String text
     * @return  String
     * @desc    base64解密
     **/
    public static String decodeBase64(String text){
        return Base64.decodeStr(text);
    }


    /**
     * 加密参数
     * @param params
     * @return
     */
    public static Map<String , Object> encodeParam(Map<String , Object> params){
        for (Map.Entry<String , Object> entry : params.entrySet() ) {
            if (isNotNull(entry.getValue()) && entry.getValue() instanceof String) {
                params.put(entry.getKey() , encodeBase64(nvl(entry.getValue() , "")));
            }
        }
        return params;
    }

    /**
     * 解密参数
     * @param params
     * @return
     */
    public static Map<String , Object> decodeParam(Map<String , Object> params){
        for (Map.Entry<String , Object> entry : params.entrySet() ) {
            if (isNotNull(entry.getValue()) && entry.getValue() instanceof String) {
                params.put(entry.getKey() , decodeBase64(nvl(entry.getValue() , "")));
            }
        }
        return params;
    }
}
