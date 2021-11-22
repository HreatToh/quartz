package com.csx.main.plugin.owner.handle;

import com.csx.common.entity.ResultBody;
import com.csx.common.enums.AppEnum;
import com.csx.common.exception.AppException;
import com.csx.common.service.ExcetionService;
import com.csx.common.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ExcetionService excetionService;

    /**
     * 处理自定义的业务异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = AppException.class)
    @ResponseBody
    public ResultBody appExceptionHandler(HttpServletRequest request, AppException e){
        log.error("发生业务异常！原因是：｛" + e.getErrorMsg() + "｝" , e);
        excetionService.saveExceptionInfo( request , e );
        return ResultBody.error(e);
    }

    /**
     * 处理空指针的异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResultBody exceptionHandler(HttpServletRequest request , NullPointerException e){
        log.error("发生空指针异常！原因是：｛" + e.getMessage() + "｝", e);
        excetionService.saveExceptionInfo( request , e );
        return ResultBody.error(e);
    }


    /**
     * 处理其他异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public ResultBody exceptionHandler( HttpServletRequest request, Throwable e ){
        log.error("其他异常！原因是：｛" + e.getMessage() + "｝", e);

        return ResultBody.error(AppEnum.INTERNAL_SERVER_ERROR , e);
    }

    /**
     * 保存异常信息
     * @param request
     * @param e
     * @return
     */
    private Boolean saveExceptionInfo( HttpServletRequest request, Throwable e ){
        Boolean success = false;
        try {
            success = excetionService.saveExceptionInfo( request , e );
        } catch ( Throwable ex) {
            log.error("保存异常信息发生异常" , ex);
        }
        return success;
    }
}
