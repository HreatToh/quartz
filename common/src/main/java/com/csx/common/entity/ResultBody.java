package com.csx.common.entity;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.csx.common.base.entity.BaseEntity;
import com.csx.common.enums.AppEnum;

/**
 * @author  Chengshx
 * @create  2021/11/19 17:19
 * @desc    自定义响应体
 **/
public class ResultBody extends BaseEntity {

    /** 响应码    */
    private String code;
    /** 响应信息    */
    private String msg;
    /** 响应结果集    */
    private Object data;
    /** 错误信息    */
    private String errorMsg;
    /** 请求结果    */
    private Boolean success;


    public ResultBody() {
    }

    public ResultBody(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.success = true;
    }

    public ResultBody(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = true;
    }

    public ResultBody(String code, String msg, Object data, Throwable throwable) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.errorMsg = ExceptionUtil.stacktraceToString(throwable );
        this.success = true;
    }

    public ResultBody(String code, String msg, Object data, Throwable throwable , Boolean success) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        if ( throwable != null ){
            this.errorMsg = ExceptionUtil.stacktraceToString( throwable );
        }
        this.success = true;
    }
    public ResultBody(String code, String msg, Object data, Boolean success) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = success;
    }

    /**
     * 默认成功 不带数据
     * @return
     */
    public static ResultBody success(){
        return success(AppEnum.SUCCESS);
    }
    /**
     * 默认成功 不带数据
     * @return
     */
    public static ResultBody success( AppEnum appEnum){
        return success(appEnum.getCode() , appEnum.getMsg());
    }
    /**
     * 默认成功 不带数据
     * @return
     */
    public static ResultBody success(String code , String msg){
        return success(code , msg , null) ;
    }

    /**
     * 默认成功 带数据
     * @return
     */
    public static ResultBody success( Object data ){
        return new ResultBody(AppEnum.SUCCESS.getCode() , AppEnum.SUCCESS.getMsg() , data);
    }

    /**
     * 默认成功 带数据
     * @return
     */
    public static ResultBody success(String code , String msg , Object data ){
        return new ResultBody(code , msg , data );
    }


    /**
     * 默认失败 不带错误信息
     * @return
     */
    public static ResultBody fail(){
        return fail( AppEnum.FAIL );
    }
    /**
     * 默认失败 不带错误信息
     * @return
     */
    public static ResultBody fail( AppEnum appEnum ){
        return fail(appEnum.getCode() , appEnum.getMsg() );
    }

    /**
     * 默认失败 不带错误信息
     * @return
     */
    public static ResultBody fail( String code , String msg ){
        return fail(code , msg , null);
    }

    /**
     * 默认失败 带错误信息
     * @return
     */
    public static ResultBody fail( String code , String msg , Throwable throwable){
        return new ResultBody(code , msg , null , throwable, false );
    }
    /**
     * 默认失败 带错误信息
     * @return
     */
    public static ResultBody fail( Throwable throwable ){
        return new ResultBody(AppEnum.FAIL.getCode() , AppEnum.FAIL.getMsg() , null , throwable , false );
    }
    /**
     * 默认错误 带错误信息
     * @return
     */
    public static ResultBody error( Throwable throwable ){
        return error(AppEnum.INTERNAL_SERVER_ERROR , throwable);
    }
    /**
     * 默认错误 带错误信息
     * @return
     */
    public static ResultBody error( AppEnum appEnum , Throwable throwable){
        return error(appEnum.getCode() , appEnum.getMsg() , throwable);
    }

    /**
     * 默认错误 带错误信息
     * @return
     */
    public static ResultBody error( String code , String msg , Throwable throwable){
        return new ResultBody(code , msg , null , throwable , true );
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
