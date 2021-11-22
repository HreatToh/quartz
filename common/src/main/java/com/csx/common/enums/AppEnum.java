package com.csx.common.enums;

/**
 * @author  Chengshx
 * @create  2021/11/19 17:17
 * @desc    自定义错误码枚举AppEnum
 **/
public enum AppEnum {
    // 数据操作错误定义
    SUCCESS("200", "请求成功!"),
    FAIL("999","请求失败!") ,
    BODY_NOT_MATCH("400","请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("401","请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503","服务器正忙，请稍后再试!") ,
    LICENSE_EXPIRED("900","Licence 已过期!") ,
    LICENSE_ERROR("901","Licence 不正确!") ;

    /** 错误码 */
    private String code;

    /** 错误描述 */
    private String msg;

    AppEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
