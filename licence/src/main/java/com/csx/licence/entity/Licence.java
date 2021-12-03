package com.csx.licence.entity;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.Serializable;

/**
 * @author  Chengshx
 * @create  2021/12/3 15:01
 * @desc    ren
 **/
public class Licence implements Serializable {
    /** 主键    */
    private String authorizationId;
    /** 给谁授权    */
    private String authorizationName;
    /** 授权日期    */
    private String authorizationDate;
    /** 到期日期    */
    private String expireDate;
    /** 是否永久    */
    private Boolean isForever;
    /** 是否试用    */
    private Boolean isTry;
    /** 试用天数    */
    private Integer trialDays;

    public String getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
    }

    public String getAuthorizationName() {
        return authorizationName;
    }

    public void setAuthorizationName(String authorizationName) {
        this.authorizationName = authorizationName;
    }

    public String getAuthorizationDate() {
        return authorizationDate;
    }

    public void setAuthorizationDate(String authorizationDate) {
        this.authorizationDate = authorizationDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Boolean getForever() {
        return isForever;
    }

    public void setForever(Boolean forever) {
        if (forever){
            trialDays = -1;
            expireDate = "9999-12-31";
        }
        isForever = forever;
    }

    public Boolean getTry() {
        return isTry;
    }

    public void setTry(Boolean aTry) {
        if (aTry){
            expireDate = DateUtil.offsetDay(DateUtil.parseDate(getAuthorizationDate()) , trialDays).toDateStr();
        }
        isTry = aTry;
    }

    public Integer getTrialDays() {
        return trialDays;
    }

    public void setTrialDays(Integer trialDays) {
        this.trialDays = trialDays;
    }

    /**
     * @method  encode
     * @params  Licence licence
     * @return  String
     * @desc    生成许可证
     **/
    public static String encode(Licence licence){
        String encode = Base64.encode(JSONUtil.toJsonStr(licence));
        encode = StrUtil.format("==#{}.{}#==" ,licence.authorizationId , encode);
        return JWT.create().withAudience(encode).sign(Algorithm.HMAC256(licence.getAuthorizationId()));
    }

    /**
     * @method  decode
     * @params  String encode
     * @return  Licence
     * @desc    获取许可证对象
     **/
    public static Licence decode(String encode){
        encode = JWT.decode(encode).getAudience().get(0);
        encode = encode.substring(36 , encode.length()-3);
        encode = Base64.decodeStr(encode);
        return JSONUtil.toBean(encode , Licence.class);
    }

    /**
     * @method  decode
     * @params  String encode
     * @return  Licence
     * @desc    获取许可证对象
     **/
    public static String decodeJson(String encode){
        encode = JWT.decode(encode).getAudience().get(0);
        encode = encode.substring(36 , encode.length()-3);
        encode = Base64.decodeStr(encode);
        return encode;
    }

    /**
     * @method  getInstance
     * @params  String json
     * @return  Licence
     * @desc    获取实例
     **/
    public static Licence getInstance(String json){
        return JSONUtil.toBean(json , Licence.class);
    }
}
