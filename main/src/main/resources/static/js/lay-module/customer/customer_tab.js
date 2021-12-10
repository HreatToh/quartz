/**
 * date:2020/02/27
 * author:Mr.Chung
 * version:2.0
 * description:layuimini tab框架扩展
 */
layui.define(["element", "layer"], function (exports) {
    var element = layui.element, layer = layui.layer , ctxPath = rootPath;
    var tab = {
        /**
         * 初始化tab
         * @param options
         */
        render: function (options) {
            options.filter = options.filter || null;
            options.multiModule = options.multiModule || false;
            options.urlHashLocation = options.urlHashLocation || false;
            options.maxTabNum = options.maxTabNum || 20;
            options.menuList = options.menuList || [];
            options.subSystemList = options.subSystemList || [];  // todo 后期菜单想改为不操作dom, 而是直接操作初始化传过来的数据
            options.homeInfo = options.homeInfo || {};
            options.listenSwichCallback = options.listenSwichCallback || function () {

            };
            tab.listen(options);
            tab.listenRoll();
            tab.listenSwitch(options);
            tab.listenHash(options);
        },

        /**
         * 新建tab窗口
         * @param options.tabId
         * @param options.href
         * @param options.title
         * @param options.isIframe
         * @param options.maxTabNum
         */
        create: function (options) {
            options.tabId = options.tabId || null;
            options.href = options.href || null;
            options.title = options.title || null;
            options.isIframe = options.isIframe || false;
            options.maxTabNum = options.maxTabNum || 20;
            if ($(".layuimini-tab .layui-tab-title li").length >= options.maxTabNum) {
                layer.msg('Tab窗口已达到限定数量，请先关闭部分Tab');
                return false;
            }
            var ele = element;
            if (options.isIframe) ele = parent.layui.element;
            ele.tabAdd('layuiminiTab', {
                title: '<span class="layuimini-tab-active"></span><span>' + options.title + '</span><i class="layui-icon layui-unselect layui-tab-close">ဆ</i>' //用于演示
                // , content: '<iframe width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0"   src="' + options.href + '"></iframe>'
                , content: '<div width="100%" height="100%" style="margin: 10px" id="layuiminiHomeTabContent_' + options.tabId + '" href="' + options.href + '" class="layuimini-container"><div class="layuimini-main"></div></div>'
                , id: options.tabId
            });
            $('[lay-id=' + options.tabId + ']').attr('id' , 'layuiminiHomeTabId_' + options.tabId);
            $('[lay-id=' + options.tabId + ']').attr('lay-href' , options.href);
            tab.reloadPage(options.tabId , options.href);
            $('.layuimini-menu-left').attr('layuimini-tab-tag', 'add');
            sessionStorage.setItem('layuiminimenu_' + options.tabId, options.title);
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
                    var height = $('.layui-body').height() - 55 ;
                    $('#layuiminiHomeTabContent_' + layId ).find('.layuimini-main').html(html);
                    $('#layuiminiHomeTabContent_' + layId).height(height);
                    $('#layuiminiHomeTabContent_' + layId).css('overflow' , 'auto');
                    if ( Boolean($('#isTimeOut').attr('content'))){
                        window.location.reload();
                    }
                }
            });
        },
        /**
         * 切换选项卡
         * @param tabhref
         */
        change: function (tabhref) {
            element.tabChange('layuiminiTab', tabhref);
        },

        /**
         * 删除tab窗口
         * @param tabId
         * @param isParent
         */
        delete: function (tabId, isParent) {
            // todo 未知BUG，不知道是不是layui问题，必须先删除元素
            $(".layuimini-tab .layui-tab-title .layui-unselect.layui-tab-bar").remove();

            if (isParent === true) {
                parent.layui.element.tabDelete('layuiminiTab', tabId);
            } else {
                element.tabDelete('layuiminiTab', tabId);
            }
        },

        /**
         * 在iframe层打开新tab方法
         */
        openNewTabByIframe: function (options) {
            options.href = options.href || null;
            options.title = options.title || null;
            if (options.href === null || options.href === undefined) options.href = new Date().getTime();
            var checkTab = tab.check(options.href, true);
            if (!checkTab) {
                tab.create({
                    tabId: options.href,
                    href: options.href,
                    title: options.title,
                    isIframe: true,
                });
            }
            parent.layui.element.tabChange('layuiminiTab', options.href);
        },

        /**
         * 在iframe层关闭当前tab方法
         */
        deleteCurrentByIframe: function () {
            var ele = $(".layuimini-tab .layui-tab-title li.layui-this", parent.document);
            if (ele.length > 0) {
                var layId = $(ele[0]).attr('lay-id');
                tab.delete(layId, true);
            }
        },

        /**
         * 判断tab窗口
         */
        check: function (tabId, isIframe) {
            // 判断选项卡上是否有
            var checkTab = false;
            if (isIframe === undefined || isIframe === false) {
                $(".layui-tab-title li").each(function () {
                    var checkTabId = $(this).attr('lay-id');
                    if (checkTabId != null && checkTabId === tabId) {
                        checkTab = true;
                    }
                });
            } else {
                parent.layui.$(".layui-tab-title li").each(function () {
                    var checkTabId = $(this).attr('lay-id');
                    if (checkTabId != null && checkTabId === tabId) {
                        checkTab = true;
                    }
                });
            }
            return checkTab;
        },

        /**
         * 开启tab右键菜单
         * @param tabId
         * @param left
         */
        openTabRignMenu: function (tabId, left) {
            tab.closeTabRignMenu();
            var menuHtml = '<div class="layui-unselect layui-form-select layui-form-selected layuimini-tab-mousedown layui-show" data-tab-id="' + tabId + '" style="left: ' + left + 'px!important;top:-35px!important;">\n' +
                '<dl>\n' +
                '<dd><a href="javascript:;" layuimini-tab-menu-close="current">关 闭 当 前</a></dd>\n' +
                '<dd><a href="javascript:;" layuimini-tab-menu-close="other">关 闭 其 他</a></dd>\n' +
                '<dd><a href="javascript:;" layuimini-tab-menu-close="all">关 闭 全 部</a></dd>\n' +
                '</dl>\n' +
                '</div>';
            var makeHtml = '<div class="layuimini-tab-make"></div>';
            $('.layuimini-tab .layui-tab-title').after(menuHtml);
            $('.layuimini-tab .layui-tab-content').after(makeHtml);
        },

        /**
         * 关闭tab右键菜单
         */
        closeTabRignMenu: function () {
            $('.layuimini-tab-mousedown').remove();
            $('.layuimini-tab-make').remove();
        },

        /**
         * 查询菜单信息
         * @param menuId
         * @param menuList
         */
        searchMenu: function (menuId, menuList) {
            var menu;
            for (key in menuList) {
                var item = menuList[key];
                if (item.menuId === menuId) {
                    menu = item;
                    break;
                }
                if (item.menuChildren) {
                    newMenu = tab.searchMenu(menuId, item.item.menuChildren);
                    if (newMenu) {
                        menu = newMenu;
                        break;
                    }
                }
            }
            return menu;
        },

        /**
         * 监听
         * @param options
         */
        listen: function (options) {
            options = options || {};
            options.maxTabNum = options.maxTabNum || 20;

            /**
             * 打开新窗口
             */
            $('body').on('click', '[layuimini-href]', function () {
                var tabId = $(this).attr('layuimini-id') , href = $(this).attr('layuimini-href'), title = $(this).text(), target = $(this).attr('target');

                if ($(this).parent().attr('data-system')){
                    tabId = 'homeTabContent';
                    title = '首页';
                }
                /** 如果地址不存在则不进行操作    */
                if (!href || href === '#' ){ return false; }
                layer.close(window.openTips);
                if (target === '_blank') {
                    window.open(href, target);
                    return false;
                }
                /** 处理Url地址链接    */
                if (href.indexOf(ctxPath) == -1){
                    href = ctxPath + (href.startsWith('/') ? href : '/' + href );
                }
                if (tabId === null || tabId === undefined) tabId = new Date().getTime();
                var checkTab = tab.check(tabId);
                if (!checkTab) {
                    tab.create({
                        tabId: tabId,
                        href: href,
                        title: title,
                        isIframe: false,
                        maxTabNum: options.maxTabNum,
                    });
                }
                element.tabChange('layuiminiTab', tabId);
            });

            /**
             * 在iframe子菜单上打开新窗口
             */
            $('body').on('click', '[layuimini-content-href]', function () {
                var tabId = $(this).attr('layuimini-content-id'),
                    href = $(this).attr('layuimini-content-href'),
                    title = $(this).attr('data-title'),
                    target = $(this).attr('target');
                if (target === '_blank') {
                    window.open(href, "_blank");
                    return false;
                }
                /** 处理Url地址链接    */
                if (href.indexOf(ctxPath) == -1){
                    /** 原型地址  开发完后需要注释掉    */
                    href = href.endsWith('.html') ? 'static/layuimini/' + href : href;
                    href = ctxPath + (href.startsWith('/') ? href : '/' + href );
                }
                if (tabId === null || tabId === undefined) tabId = new Date().getTime();
                var checkTab = tab.check(tabId);
                if (!checkTab) {
                    tab.create({
                        tabId: tabId,
                        href: href,
                        title: title,
                        isIframe: false,
                        maxTabNum: options.maxTabNum,
                    });
                }
                element.tabChange('layuiminiTab', tabId);
            });

            /**
             * 关闭选项卡
             **/
            $('body').on('click', '.layuimini-tab .layui-tab-title .layui-tab-close', function () {
                var $parent = $(this).parent();
                var tabId = $parent.attr('lay-id');
                if (tabId !== undefined || tabId !== null) {
                    tab.delete(tabId);
                }
            });

            /**
             * 选项卡操作
             */
            $('body').on('click', '[layuimini-tab-close]', function () {
                var closeType = $(this).attr('layuimini-tab-close');
                $(".layuimini-tab .layui-tab-title li").each(function () {
                    var tabId = $(this).attr('lay-id');
                    var id = $(this).attr('id');
                    var isCurrent = $(this).hasClass('layui-this');
                    if (id !== 'layuiminiHomeTabId_homeTabContent') {
                        if (closeType === 'all') {
                            tab.delete(tabId);
                        } else {
                            if (closeType === 'current' && isCurrent) {
                                tab.delete(tabId);
                            } else if (closeType === 'other' && !isCurrent) {
                                tab.delete(tabId);
                            }
                        }
                    }
                });
            });

            /**
             * 禁用网页右键
             */
            $(".layuimini-tab .layui-tab-title").unbind("mousedown").bind("contextmenu", function (e) {
                e.preventDefault();
                return false;
            });

            /**
             * 注册鼠标右键
             */
            $('body').on('mousedown', '.layuimini-tab .layui-tab-title li', function (e) {
                var left = $(this).offset().left - $('.layuimini-tab ').offset().left + ($(this).width() / 2),
                tabId = $(this).attr('lay-id');
                if (e.which === 3) {
                    tab.openTabRignMenu(tabId, left);
                }
            });

            /**
             * 关闭tab右键菜单
             */
            $('body').on('click', '.layui-body,.layui-header,.layuimini-menu-left,.layuimini-tab-make', function () {
                tab.closeTabRignMenu();
            });

            /**
             * tab右键选项卡操作
             */
            $('body').on('click', '[layuimini-tab-menu-close]', function () {
                var closeType = $(this).attr('layuimini-tab-menu-close'),
                    currentTabId = $('.layuimini-tab-mousedown').attr('data-tab-id');
                $(".layuimini-tab .layui-tab-title li").each(function () {
                    var tabId = $(this).attr('lay-id');
                    var id = $(this).attr('id');
                    if (id !== 'layuiminiHomeTabId_homeTabContent') {
                        if (closeType === 'all') {
                            tab.delete(tabId);
                        } else {
                            if (closeType === 'current' && currentTabId === tabId) {
                                tab.delete(tabId);
                            } else if (closeType === 'other' && currentTabId !== tabId) {
                                tab.delete(tabId);
                            }
                        }
                    }
                });
                tab.closeTabRignMenu();
            });
        },

        /**
         * 监听tab切换
         * @param options
         */
        listenSwitch: function (options) {
            options.filter = options.filter || null;
            options.multiModule = options.multiModule || false;
            options.urlHashLocation = options.urlHashLocation || false;
            options.listenSwichCallback = options.listenSwichCallback || function () { };
            element.on('tab(' + options.filter + ')', function (data) {
                var tabId = $(this).attr('lay-id');
                var tabhref = $(this).attr('lay-href');
                if (options.urlHashLocation) {
                    location.hash = tabhref.startsWith('/') ? tabhref : '/' + tabhref;
                    location.hashId = tabId;
                }
                if (typeof options.listenSwichCallback === 'function') {
                    options.listenSwichCallback();
                }
                // 判断是否为新增窗口
                if ($('.layuimini-menu-left').attr('layuimini-tab-tag') === 'add') {
                    $('.layuimini-menu-left').attr('layuimini-tab-tag', 'no')
                } else {
                    $("[layuimini-href]").parent().removeClass('layui-this');
                }
                tab.rollPosition();
            });
        },

        /**
         * 过去菜单集合
         * @param menuList
         * @param subSystemList
         */
        getMenuList: function(menuList , subSystemList){
            menuList = menuList || [];
            for (var i in subSystemList) {
                if (subSystemList[i].menus && subSystemList[i].menus.length > 0){
                    for (var menu in subSystemList[i].menus) {
                        menuList.push(menu);
                    }
                }
            }
            return menuList;
        },
        /**
         * 监听hash变化
         * @param options
         * @returns {boolean}
         */
        listenHash: function (options) {
            options.urlHashLocation = options.urlHashLocation || false;
            options.maxTabNum = options.maxTabNum || 20;
            options.homeInfo = options.homeInfo || {};
            options.subSystemList = options.subSystemList || [];
            options.menuList = options.menuList || [];
            if (!options.urlHashLocation){
                return false;
            }
            var tabId = location.hashId;
            var tabhref = location.hash;

            if (tabhref === null || tabhref === undefined || tabhref ==='') return false;
            // 判断是否为首页
            if(tabhref === options.homeInfo.href) return false;

            // 判断是否为右侧菜单
            var menu = tab.searchMenu( tabId , tab.getMenuList(menuList , options.subSystemList));
            if (menu !== undefined) {
                tab.create({
                    tabId: tabId,
                    href: tabhref,
                    title: menu.title,
                    isIframe: false,
                    maxTabNum: options.maxTabNum,
                });
                $('.layuimini-menu-left').attr('layuimini-tab-tag', 'no');
                element.tabChange('layuiminiTab', tabId);
                return false;
            }

            // 判断是否为快捷菜单
            var isSearchMenu = false;
            $("[layuimini-content-href]").each(function () {
                if ($(this).attr("layuimini-content-id") === tabId) {
                    var title = $(this).attr("data-title");
                    tab.create({
                        tabId: tabId,
                        href: tabhref,
                        title: title,
                        isIframe: false,
                        maxTabNum: options.maxTabNum,
                    });
                    $('.layuimini-menu-left').attr('layuimini-tab-tag', 'no');
                    element.tabChange('layuiminiTab', tabId);
                    isSearchMenu = true;
                    return false;
                }
            });
            if (isSearchMenu){
                return false;
            }

            // 既不是右侧菜单、快捷菜单,就直接打开
            var title = sessionStorage.getItem('layuiminimenu_' + tabId) === null ? tabId : sessionStorage.getItem('layuiminimenu_' + tabId);
            tab.create({
                tabId: tabId,
                href: tabhref,
                title: title,
                isIframe: false,
                maxTabNum: options.maxTabNum,
            });
            element.tabChange('layuiminiTab', tabId);
            return false;
        },

        /**
         * 监听滚动
         */
        listenRoll: function () {
            $(".layuimini-tab-roll-left").click(function () {
                tab.rollClick("left");
            });
            $(".layuimini-tab-roll-right").click(function () {
                tab.rollClick("right");
            });
        },

        /**
         * 单模块切换
         * @param tabId
         */
        listenSwitchSingleModule: function (tabId) {
            $("[layuimini-href]").each(function () {
                if ($(this).attr("layuimini-id") === tabId) {
                    // 自动展开菜单栏
                    var addMenuClass = function ($element, type) {
                        if (type === 1) {
                            $element.addClass('layui-this');
                            if ($element.hasClass('layui-nav-item') && $element.hasClass('layui-this')) {
                                $(".layuimini-header-menu li").attr('class', 'layui-nav-item');
                            } else {
                                addMenuClass($element.parent().parent(), 2);
                            }
                        } else {
                            $element.addClass('layui-nav-itemed');
                            if ($element.hasClass('layui-nav-item') && $element.hasClass('layui-nav-itemed')) {
                                $(".layuimini-header-menu li").attr('class', 'layui-nav-item');
                            } else {
                                addMenuClass($element.parent().parent(), 2);
                            }
                        }
                    };
                    addMenuClass($(this).parent(), 1);
                    return false;
                }
            });
        },

        /**
         * 多模块切换
         * @param tabId
         */
        listenSwitchMultiModule: function (tabId ) {
            $("[layuimini-href]").each(function () {
                if ($(this).attr("layuimini-id") === tabId) {
                    // 自动展开菜单栏
                    var addMenuClass = function ($element) {
                        var parentid = $element.attr('parentid');
                        if (parentid){
                            $('#' + parentid).find('.layui-nav-child').toggle('slide(down)');
                            addMenuClass($element.parent().parent());
                        } else{
                            $element.addClass('layui-this');
                        }
                    };
                    addMenuClass($(this).parent());
                    return false;
                }
            });
        },

        /**
         * 自动定位
         */
        rollPosition: function () {
            var $tabTitle = $('.layuimini-tab  .layui-tab-title');
            var autoLeft = 0;
            $tabTitle.children("li").each(function () {
                if ($(this).hasClass('layui-this')) {
                    return false;
                } else {
                    autoLeft += $(this).outerWidth();
                }
            });
            $tabTitle.animate({
                scrollLeft: autoLeft - $tabTitle.width() / 3
            }, 200);
        },

        /**
         * 点击滚动
         * @param direction
         */
        rollClick: function (direction) {
            var $tabTitle = $('.layuimini-tab  .layui-tab-title');
            var left = $tabTitle.scrollLeft();
            if ('left' === direction) {
                $tabTitle.animate({
                    scrollLeft: left - 450
                }, 200);
            } else {
                $tabTitle.animate({
                    scrollLeft: left + 450
                }, 200);
            }
        }

    };

    exports("customer_tab", tab);
});
