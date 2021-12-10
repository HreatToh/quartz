/**
 * date:2020/02/27
 * author:Mr.Chung
 * version:2.0
 * description:layuimini 菜单框架扩展
 */
layui.define(["element","laytpl"], function (exports) {
    var element = layui.element, laytpl = layui.laytpl, layer = layui.layer , ctxPath = rootPath;

    var menu = {

        /**
         * 菜单初始化
         * @param options.menuList   菜单数据信息
         * @param options.multiModule 是否开启多模块
         * @param options.menuChildOpen 是否展开子菜单
         */
        render: function (options) {
            options.subSystemList = options.subSystemList || [];
            options.menuList = options.menuList || [];
            options.multiModule = options.multiModule || false;
            options.menuChildOpen = options.menuChildOpen || false;
            if (options.multiModule) {
                menu.renderMultiModule(options.subSystemList, options.menuChildOpen);
            } else {
                menu.renderSingleModule(options.menuList, options.menuChildOpen);
            }
            menu.listen();
        },

        /**
         * 单模块
         * @param menuList 菜单数据
         * @param menuChildOpen 是否默认展开
         */
        renderSingleModule: function (menuList, menuChildOpen) {
            menuList = menuList || [];
            var leftMenuHtml = '',
                childOpenClass = '',
                leftMenuCheckDefault = 'layui-this';
            var me = this ;
            if (menuChildOpen) childOpenClass = ' layui-nav-itemed';
            leftMenuHtml = this.renderLeftMenu(menuList,{ childOpenClass:childOpenClass }) ;
            $('.layui-layout-body').addClass('layuimini-single-module'); //单模块标识
            $('.layuimini-header-menu').remove();
            $('.layuimini-menu-left').html(leftMenuHtml);

            element.init();
        },


        /**
         * 渲染一级菜单
         */
        compileSubSystem: function( subSystem , isSub){
            var systemHtml = '<li {{#if( d.menu){ }}  data-system="{{d.menu}}" {{#}}} class="layui-nav-item menu-li  {{d.className}}"  {{#if( d.sysId){ }}  id="multi_module_headerId_{{d.sysId}}" {{#}}} {{#if( d.sysParentId){ }}  parentId="multi_module_headerId_{{d.sysParentId}}" {{#}}} > <a {{#if( d.sysUrl){ }} layuimini-href="{{d.sysUrl}}" {{#}}} {{#if( d.sysId){ }}  layuimini-id="{{d.sysId}}"  {{#}}} {{#if( d.sysTarget){ }}  target="{{d.sysTarget}}" {{#}}} href="javascript:;">{{#if( d.sysIcon){ }}  <i class="{{d.sysIcon}}"></i> {{#}}} <span class="layui-left-nav">{{d.sysName}}</span></a>  {{# if(d.systemChildren){}} {{d.systemChildren}} {{#}}} </li>' ;
            if(isSub){
                systemHtml = '<dd {{#if( d.menu){ }}  data-system="{{d.menu}}" {{#}}} class="menu-dd {{d.childOpenClass}} {{ d.className }}"  {{#if( d.sysId){ }}  id="multi_module_headerId_{{d.sysId}}" {{#}}} {{#if( d.sysParentId){ }}  parentId="multi_module_headerId_{{d.sysParentId}}" {{#}}} > <a href="javascript:;"    {{#if(( !d.systemChildren || !d.systemChildren.length ) && d.sysUrl){ }} layuimini-href="{{d.sysUrl}}" {{#}}} {{#if( d.sysId){ }}  layuimini-id="{{d.sysId}}"  {{#}}} {{#if( d.sysTarget){ }}  target="{{d.sysTarget}}" {{#}}}> {{#if( d.sysIcon){ }}  <i class="{{d.sysIcon}}"></i> {{#}}} <span class="layui-left-nav"> {{d.sysName}}</span></a> {{# if(d.systemChildren){}} {{d.systemChildren}} {{#}}}</dd>'
            }
            return laytpl(systemHtml).render(subSystem);
        },
        /**
         * 渲染一级菜单
         */
        compileMenu: function(menu,isSub){
            var menuHtml = '<li {{#if( d.menu){ }}  data-system-id="{{d.menu}}" {{#}}} class="layui-nav-item menu-li {{d.childOpenClass}} {{d.className}}"  {{#if( d.menuId){ }}  id="multi_module_menuId_{{d.menuId}}" {{#}}} {{#if( d.menuParentId){ }}  parentId="multi_module_menuId_{{d.menuParentId}}" {{#}}}> <a {{#if( d.menuUrl){ }} layuimini-href="{{d.menuUrl}}" {{#}}} {{#if( d.menuId){ }}  layuimini-id="{{d.menuId}}"  {{#}}} {{#if( d.menuTarget){ }}  target="{{d.menuTarget}}" {{#}}} href="javascript:;">{{#if( d.menuIcon){ }}  <i class="{{d.menuIcon}}"></i> {{#}}} <span class="layui-left-nav">{{d.menuName}}</span></a>  {{# if(d.menuChildren){}} {{d.menuChildren}} {{#}}} </li>' ;
            if(isSub){
                menuHtml = '<dd {{#if( d.menu){ }}  data-system-id="{{d.menu}}" {{#}}} class="menu-dd {{d.childOpenClass}} {{ d.className }}" {{#if( d.menuId){ }}  id="multi_module_menuId_{{d.menuId}}" {{#}}} {{#if( d.menuParentId){ }}  parentId="multi_module_menuId_{{d.menuParentId}}" {{#}}}  > <a href="javascript:;"   {{#if(( !d.menuChildren || !d.menuChildren.length ) && d.menuUrl){ }} layuimini-href="{{d.menuUrl}}" {{#}}} {{#if( d.menuId){ }}  layuimini-id="{{d.menuId}}"  {{#}}}  {{#if( d.menuTarget){ }}  target="{{d.menuTarget}}" {{#}}}> {{#if( d.menuIcon){ }}  <i class="{{d.menuIcon}}"></i> {{#}}} <span class="layui-left-nav"> {{d.menuName}}</span></a> {{# if(d.children){}} {{d.children}} {{#}}}</dd>'
            }
            return laytpl(menuHtml).render(menu);
        },

        /**
         * 渲染 菜单容器
         * @param menu
         * @param isSub
         * @returns {string|*}
         */
        compileMenuContainer :function(menu,isSub){
            var wrapperHtml = '<ul class="layui-nav layui-nav-tree layui-left-nav-tree {{d.className}}" id="{{d.id}}">{{d.children}}</ul>' ;
            if(isSub){
                wrapperHtml = '<dl class="layui-nav-child ">{{d.children}}</dl>' ;
            }
            if(!menu.children){
                return "";
            }
            return laytpl(wrapperHtml).render(menu);
        },

        each:function(list,callback){
            var _list = [];
            for(var i = 0 ,length = list.length ; i<length ;i++ ){
                _list[i] = callback(i,list[i]) ;
            }
            return _list ;
        },
        /**
         * 渲染孩子菜单
         * @param menuList
         * @param options
         * @returns {string|*}
         */
        renderChildrenMenu:function(menuList,options){
            var me = menu;
            menuList = menuList || [] ;
            var html = me.each(menuList,function (idx,menu) {
                if(menu.menuChildren && menu.menuChildren.length){
                    menu.menuChildren = me.renderChildrenMenu(menu.menuChildren,{ parentId : options.parentId , childOpenClass: options.childOpenClass || '' });
                }
                if (menu.menuUrl && menu.menuUrl != '#'){
                    menu.menuUrl = ctxPath + menu.menuUrl;
                    menu.menuUrl = $.cusFn.contains(menu.menuUrl , '?') ? menu.menuUrl + '&sysId=' + menu.sysId + '&menuId=' + menu.menuId : menu.menuUrl + '?sysId=' + menu.sysId + '&menuId=' + menu.menuId;
                }
                menu.menu = options.parentId;
                menu.className = "" ;
                menu.childOpenClass = options.childOpenClass || ''
                return me.compileMenu(menu,true)
            }).join("");
            return me.compileMenuContainer({ children : html },true)
        },

        /**
         *  渲染左侧菜单
         * @param leftMenus
         * @param options
         * @returns {string|*}
         */
        renderLeftMenu :function(leftMenus,options){
            options = options || {};
            // 左侧菜单遍历
            var leftMenusHtml =  menu.each(leftMenus || [],function (idx,leftMenu) {
                if (leftMenu.menuUrl && leftMenu.menuUrl != '#'){
                    leftMenu.menuUrl = ctxPath + leftMenu.menuUrl;
                    leftMenu.menuUrl = $.cusFn.contains(leftMenu.menuUrl , '?') ? leftMenu.menuUrl + '&sysId=' + leftMenu.sysId + '&menuId=' + leftMenu.menuId : leftMenu.menuUrl + '?sysId=' + leftMenu.sysId + '&menuId=' + leftMenu.menuId;
                }
                leftMenu.menu = options.parentId;
                var menuChildren = menu.renderChildrenMenu(leftMenu.menuChildren, { parentId : options.parentId , childOpenClass : options.childOpenClass });
                leftMenu.menuChildren = menuChildren;
                leftMenu.childOpenClass = options.childOpenClass;
                leftMenu.className = '';
                var leftMenuHtml = menu.compileMenu(leftMenu);
                return leftMenuHtml ;
            }).join("");

            leftMenusHtml = menu.compileMenuContainer({ id: options.parentId ,className: options.leftMenuCheckDefault,children:leftMenusHtml }) ;
            return leftMenusHtml ;
        },
        /**
         * 多模块
         * @param menuList 菜单数据
         * @param menuChildOpen 是否默认展开
         */
        renderMultiModule: function (subSystemList, menuChildOpen) {
            subSystemList = subSystemList || [];
            var headerMenuHtml = '',
                headerMobileMenuHtml = '',
                leftMenuHtml = '',
                leftMenuCheckDefault = 'layui-this',
                childOpenClass = '',
                headerMenuCheckDefault = 'layui-this';

            if (menuChildOpen) childOpenClass = ' layui-nav-itemed';
            var headerMenuHtml = menu.each(subSystemList, function (index, subSystem) { //顶部菜单渲染
                /** 开始渲染头部系统菜单    */
                subSystem.className = headerMenuCheckDefault;
                subSystem.sysUrl = ctxPath + subSystem.sysUrl;
                subSystem.menu = "multi_module_headerId_" + subSystem.sysId;
                var topMenuItemHtml = menu.compileSubSystem(subSystem);
                /** 开始渲染左侧菜单    */
                leftMenuHtml += menu.renderLeftMenu( subSystem.menus,{
                    parentId: subSystem.menu ,
                    childOpenClass: childOpenClass,
                    leftMenuCheckDefault: leftMenuCheckDefault
                });
                headerMobileMenuHtml += menu.compileSubSystem( subSystem ,true);
                headerMenuCheckDefault = "";
                leftMenuCheckDefault = "layui-hide" ;
                return topMenuItemHtml ;
            }).join("");
            $('.layui-layout-body').addClass('layuimini-multi-module'); //多模块标识
            $('.layuimini-menu-header-pc').html(headerMenuHtml); //电脑
            $('.layuimini-menu-left').html(leftMenuHtml);
            $('.layuimini-menu-header-mobile').html(headerMobileMenuHtml); //手机
            element.init();
        },

        /**
         * 监听
         */
        listen: function () {

            /**
             * 菜单模块切换
             */
            $('body').on('click', '[data-system]', function () {
                var headId = $(this).attr('data-system');
                // header
                $(".layuimini-header-menu .layui-nav-item.layui-this").removeClass('layui-this');
                $(this).addClass('layui-this');
                // left
                $(".layuimini-menu-left .layui-nav.layui-nav-tree").removeClass('layui-this');
                $(".layuimini-menu-left .layui-nav.layui-nav-tree").addClass('layui-hide');
                $(".layuimini-menu-left .layui-nav.layui-nav-tree#" + headId).removeClass('layui-hide');
                $(".layuimini-menu-left .layui-nav.layui-nav-tree#" + headId).addClass('layui-this');
                element.init();
            });

            /**
             * 菜单模块点击事件
             */
            $('body').on('click', '[layuimini-href]', function () {
                var headId = 'multi_module_menuId_' + $(this).attr('layuimini-id');
                $('.layui-nav-item .menu-li .layui-this').removeClass('layui-this');
                if ($(this).parent().find('[parentId = '+ headId +']') && $(this).parent().find('[parentId = '+ headId +']').length > 0){
                    $(this).parent().find('.layui-nav-child').toggle('slide(down)');
                } else {
                    $(this).parent().removeClass('layui-this');
                    $(this).parent().addClass('layui-this');
                }
                element.init();
            });
            /**
             * 菜单缩放
             */
            $('body').on('click', '.layuimini-site-mobile', function () {
                var isShow = $('.layuimini-tool [data-side-fold]').attr('data-side-fold');
                if (isShow == 1) { // 缩放
                    $('.layuimini-tool [data-side-fold]').attr('data-side-fold', 0);
                    $('.layuimini-tool [data-side-fold]').attr('class', 'fa fa-indent');
                    $('.layui-layout-body').removeClass('layuimini-all');
                    $('.layui-layout-body').addClass('layuimini-mini');
                } else { // 正常
                    $('.layuimini-tool [data-side-fold]').attr('data-side-fold', 1);
                    $('.layuimini-tool [data-side-fold]').attr('class', 'fa fa-outdent');
                    $('.layui-layout-body').removeClass('layuimini-mini');
                    $('.layui-layout-body').addClass('layuimini-all');
                    layer.close(window.openTips);
                }
                element.init();
            });
            /**
             * 菜单缩放
             */
            $('body').on('click', '[data-side-fold]', function () {
                var isShow = $('.layuimini-tool [data-side-fold]').attr('data-side-fold');
                if (isShow == 1) { // 缩放
                    $('.layuimini-tool [data-side-fold]').attr('data-side-fold', 0);
                    $('.layuimini-tool [data-side-fold]').attr('class', 'fa fa-indent');
                    $('.layui-layout-body').removeClass('layuimini-all');
                    $('.layui-layout-body').addClass('layuimini-mini');
                    // $(".menu-li").each(function (idx,el) {
                    //     $(el).addClass("hidden-sub-menu");
                    // });

                } else { // 正常
                    $('.layuimini-tool [data-side-fold]').attr('data-side-fold', 1);
                    $('.layuimini-tool [data-side-fold]').attr('class', 'fa fa-outdent');
                    $('.layui-layout-body').removeClass('layuimini-mini');
                    $('.layui-layout-body').addClass('layuimini-all');
                    // $(".menu-li").each(function (idx,el) {
                    //     $(el).removeClass("hidden-sub-menu");
                    // });
                    layer.close(window.openTips);
                }
                element.init();
            });

            /**
             * 手机端点开模块
             */
            $('body').on('click', '.layuimini-header-menu.layuimini-mobile-show dd', function () {
                var check = $('.layuimini-tool [data-side-fold]').attr('data-side-fold');
                if(check === "1"){
                    $('.layuimini-site-mobile').trigger("click");
                    element.init();
                }
            });
        },

    };


    exports("customer_menu", menu);
});
