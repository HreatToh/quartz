package com.csx.common.exception;


import com.csx.common.enums.AppEnum;

/**
 * @author  Chengshx
 * @create  2021/11/19 17:18
 * @desc    自定义 AppException 全局异常
 **/
public class AppException extends RuntimeException {

    /**
     * 错误码
     */
    protected String errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;

    public AppException() {
        super();
    }

    public AppException(AppEnum appEnum) {
        super(appEnum.getCode());
        this.errorCode = appEnum.getCode();
        this.errorMsg = appEnum.getMsg();
    }

    public AppException(AppEnum appEnum, Throwable cause) {
        super(appEnum.getCode(), cause);
        this.errorCode = appEnum.getCode();
        this.errorMsg = appEnum.getMsg();
    }

    public AppException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public AppException(String errorCode, String errorMsg) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public AppException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMessage() {
        return errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
