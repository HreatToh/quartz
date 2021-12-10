/**
 * @author  Chengshx
 * @create  2021/11/23 14:46
 * @desc    Application.js 全局配置Js
 *
 **/

$(function () {

    /**
     * 自定义用户对象
     * @type object
     */
    jQuery.cus = {
        // token
        token : '',
        /** 项目目录    */
        ctxPath : rootPath,
        /** 请求头的key值    */
        tokenKey : 'Authorization',
        /** 分页参数 当前页    */
        pageKey : 'PAGE_NUM',
        /** 分页参数 分页大小    */
        limitKey : 'PAGE_SIZE_NUM',
        /** 字典对象    */
        dict : null,
        /** 清理缓存    */
        clear : function () {
            jQuery.cus.token = '';
        }
    };

    /**
     * cusAjax 自定义ajax方法
     * @param options
     */
    jQuery.cusInitAjaxSetup = function(){
        return {
            /** 返回的数据类型    */
            dataType : 'json',
            /** 请求超时时间  默认60秒  */
            timeout : 60000,
            /** 默认请求方式 POST    */
            type : 'POST',
            /** 默认是异步请求    */
            async : true,
            /** 发送请求之前先设置请求头 把token信息放进去    */
            headers : jQuery.cusFn.getHeaders()
        };
    };

    /**
     * cusAjax 自定义ajax方法
     * @param options
     */
    jQuery.cusAjax = function(options){
        options.url = jQuery.cusFn.getUrl(options.url);
        options = $.extend(jQuery.cusInitAjaxSetup() , options);
        return jQuery.ajax(options);
    };

    /**
     * cusPost 自定义post请求
     * @param options
     */
    jQuery.cusPost = function(url , param , callback ){
        var options = {};
        options.type = 'POST';
        if (param && param instanceof Function){
            options.url = url || '';
            options.success = param || function () { }
        }else{
            options.url = url || '';
            options.data = param || {};
            options.success = callback || function(){};
        }
        return jQuery.cusAjax(options);
    };

    /**
     * cusGet 自定义get请求
     * @param options
     */
    jQuery.cusGet = function(url , param , callback){
        var options = {};
        options.type = 'GET';
        if (param && param instanceof Function){
            options.url = url || '';
            options.success = param || function () { }
        }else{
            options.url = url || '';
            options.data = param || {};
            options.success = callback || function(){};
        }
        return jQuery.cusAjax(options);
    };

    /**
     * 用户自定义格式化数值
     * @type object
     */
    jQuery.cusForm = {
        date : function (val , options) {
            var format = options.format || 'YYYY-MM-DD';
            return new Date(val).format(format);
        },
        text : function (val , options) {

        },
        combo : function (val , options) {

        },
        time : function (val , options) {

        }
    };
    /**
     * 自定义用户函数
     * @type object
     */
    jQuery.cusFn = {

        /**
         * 清理缓存
         */
        clearCache: function(){
            jQuery.cus.dict = null;
        },
        /**
         * 初始化字典缓存
         */
        initDict : function(){
            jQuery.cusAjax({
                url : 'dict/getDict',
                async : false,
                success : function (data) {
                    jQuery.cus.dict = data;
                }
            });
        },
        /**
         * 转换字典的值
         * @param sysId
         * @param dictType
         * @param dictId
         * @returns {string}
         */
        getDict: function(sysId , dictType , dictId){
            var dictKey = sysId + '.' + dictType + '.' + dictId , dictVal;
            if (!jQuery.cus.dict){
                jQuery.cusFn.initDict();
            }
            dictVal = jQuery.cus.dict[dictKey] || '';
            return dictVal;
        },
        /**
         * 获取请求头
         */
        getHeaders : function(){
            var header = {};
            header[jQuery.cus.tokenKey] = jQuery.cus.token;
            return header;
        },
        /**
         * 渲染 url地址
         * @param url
         */
        getUrl : function( url , param ){
            if (jQuery.cusFn.isNull(url)){
                return '';
            }
            if (!url.startsWith('/')){
                url = '/' + url ;
            }
            if (!jQuery.cusFn.contains(url , jQuery.cus.ctxPath)){
                url = jQuery.cus.ctxPath + url;
            }
            if (param){
                param = jQuery.cusFn.encodeParam(params);
                if (jQuery.cusFn.contains(url , '?')){
                    url = url + '&' + jQuery.param(params);
                }else{
                    url = url + '?' + jQuery.param(params);
                }
            }
            return url;
        },
        /**
         * isNull 判断是否为空
         * @param val
         * @returns {*|boolean}
         */
        isNull : function( val ){
            return (jQuery.isEmptyObject(val) || val == null || val == undefined);
        },

        /**
         * isNotNull 判断是否为空
         * @param val
         * @returns {*|boolean}
         */
        isNotNull : function( val ){
            return !jQuery.isNull(val);
        },

        /**
         * nvl 函数 如果 val为null 则取 defVal
         * @param val
         * @param defVal
         * @returns {*}
         */
        nvl : function( val , defVal ){
            return ( jQuery.isNotNull(val) ? val : defVal );
        },

        /**
         * 包含函数
         * @param val
         * @param c
         */
        contains : function ( val , c) {
            if ( val === c ) {
                return true ;
            }
            if ( isNull(val )) {
                return false ;
            }
            if ( isNull(c) ) {
                return false ;
            }
            if ( val instanceof Object || c instanceof Object ){
                return false;
            }
            return val.indexOf(c) > 0 ;
        },

        /**
         * 加密参数
         * @param param
         */
        encodeParam : function (param){
            jQuery.each(param , function (key , value) {
                if (jQuery.cusFn.isNotNull(value) && value instanceof String){
                    param[key] = jQuery.cusFn.encodeBase64(value);
                }
            });
            param.isEncode = true;
            return param;
        },

        /**
         * 解密参数
         * @param param
         */
        decodeParam : function (param){
            jQuery.each(param , function (key , value) {
                if (jQuery.cusFn.isNotNull(value) && value instanceof String){
                    param[key] = jQuery.cusFn.decodeBase64(value);
                }
            });
            return param;
        },

        /**
         * base64加密
         */
        encodeBase64 : function (text) {
            return window.btoa(text);
        },
        /**
         * base64解密
         */
        decodeBase64 : function (text) {
            return window.atob(text);
        }
    };

})