layui.define(["customer_menu", "element","customer_tab", "customer_theme","form"], function (exports) {
    var layer = layui.layer, menu = layui.customer_menu, theme = layui.customer_theme,
        element = layui.element , tab = layui.customer_tab , ctxPath = rootPath , form = layui.form ;

    var customer = {

        /**
         * @method  success
         * @params  msg , callback
         * @return  index
         * @desc    成功图标对象
         **/
        ICON_SUCCESS : 1,

        /**
         * @method  fail
         * @params  msg , callback
         * @return  index
         * @desc    失败图标对象
         **/
        ICON_FAIL : 2,

        /**
         * 后台框架初始化
         * @param options.iniUrl   后台初始化接口地址
         * @param options.clearUrl   后台清理缓存接口
         * @param options.urlHashLocation URL地址hash定位
         * @param options.bgColorDefault 默认皮肤
         * @param options.multiModule 是否开启多模块
         * @param options.menuChildOpen 是否展开子菜单
         * @param options.loadingTime 初始化加载时间
         * @param options.pageAnim iframe窗口动画
         * @param options.maxTabNum 最大的tab打开数量
         */
        render: function (options) {
            options.iniUrl = ctxPath + options.iniUrl || null;
            options.clearUrl = ctxPath + options.clearUrl || null;
            options.licenceUrl = ctxPath + options.licenceUrl || null;
            options.urlHashLocation = options.urlHashLocation || false;
            options.bgColorDefault = options.bgColorDefault || 0;
            options.multiModule = options.multiModule || false;
            options.menuChildOpen = options.menuChildOpen || false;
            options.loadingTime = options.loadingTime || 1;
            options.pageAnim = options.pageAnim || false;
            options.maxTabNum = options.maxTabNum || 20;

            $.post( options.iniUrl, function (data) {
                if ( !data ) {
                    customer.error('暂无菜单信息');
                    return;
                }
                customer.renderLoader('正在加载配置，进入首页...');
                /** 初始化 Logo    */
                customer.renderLogo(data.homeInfo);
                /** 初始化 刷新缓存的Url    */
                customer.renderClear(options.clearUrl);
                /** 初始化首页    */
                customer.renderHome(data.homeInfo);
                /** 初始化动画    */
                customer.renderAnim(options.pageAnim);
                /** 初始化监听    */
                customer.listen();
                /** 初始化菜单    */
                menu.render({
                    subSystemList: data.subSystemInfo,
                    menuList: [],
                    multiModule: options.multiModule,
                    menuChildOpen: options.menuChildOpen
                });
                /** 初始化主题    */
                theme.render({
                    bgColorDefault: options.bgColorDefault,
                    listen: true,
                });

                tab.render({
                    filter: 'layuiminiTab',
                    urlHashLocation: options.urlHashLocation,
                    multiModule: options.multiModule,
                    menuChildOpen: options.menuChildOpen,
                    maxTabNum: options.maxTabNum,
                    subSystemList: data.subSystemInfo,
                    homeInfo: data.homeInfo,
                    listenSwichCallback: function () {
                        customer.renderDevice();
                    }
                });
                /** 开始 licence 认证    */
                customer.renderLicence(options.licenceUrl,function (result) {
                    if (result.code == 200 ){
                        /** 删除加载层    */
                        customer.deleteLoader(options.loadingTime);
                        $.cus.token = result.data;
                        customer.renderLicenceFooter(result.msg)
                        /** 初始化字典缓存    */
                        $.cusFn.initDict();
                    } else {
                        /** 去授权信息    */
                        result.loadingTime = options.loadingTime;
                        customer.authLicence(result);
                    }
                });
            }).fail(function () {
                customer.error('菜单接口有误！');
            });
        },

        /**
         * 许可证认证
         * @param data
         */
        authLicence: function(data){
            var id = data.threadId;
            var content = '<form class="layui-form layui-form-pane" style="padding: 5px;background: #eee;" lay-filter="' + id + '" action="">' +
                '   <div class="layui-form-item layui-form-text pane">' +
                '       <label class="layui-form-label" style="font-family: 楷体;font-weight: 800;">' + data.msg + '</label>' +
                '       <div class="layui-input-block">' +
                '           <textarea name="licenceCode" required lay-verify="required" placeholder="请输入认证码..." class="layui-textarea"></textarea>' +
                '       </div>' +
                '   </div>' +
                '</form>';
            return layer.open({
                title: ['Licence 系统认证','font-family: 华文行楷;']
                ,id: id
                ,type: 1
                ,area: ['500px', '260px']
                ,move: false
                ,closeBtn: 0
                ,offset: 'auto'
                ,content: content
                ,fixed: true
                ,resize: false
                ,shade: 0.3
                ,anim : 3
                ,btnAlign: 'c'
                ,btn: ['开始认证', '退出系统']
                ,yes: function(index, layero){
                    $.post(ctxPath + '/home/authLicence' , form.val(id) , function (result) {
                        if (result && result.code == '200'){
                            $.cus.token = result.data;
                            customer.success('认证成功！');
                            customer.deleteLoader(data.loadingTime);
                            customer.renderLicenceFooter(result.msg)
                            layer.close(index);
                        }else{
                            customer.error(result.msg);
                        }
                    });
                }
                ,btn2: function(index, layero){
                    layer.close(index);
                    customer.loginout();
                }
            });
        },

        /**
         * 渲染认证信息脚标
         * @param text
         */
        renderLicenceFooter: function(text){
            var html = '<span class="layui-badge-rim" style="position: relative;top: 32%;"> <i class="fa fa-connectdevelop"></i> ' + text+ '</span>' +
                '<span class="layui-badge-rim" style="position: relative;top: 32%;left: 30%;" id="currentTime"></span>';
            $('.layui-body-foot').html(html);
            setInterval(function () {
                $('#currentTime').html("系统时间：" + new Date().format('yyyy年MM月dd日 HH时mm分ss秒'));
            } , 1000);
        },
        /**
         * 许可证认证
         * @param licence
         */
        renderLicence: function( licenceUrl , callback ){
            $.post(licenceUrl , callback).fail(function () {
                customer.error('认证接口不存在！' , function () {
                    /** 不存在则登出！    */
                    customer.loginout();
                });
            });
        },
        /**
         * 登出
         */
        loginout: function(){
            $.post(ctxPath + '/logout' , function () {
                sessionStorage.clear();
                $.cus.clear();
                customer.msg('退成登录成功！' , function () {
                    window.location.href = ctxPath + '/page/login';
                });
            });
        },

        /**
         * 渲染 加载层...
         * @param msg
         */
        renderLoader: function (msg){
            msg = msg || 'loading ...';
            var i = 0 , loadHtml = '<div class="bounce">';
            $('div.bounce').remove();
            for (; i < msg.length; i++) {
                loadHtml += '<span class="letter">' + msg[i] + '</span>';
            }
            loadHtml += '</div>';
            $('.layuimini-loader').append(loadHtml);
        },
        /**
         * 初始化iframe窗口动画
         * @param anim
         */
        renderAnim: function (anim) {
            if (anim) {
                $('#layuimini-bg-color').after('<style id="layuimini-page-anim">' +
                    '.layui-tab-item.layui-show {animation:moveTop 1s;-webkit-animation:moveTop 1s;animation-fill-mode:both;-webkit-animation-fill-mode:both;position:relative;height:100%;-webkit-overflow-scrolling:touch;}\n' +
                    '@keyframes moveTop {0% {opacity:0;-webkit-transform:translateY(30px);-ms-transform:translateY(30px);transform:translateY(30px);}\n' +
                    '    100% {opacity:1;-webkit-transform:translateY(0);-ms-transform:translateY(0);transform:translateY(0);}\n' +
                    '}\n' +
                    '@-o-keyframes moveTop {0% {opacity:0;-webkit-transform:translateY(30px);-ms-transform:translateY(30px);transform:translateY(30px);}\n' +
                    '    100% {opacity:1;-webkit-transform:translateY(0);-ms-transform:translateY(0);transform:translateY(0);}\n' +
                    '}\n' +
                    '@-moz-keyframes moveTop {0% {opacity:0;-webkit-transform:translateY(30px);-ms-transform:translateY(30px);transform:translateY(30px);}\n' +
                    '    100% {opacity:1;-webkit-transform:translateY(0);-ms-transform:translateY(0);transform:translateY(0);}\n' +
                    '}\n' +
                    '@-webkit-keyframes moveTop {0% {opacity:0;-webkit-transform:translateY(30px);-ms-transform:translateY(30px);transform:translateY(30px);}\n' +
                    '    100% {opacity:1;-webkit-transform:translateY(0);-ms-transform:translateY(0);transform:translateY(0);}\n' +
                    '}' +
                    '</style>');
            }
        },
        /**
         * 初始化首页
         * @param data
         */
        renderHome: function (data) {
            sessionStorage.setItem('layuiminiHomeHref', ctxPath + data.homeUrl);
            $('#layuiminiHomeTabId_homeTabContent').html('<span class="layuimini-tab-active"></span><span class="disable-close">' + data.homeName + '</span><i class="layui-icon layui-unselect layui-tab-close">ဆ</i>');
            $('#layuiminiHomeTabId_homeTabContent').attr('lay-id', "homeTabContent");
            $('#layuiminiHomeTabId_homeTabContent').attr('lay-href', ctxPath + data.homeUrl);
            $('#appName').html(data.AppName + ' [' + data.environment + ']');
            tab.reloadPage('homeTabContent' , ctxPath + data.homeUrl);
        },


        /**
         * 初始化logo
         * @param data
         */
        renderLogo: function (data) {
            var html = '<a href="' + ctxPath + data.homeUrl + '"><img src="' + ctxPath + data.homeSideIcon + '" alt="logo"><h1>' + data.homeSideName + '</h1></a>';
            $('.layuimini-logo').html(html);
        },

        /**
         * 初始化缓存地址
         * @param clearUrl
         */
        renderClear: function (clearUrl) {
            $('.layuimini-clear').attr('data-href',ctxPath + clearUrl);
        },


        /**
         * 全屏的调用方法
         */
        fullScreen: function () {
            var el = document.documentElement;
            var rfs = el.requestFullScreen || el.webkitRequestFullScreen;
            if (typeof rfs != "undefined" && rfs) {
                rfs.call(el);
            } else if (typeof window.ActiveXObject != "undefined") {
                var wscript = new ActiveXObject("WScript.Shell");
                if (wscript != null) {
                    wscript.SendKeys("{F11}");
                }
            } else if (el.msRequestFullscreen) {
                el.msRequestFullscreen();
            } else if (el.oRequestFullscreen) {
                el.oRequestFullscreen();
            } else if (el.webkitRequestFullscreen) {
                el.webkitRequestFullscreen();
            } else if (el.mozRequestFullScreen) {
                el.mozRequestFullScreen();
            } else {
                miniAdmin.error('浏览器不支持全屏调用！');
            }
        },

        /**
         * 退出全屏
         */
        exitFullScreen: function () {
            var el = document;
            var cfs = el.cancelFullScreen || el.webkitCancelFullScreen || el.exitFullScreen;
            if (typeof cfs != "undefined" && cfs) {
                cfs.call(el);
            } else if (typeof window.ActiveXObject != "undefined") {
                var wscript = new ActiveXObject("WScript.Shell");
                if (wscript != null) {
                    wscript.SendKeys("{F11}");
                }
            } else if (el.msExitFullscreen) {
                el.msExitFullscreen();
            } else if (el.oRequestFullscreen) {
                el.oCancelFullScreen();
            }else if (el.mozCancelFullScreen) {
                el.mozCancelFullScreen();
            } else if (el.webkitCancelFullScreen) {
                el.webkitCancelFullScreen();
            } else {
                customer.error('浏览器不支持全屏调用！');
            }
        },

        /**
         * 初始化设备端
         */
        renderDevice: function () {
            if (customer.checkMobile()) {
                $('.layuimini-tool i').attr('data-side-fold', 1);
                $('.layuimini-tool i').attr('class', 'fa fa-outdent');
                $('.layui-layout-body').removeClass('layuimini-mini');
                $('.layui-layout-body').addClass('layuimini-all');
            }
        },
        /**
         * 初始化加载时间
         * @param loadingTime
         */
        deleteLoader: function (loadingTime) {
            setTimeout(function () {
                $('.layuimini-loader').fadeOut();
            }, loadingTime * 1000)
        },

        /**
         * 成功
         * @param title
         * @returns {*}
         */
        success: function (title , callback) {
            return customer.msg(title, {icon: 1, shade: this.shade, scrollbar: false, time: 1000, shadeClose: true} , callback);
        },

        /**
         * 失败
         * @param title
         * @returns {*}
         */
        error: function (title , callback) {
            return customer.msg(title, {icon: 2, shade: this.shade, scrollbar: false, time: 3000, shadeClose: true} , callback);
        },

        /**
         * 加载层...
         * @returns {*}
         */
        loading: function(msg , options ){
            msg = msg || '加载中..';
            options = options || {
                icon: 16
                ,shade: 0.3
                ,time: false
            };
            return layer.msg( msg , options);
        },
        /**
         * 信息框...
         * @returns {*}
         */
        msg: function(title , options , callback ){
            if ($.isFunction(options) && !callback){
                callback = options;
                options = {};
            }else{
                options = options || {};
                callback = callback || function(){};
            }
            options = $.extend({icon: 1, shade: this.shade, scrollbar: false, time: 1000, shadeClose: true},options);
            return layer.msg(title, options , callback);
        },

        /**
         * 判断是否为手机
         * @returns {boolean}
         */
        checkMobile: function () {
            var ua = navigator.userAgent.toLocaleLowerCase();
            var pf = navigator.platform.toLocaleLowerCase();
            var isAndroid = (/android/i).test(ua) || ((/iPhone|iPod|iPad/i).test(ua) && (/linux/i).test(pf))
                || (/ucweb.*linux/i.test(ua));
            var isIOS = (/iPhone|iPod|iPad/i).test(ua) && !isAndroid;
            var isWinPhone = (/Windows Phone|ZuneWP7/i).test(ua);
            var clientWidth = document.documentElement.clientWidth;
            if (!isAndroid && !isIOS && !isWinPhone && clientWidth > 1024) {
                return false;
            } else {
                return true;
            }
        },

        /**
         * 监听
         */
        listen: function () {

            /**
             * 清理
             */
            $('body').on('click', '[data-clear]', function () {
                var loading = customer.loading('正在清理...');
                sessionStorage.clear();

                // 判断是否清理服务端
                var clearUrl = $(this).attr('data-href');
                if (clearUrl != undefined && clearUrl != '' && clearUrl != null) {
                    $.post(clearUrl, function (data) {
                        layer.close(loading);
                        if (data.code != '200') {
                            return customer.error(data.msg);
                        } else {
                            return customer.success(data.msg);
                        }
                    }).fail(function () {
                        layer.close(loading);
                        return customer.error('清理缓存接口有误！');
                    });
                } else {
                    layer.close(loading);
                    return customer.success('清除缓存成功！');
                }
            });

            /**
             * 刷新
             */
            $('body').on('click', '[data-refresh]', function () {
                var layId = $('#layuiminiHomeTabId_homeTabContent').attr('lay-id');
                var layUrl = $('#layuiminiHomeTabId_homeTabContent').attr('lay-href');
                tab.reloadPage(layId , layUrl);
                customer.success('刷新成功');
            });

            /**
             * 监听提示信息
             */
            $("body").on("mouseenter", ".layui-nav-tree .menu-li", function () {
                if (customer.checkMobile()) {
                    return false;
                }
                var classInfo = $(this).attr('class'), tips = $(this).prop("innerHTML"), isShow = $('.layuimini-tool i').attr('data-side-fold');
                if (isShow == 0 && tips) {
                    tips = "<ul class='layuimini-menu-left-zoom layui-nav layui-nav-tree layui-this'><li class='layui-nav-item layui-nav-itemed'>"+tips+"</li></ul>" ;
                    window.openTips = layer.tips(tips, $(this), {
                        tips: [2, '#2f4056'],
                        time: 300000,
                        skin:"popup-tips",
                        success:function (el) {
                            var left = $(el).position().left - 10 ;
                            $(el).css({ left:left });
                            element.render();
                        }
                    });
                }
            });

            $("body").on("mouseleave", ".popup-tips", function () {
                if (customer.checkMobile()) {
                    return false;
                }
                var isShow = $('.layuimini-tool i').attr('data-side-fold');
                if (isShow == 0) {
                    try {
                        layer.close(window.openTips);
                    } catch (e) {
                        console.log(e.message);
                    }
                }
            });


            /**
             * 全屏
             */
            $('body').on('click', '[data-check-screen]', function () {
                var check = $(this).attr('data-check-screen');
                if (check == 'full') {
                    customer.fullScreen();
                    $(this).attr('data-check-screen', 'exit');
                    $(this).html('<i class="fa fa-compress"></i>');
                } else {
                    customer.exitFullScreen();
                    $(this).attr('data-check-screen', 'full');
                    $(this).html('<i class="fa fa-arrows-alt"></i>');
                }
            });

            /**
             * 点击遮罩层
             */
            $('body').on('click', '.layuimini-make', function () {
                customer.renderDevice();
            });

            /**
             * 点击登出事件
             */
            $('.login-out').on("click", function () {
                layer.confirm('是否要退出登录么？', function(index, layero){
                    customer.loginout();
                });
            });
            /**
             * 窗口改变事件
             */
            window.addEventListener('resize' ,function () {
                // var width = $(window).width() - 200;
                var width = $('.layui-body').width();
                var height = $('.layui-body').height() ;
                var containerHeight = height - 55 ;
                var mainHeight = containerHeight - 20 ;
                console.log('窗口改变了！');
                console.log('窗口高度：' + height);
                console.log('窗口宽度：' + width);
                $('[id^=layuiminiHomeTabContent].layuimini-container').height(containerHeight);
                $('[id^=layuiminiHomeTabContent].layuimini-container').css('overflow' , 'auto');
            });
        }
    };

    exports("customer", customer);
});
