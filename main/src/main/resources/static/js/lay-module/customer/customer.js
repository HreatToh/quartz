layui.define(["jquery", "customer_menu", "element","customer_tab", "customer_theme"], function (exports) {
    var $ = layui.$, layer = layui.layer, menu = layui.customer_menu, theme = layui.customer_theme,
        element = layui.element , tab = layui.customer_tab , ctxPath = rootPath;

    var customer = {

        /**
         * @method  success
         * @params  msg , callback
         * @return  index
         * @desc    成功图标对象
         **/
        ICON_SUCCESS : {icon: 1},

        /**
         * @method  fail
         * @params  msg , callback
         * @return  index
         * @desc    失败图标对象
         **/
        ICON_FAIL : {icon: 2},

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
                customer.deleteLoader(options.loadingTime);
                /** 初始化菜单    */
                menu.render({
                    token: data.homeInfo.token,
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
                console.log(data);
                return;
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
                customer.deleteLoader(options.loadingTime);
            }).fail(function () {
                customer.error('菜单接口有误！');
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
            $('#layuiminiHomeTabId').html('<span class="layuimini-tab-active"></span><span class="disable-close">' + data.homeName + '</span><i class="layui-icon layui-unselect layui-tab-close">ဆ</i>');
            $('#layuiminiHomeTabId').attr('lay-id', "homeTabContent");
            $('#layuiminiHomeTabId').attr('lay-href', ctxPath + data.homeUrl);
            $('#appName').html(data.AppName + ' [' + data.environment + ']');
            customer.reloadPage('homeTabContent' , ctxPath + data.homeUrl);
        },

        /**
         * 刷新页面
         * @param layId
         * @param layUrl
         */
        reloadPage: function (layId , layUrl){
            $.ajax({
                url: layUrl,
                cache: false,
                success: function(html){
                    $('#layuiminiHomeTabContent_' + layId ).html(html);
                }
            });
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
        success: function (title) {
            return layer.msg(title, {icon: 1, shade: this.shade, scrollbar: false, time: 2000, shadeClose: true});
        },

        /**
         * 失败
         * @param title
         * @returns {*}
         */
        error: function (title) {
            return layer.msg(title, {icon: 2, shade: this.shade, scrollbar: false, time: 3000, shadeClose: true});
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
                var loading = layer.load(0, {shade: false, time: 2 * 1000});
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
                var layId = $('#layuiminiHomeTabId').attr('lay-id');
                var layUrl = $('#layuiminiHomeTabId').attr('lay-href');
                customer.reloadPage(layId , layUrl);
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
        }
    };

    exports("customer", customer);
});
