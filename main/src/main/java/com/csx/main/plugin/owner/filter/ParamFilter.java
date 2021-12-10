package com.csx.main.plugin.owner.filter;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.csx.common.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ParamFilter implements Filter {

    /**
     * 过滤器初始化
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 开始过滤
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest hrequest = (HttpServletRequest)servletRequest;
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 销毁
     */
    @Override
    public void destroy() {

    }

     /**
     * @author chengshx
     * @description: 对请求参数进行过滤
     * @date 2021-12-10
     */
    class ParamsRequestWrapper extends HttpServletRequestWrapper {

        private Map<String, String[]> params = new HashMap<String , String[]>();

        public ParamsRequestWrapper(HttpServletRequest request) {
            super(request);
            // 将参数表，赋予给当前的Map以便于持有request中的参数
            Map<String, String[]> requestMap = request.getParameterMap();
            log.debug("转化之前的参数：" + JSONUtil.toJsonStr(requestMap));
            this.params.putAll(requestMap);
            this.modifyParameters();
            log.debug("转化之后的参数：" + JSONUtil.toJsonStr(params));
        }

        /**
         * 重写getInputStream方法  post请求参数必须通过流才能获取到值
         */
        @Override
        public ServletInputStream getInputStream() throws IOException {
            ServletInputStream stream = super.getInputStream();
            //非json类型，直接返回
            if (!super.getHeader(HttpHeaders.CONTENT_TYPE).equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
                return stream;
            }
            String json = IoUtil.readUtf8(stream);
            if (ToolUtils.isBlank(json)) {
                return stream;
            }
            log.debug("转化之前的参数：" + json);
            Map<String, Object> map = modifyParams(json);
            log.debug("转化之后的参数：" + JSONUtil.toJsonStr(map));
            ByteArrayInputStream bis = IoUtil.toUtf8Stream(JSONUtil.toJsonStr(map));
            return new ParamsServletInputStream(bis);
        }

         /**
          * 转换参数
          * @param json
          * @return
          */
        private Map<String, Object> modifyParams(String json) {
            Map<String, Object> params = JSONUtil.toBean(json , Map.class);
            Boolean isEncode = MapUtil.getBool(params , "isEncode" , false);
            Map<String, Object> maps = new HashMap<>(params.size());
            for (String key : params.keySet()) {
                Object value = getValue(params.get(key) , isEncode);
                maps.put(key, value);
            }
            if (isEncode){
                maps = ToolUtils.decodeParam(maps);
            }
            return maps;
        }

         /**
          * 获取参数值
          * @param value
          * @param isEncode
          * @return
          */
        private Object getValue(Object value , Boolean isEncode) {
            if (ToolUtils.isNull( value )){
                return null;
            }
            if (value instanceof String){
                value = value.toString().trim();
            }
            return value;
        }

        /**
         * 将parameter的值去除空格后重写回去
         */
        private void modifyParameters() {
            Set<String> set = params.keySet();
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String[] values = params.get(key);
                values[0] = values[0].trim();
                params.put(key, values);
            }
        }

        /**
         * 重写getParameter 参数从当前类中的map获取
         */
        @Override
        public String getParameter(String name) {
            String[] values = params.get(name);
            if (values == null || values.length == 0) {
                return null;
            }
            return values[0];
        }

    }

    /**
     * @author chengshx
     * @description: 请求参数输入流
     * @date 2021-12-10 14:07:00
     */
    class ParamsServletInputStream extends ServletInputStream {

        private ByteArrayInputStream bis;

        public ParamsServletInputStream(ByteArrayInputStream bis) {
            this.bis = bis;
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return bis.read();
        }

    }
}
