package com.csx.common.handler;
/**
 * @author  Chengshx
 * @create  2021/11/22 14:27
 * @desc    缓存助手类
 **/
public interface CacheHandler<T> {

    public <T> T handler() throws Exception;

}
