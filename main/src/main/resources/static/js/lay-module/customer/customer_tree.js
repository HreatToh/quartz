layui.define([ "layer", "tree" ], function (exports) {
    var layer = layui.layer ,
        tree = layui.tree ,
        ctxPath = rootPath;

    var cusTree = {

        /**
         * 获取默认配置参数
         * @returns {{async: {headers: {Authorization}, dataFilter: null, enable: boolean, autoParam: [], dataType: string, otherParam: [], type: string, contentType: string, url: string}, view: {dblClickExpand: boolean, showTitle: boolean, nameIsHTML: boolean, selectedMulti: boolean, showLine: boolean, fontCss: {}, showIcon: boolean, expandSpeed: string}, data: {keep: {parent: boolean, leaf: boolean}, simpleData: {idKey: string, enable: boolean, pIdKey: string, rootPId: null}, render: {name: (function(*, *): string), title: (function(*, *): string)}, key: {children: string, name: string, checked: string, title: string, url: string}}, callback: {onCollapse: callback.onCollapse, onClick: callback.onClick, onExpand: callback.onExpand, onDblClick: callback.onDblClick}, check: {chkStyle: string, autoCheckTrigger: boolean, enable: boolean, chkboxType: {Y: string, N: string}, radioType: string}}}
         */
        getDefaultOption : function(){
            return {
                async : {
                    /** 异步加载时需要自动提交父节点属性的参数。[setting.async.enable = true 时生效]
                     *  默认值：[]
                     */
                    autoParam : [],
                    /** Ajax 提交参数的数据类型。[setting.async.enable = true 时生效]
                     * 默认值："application/json"
                     */
                    contentType : "application/json",
                    /** 用于对 Ajax 返回数据进行预处理的函数。[setting.async.enable = true 时生效]
                     *  默认值：null
                     */
                    dataFilter: null,
                    /**
                     * Ajax 获取的数据类型。[setting.async.enable = true 时生效]
                     * 默认值："text"
                     */
                    dataType: "text",
                    /** 设置 zTree 是否开启异步加载模式
                     * 默认值：false
                     */
                    enable: true,
                    /**
                     * Ajax 请求提交的静态参数键值对。[setting.async.enable = true 时生效]
                     * 默认值：[ ]
                     */
                    otherParam: [],
                    /**
                     * Ajax 的 http 请求模式。[setting.async.enable = true 时生效]
                     * 默认值："post"
                     */
                    type: "post",
                    /**
                     * [setting.async.enable = true 时生效]
                     * 默认值："{}"
                     * v3.5.36+
                     */
                    headers : $.cusFn.getHeaders(),
                    /**
                     * Ajax 获取数据的 URL 地址。[setting.async.enable = true 时生效]
                     * 默认值：""
                     */
                    url: ""
                },
                /**
                 * 函数回调
                 */
                callback: {
                    /**
                     * 用于捕获节点被点击的事件回调函数
                     * @param event js event 对象
                     * @param treeId 对应 zTree 的 treeId，便于用户操控
                     * @param treeNode 被点击的节点 JSON 数据对象
                     * @param clickFlag 节点被点击后的选中操作类型，详细看下表
                     */
                    onClick : function (event , treeId , treeNode , clickFlag ) {
                        $.fn.zTree.getZTreeObj(treeId).checkNode(treeNode, !treeNode.checked, true, true);;
                    },
                    /**
                     * 用于捕获 zTree 上鼠标双击之后的事件回调函数
                     * 如果设置了 setting.callback.beforeDblClick 方法，且返回 false，将无法触发 onDblClick 事件回调函数。
                     * @param event
                     * @param treeId
                     * @param treeNode
                     */
                    onDblClick : function (event , treeId , treeNode ) {
                        console.log('onDblClick' , treeNode);
                    },

                    /**
                     * 用于捕获节点被折叠的事件回调函数
                     * 如果设置了 setting.callback.beforeCollapse 方法，且返回 false，将无法触发 onCollapse 事件回调函数。
                     * @param event
                     * @param treeId
                     * @param treeNode
                     */
                    onCollapse : function (event , treeId , treeNode ) {
                        console.log('onCollapse' , treeNode);
                    },

                    /**
                     * 用于捕获节点被展开的事件回调函数
                     * 如果设置了 setting.callback.beforeExpand 方法，且返回 false，将无法触发 onExpand 事件回调函数。
                     * @param event
                     * @param treeId
                     * @param treeNode
                     */
                    onExpand : function (event , treeId , treeNode ) {
                        console.log('onExpand' , treeNode);
                    },
                },

                /**
                 * 选框配置
                 */
                check : {
                    /**
                     * 设置自动关联勾选时是否触发 beforeCheck / onCheck 事件回调函数。[setting.check.enable = true 且 setting.check.chkStyle = "checkbox" 时生效]
                     * 1、如果设置 setting.check.chkboxType = { "Y": "", "N": "" }，将不会有任何自动关联勾选的操作。
                     * 2、如果开启触发，对于节点较多的树将会影响性能，因为所有被联动勾选的操作都会触发事件回调函数，请根据需要决定是否使用此功能。
                     */
                    autoCheckTrigger : true,
                    /**
                     * 勾选 checkbox 对于父子节点的关联关系。[setting.check.enable = true 且 setting.check.chkStyle = "checkbox" 时生效]
                     */
                    chkboxType : { "Y": "p", "N": "s" },
                    /**
                     * 勾选框类型(checkbox 或 radio）[setting.check.enable = true 时生效]
                     * 默认值："radio"
                     */
                    chkStyle : 'radio',
                    /**
                     * 设置 zTree 的节点上是否显示 checkbox / radio
                     * 默认值: true
                     */
                    enable : true,
                    /**
                     * radio 的分组范围。[setting.check.enable = true 且 setting.check.chkStyle = "radio" 时生效]
                     * 默认值："level"
                     */
                    radioType: 'all'
                },

                /**
                 * 数据配置
                 */
                data : {
                    keep : {
                        /**
                         * zTree 的节点叶子节点属性锁，是否始终保持 isParent = false
                         * 默认值：false
                         */
                        leaf : false,
                        /**
                         * zTree 的节点父节点属性锁，是否始终保持 isParent = true
                         * 默认值：false
                         */
                        parent : false
                    },
                    key : {
                        /**
                         * zTree 节点数据中保存 check 状态的属性名称。
                         * 默认值："checked"
                         */
                        checked : 'checked',
                        /**
                         * zTree 节点数据中保存子节点数据的属性名称。
                         * 默认值："children"
                         */
                        children : 'children',

                        /**
                         * zTree 节点数据保存节点名称的属性名称。
                         * 默认值："name"
                         */
                        name : 'name',
                        /**
                         * zTree 节点数据保存节点提示信息的属性名称。[setting.view.showTitle = true 时生效]
                         * 如果设置为 "" ，则自动与 setting.data.key.name 保持一致，避免用户反复设置
                         * 默认值：""
                         */
                        title : 'title',

                        /**
                         * zTree 节点数据保存节点链接的目标 URL 的属性名称。
                         * 特殊用途：当后台数据只能生成 url 属性，又不想实现点击节点跳转的功能时，可以直接修改此属性为其他不存在的属性名称
                         * 默认值："url"
                         */
                        url : 'url'
                    },

                    /**
                     * 简单数据配置
                     */
                    simpleData : {
                        /**
                         * 确定 zTree 初始化时的节点数据、异步加载时的节点数据、或 addNodes 方法中输入的 newNodes 数据是否采用简单数据模式 (Array)
                         * 不需要用户再把数据库中取出的 List 强行转换为复杂的 JSON 嵌套格式
                         * 默认值：false
                         */
                        enable : true,
                        /**
                         * 节点数据中保存唯一标识的属性名称。[setting.data.simpleData.enable = true 时生效]
                         * 默认值："id"
                         */
                        idKey: 'id',
                        /**
                         * 节点数据中保存其父节点唯一标识的属性名称。[setting.data.simpleData.enable = true 时生效]
                         * 默认值："pid"
                         */
                        pIdKey: 'pid',
                        /**
                         * 用于修正根节点父节点数据，即 pIdKey 指定的属性值。[setting.data.simpleData.enable = true 时生效]
                         * 默认值：null
                         */
                        rootPId: null,
                    }
                },
                /**
                 * 显示配置
                 */
                view : {
                    /**
                     * 双击节点时，是否自动展开父节点的标识
                     * 默认值: true
                     */
                    dblClickExpand : true,
                    /**
                     * zTree 节点展开、折叠时的动画速度，设置方法同 JQuery 动画效果中 speed 参数。
                     * IE6 下会自动关闭动画效果，以保证 zTree 的操作速度
                     * 默认值："fast"
                     */
                    expandSpeed : 'fast',
                    /**
                     * 个性化文字样式，只针对 zTree 在节点上显示的<A>对象。
                     * 默认值：{}
                     */
                    fontCss : {},
                    /**
                     * 设置 name 属性是否支持 HTML 脚本
                     * 如果允许 HTML 脚本，请根据自己的需求做校验，避免出现 js 注入等安全问题。
                     * 默认值: false
                     */
                    nameIsHTML : true,
                    /**
                     * 设置是否允许同时选中多个节点。
                     * 默认值: false
                     */
                    selectedMulti : false,
                    /**
                     * 设置 zTree 是否显示节点的图标。
                     * 默认值：false
                     */
                    showIcon : false,
                    /**
                     * 设置 zTree 是否显示节点之间的连线。
                     * 默认值：true
                     */
                    showLine : true,
                    /**
                     * 设置 zTree 是否显示节点的 title 提示信息(即节点 DOM 的 title 属性)。
                     * 请务必与 setting.data.key.title 同时使用。
                     * 默认值：true
                     */
                    showTitle : true
                }

            };
        },

        /**
         *  用于使用自定义的方法动态渲染节点名称
         *  默认值：null
         *  v3.5.45+
         * @param name
         * @param treeNode
         * @returns {*}
         */
        renderName : function (name , treeNode) {
            treeNode.iconSkin = treeNode.iconSkin || 'fa fa-snowflake-o';
            return '<span>[' + treeNode.id + ']</span> <i class="' + treeNode.iconSkin + '"></i> <span style="font-weight: bold">' + name + '</span>';
        },
        /**
         *  用于使用自定义的方法动态渲染节点标题
         *  默认值：null
         *  v3.5.45+
         * @param name
         * @param treeNode
         * @returns {*}
         */
        renderTitle : function (title , treeNode) {
            return '[' + treeNode.id + '] ' + title;
        },

        /**
         * 开始渲染
         * options.url 请求数据的地址
         * @param options
         */
        render : function (options) {
            $.cusPost(options.url , function (data) {
                $.fn.zTree.init($(options.elem), cusTree.getOptions(options) , cusTree.renderData(options , data));
                $(options.elem).css('overflow' , 'auto');
                cusTree.lisener(options);
            });
        },

        /**
         * 初始化高度
         * @param height
         */
        initHeight : function(elem , height){
            $(elem).height(height);
        },

        /**
         * 获取配置信息
         * @param options
         */
        getOptions : function(options){
            var setting = cusTree.getDefaultOption();
            /** 做默认配置跟现有配置的映射关系    */
            setting.async.url = options.url || '';
            setting.view.addDiyDom = options.addDiyDom || function(){};
            /** 是否渲染自定义的名称 和标题    */
            options.isShowCusName = options.isShowCusName || true;
            options.isShowCusTitle = options.isShowCusTitle || true;
            options.height = options.height || 'full-40';
            return setting;
        },
        /**
         * 渲染异步数据
         * @param options
         * @param data
         */
        renderData : function (options , data) {
            $.each(data , function (i , o) {
                if (options.isShowCusName){
                    o.name = cusTree.renderName(o.name , o);
                }
                if (options.isShowCusTitle){
                    o.title = cusTree.renderTitle(o.title , o);
                }
            });
            return data;
        },

        /**
         * 监听
         * @param options
         */
        lisener: function (options) {

            var h = options.height;
            /** 监听窗口大小    */
            if (options.height && options.height.startsWith('full-') && options.height.split('-').length > 0){
                h = $(window).height() - options.height.split('-')[1] + 10;
                $(window).resize(function () {
                    cusTree.initHeight(options.elem , $(window).height() - options.height.split('-')[1] + 10);
                });
            }
            cusTree.initHeight(options.elem , h);
        }
    };
    exports("customer_tree", cusTree);
});