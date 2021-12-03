package com.csx.main.plugin.owner.listener;

import com.csx.common.other.Constants;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        if ( se.getSession().isNew() && ToolUtils.isNotNull(se.getSession().getAttribute(Constants.Session.SESSION_USER_KEY))){
            ONLINE_COUNT ++ ;
            log.debug("当前在线人数：" + ONLINE_COUNT);
        }
    }

    /**
     * session销毁监听
     * @param se
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        if ( se.getSession().isNew() && ToolUtils.isNotNull(se.getSession().getAttribute(Constants.Session.SESSION_USER_KEY))){
            ONLINE_COUNT -- ;
            log.debug("当前在线人数：" + ONLINE_COUNT);
        }
    }
}
