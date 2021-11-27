package com.csx.common.controller;


import com.csx.common.utils.ToolUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * @author  Chengshx
 * @create  2021/11/27 20:03
 * @desc    验证码接口
 **/
@Slf4j
@Controller
public class CodeController {

    @Autowired
    private Producer captchaProducer ;

    /**
     * @method  getKaptchaImage
     * @params  HttpServletRequest request, HttpServletResponse response
     * @return  
     * @desc    获取验证码的接口
     **/
    @RequestMapping( name = "获取验证码的接口地址" , value = "/kaptcha" , method = RequestMethod.GET )
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = null;
        BufferedImage bi = null;
        ServletOutputStream out = null;
        try{
            session = request.getSession();
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");
            //生成验证码
            String capText = captchaProducer.createText();
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
            //向客户端写出
            bi = captchaProducer.createImage(capText);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (Exception e){
            log.error(ToolUtils.format("[{}] 获取验证码失败！" , "/kaptcha" ) , e);
        } finally {
            ToolUtils.closeIO(out);
        }
    }
}
