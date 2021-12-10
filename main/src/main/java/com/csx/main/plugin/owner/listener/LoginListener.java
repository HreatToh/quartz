package com.csx.main.plugin.owner.listener;

import com.csx.common.entity.SysUser;
import com.csx.common.other.Constants;
import com.csx.common.utils.CacheUtils;
import com.csx.common.utils.OnlineUtils;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Slf4j
public class LoginListener implements HttpSessionListener {

    /**
     * 引入日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginListener.class);
    /**
     * 在线人数
     */
    public static Integer ONLINE_COUNT = 0 ;

    /**
     * session创建监听
     * @param se
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.debug("Session 创建....");
    }

    /**
     * session销毁监听
     * @param se
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.debug("Session 销毁....");
         HttpSession session = se.getSession();
        SysUser sysUser = (SysUser) session.getAttribute(Constants.Session.SESSION_USER_KEY);
        OnlineUtils.remove(sysUser.getUserId() , session.getId());
    }
}
