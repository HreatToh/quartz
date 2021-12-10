package com.csx.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author  Chengshx
 * @create  2021/12/9 10:59
 * @desc    CookieUtils 工具类
 **/
@Slf4j
public class CookieUtils {

    /**
     * 设置 Cookie
     * @param name 名称
     * @param value 值
     * @param maxAge 生存时间（单位秒）
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, null);
        if(ToolUtils.isNotBlank(SpringUtils.getApplicationContext().getApplicationName())){
            cookie.setPath(SpringUtils.getApplicationContext().getApplicationName());
        }else{
            cookie.setPath( "/");
        }

        cookie.setMaxAge(maxAge);
        try {
            cookie.setValue(URLEncoder.encode(value, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("编码异常！" , e);
        }
        response.addCookie(cookie);
    }

    /**
     * 设置 Cookie（生成时间为1天）
     * @param name 名称
     * @param value 值
     */
    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, 60*60*24);
    }
    /**
     * 获得指定Cookie的值
     * @param request 请求对象
     * @param name 名字
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, String name) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (ToolUtils.isNotNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    try {
                        value = URLDecoder.decode(cookie.getValue(), "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        log.error("编码异常！" , e);
                    }
                }
            }
        }
        return value;
    }
    /**
     * 移出Cookie
     * @param request 请求对象
     * @param response 响应对象
     * @param name 名字
     * @return 值
     */
    public static String removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (ToolUtils.isNotNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        return value;
    }
}
