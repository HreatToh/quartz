package com.csx.common.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.csx.common.entity.Constants;
import com.csx.common.entity.SysException;
import com.csx.common.mapper.ExcetionMapper;
import com.csx.common.utils.ToolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author  Chengshx
 * @create  2021/11/19 18:50
 * @desc    ExcetionService 异常处理服务
 **/
@Service
public class ExcetionService {

    @Autowired
    private ExcetionMapper excetionMapper;


    /**
     * @method  saveExceptionInfo
     * @params  HttpServletRequest request, Throwable e
     * @return  Boolean
     * @desc    保存异常信息的方法
     **/
    public Boolean saveExceptionInfo(HttpServletRequest request, Throwable e) {
        SysException exception = new SysException();
        exception.setExpId(IdUtil.fastSimpleUUID().toUpperCase());
        exception.setExpIp(ToolUtils.localhost());
        exception.setExpPort(String.valueOf(request.getServerPort()));
        exception.setExpClassName(e.getClass().getName());
        exception.setExpName(e.getClass().getSimpleName());
        exception.setExpMsg(ExceptionUtil.stacktraceToString(e));
        exception.setExpTime(DateUtil.now());
        exception.setCommCdate(DateUtil.today());
        exception.setCommUdate(DateUtil.today());
        exception.setCommCuser(Constants.ADMIN);
        exception.setCommUuser(Constants.ADMIN);
        return excetionMapper.insert(exception) > 0 ;
    }
}
