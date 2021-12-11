layui.define([ "layer", "table" ], function (exports) {
    var layer = layui.layer ,
        table = layui.table ,
        ctxPath = rootPath ;

    /** 自定义表格    */
    var cusGrid = {

        /**
         * options.elem String/DOM 指定原始 table 容器的选择器或 DOM，方法渲染方式必填
         * options.cols Array 设置表头。值是一个二维数组。方法渲染方式必填
         * options.url（等） 异步数据接口相关参数。其中 url 参数为必填项
         * options.method	接口http请求类型，默认：post
         * options.headers  设置请求头 $.cusFn.getHeaders();
         * options.parseData 设置解析参数 options.parseData || cusGrid.getParseData();
         * options.request  设置请求参数 options.request || cusGrid.getRequest();
         * options.response 设置响应参数 options.response || cusGrid.getResponse();
         * options.toolbar String/DOM/Boolean 开启表格头部工具栏区域，该参数支持四种类型值：
         * options.defaultToolbar Array	该参数可自由配置头部工具栏右侧的图标按钮
         * options.width	Number	设定容器宽度。table容器的默认宽度是跟随它的父元素铺满，你也可以设定一个固定值，当容器中的内容超出了该宽度时，会自动出现横向滚动条。
         * options.height   Number/String	设定容器高度 'full-20';
         * options.cellMinWidth （layui 2.2.1 新增）全局定义所有常规单元格的最小宽度（默认：60），一般用于列宽自动分配的情况。其优先级低于表头参数中的 minWidth 100;
         * options.done	Function	数据渲染完的回调。你可以借此做一些其它的操作
         * options.error	Function	数据请求失败的回调，返回两个参数：错误对象、内容 layui 2.6.0 新增
         * options.data	Array	直接赋值数据。既适用于只展示一页数据，也非常适用于对一段已知数据进行多页展示。
         * options.escape	Boolean	是否开启 xss 字符过滤（默认 false）layui 2.6.8 新增
         * options.totalRow	Boolean/String	是否开启合计行区域
         * options.page	Boolean/Object	开启分页（默认：false）。支持传入一个对象，里面可包含 laypage 组件所有支持的参数（jump、elem除外）
         * options.limit	Number	每页显示的条数（默认 10）。值需对应 limits 参数的选项。 注意：优先级低于 page 参数中的 limit 参数
         * options.limits	Array	每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。 注意：优先级低于 page 参数中的 limits 参数	[30,60,90]
         * options.loading	Boolean	是否显示加载条（默认 true）。若为 false，则在切换分页时，不会出现加载条。该参数只适用于 url 参数开启的方式	false
         * options.title	String	定义 table 的大标题（在文件导出等地方会用到）	"用户表"
         * options.text	Object	自定义文本，如空数据时的异常提示等。
         * options.autoSort	Boolean	默认 true，即直接由 table 组件在前端自动处理排序。 若为 false，则需自主排序，即由服务端返回排序好的数据
         * 渲染表格
         */
        getOptions : function(options){
            options.elem = options.elem || '#cusGird';
            options.cols = cusGrid.getCols(options.cols) || [];
            options.url  = $.cusFn.getUrl(options.url);
            options.method = options.method || 'post';
            options.headers = $.cusFn.getHeaders();
            options.parseData = options.parseData || function (result){ return cusGrid.getParseData(result)};
            options.request = options.request || cusGrid.getRequest();
            options.response = options.response || cusGrid.getResponse();
            options.toolbar = options.toolbar || 'false';
            options.defaultToolbar = options.defaultToolbar || ['filter', 'print', 'exports']
            if (options.width){
                options.width = options.width || 1000
            }
            options.height = options.height || 'full-100';
            options.cellMinWidth = options.cellMinWidth || 100;
            options.done = options.done || function(){};
            options.error = options.error || function(){};
            options.data = options.data || [];
            options.escape = options.escape || false;
            options.totalRow = options.totalRow || false;
            options.page = options.page || cusGrid.getDefPageOptions();
            options.limit = options.limit || 0;
            options.limits = options.limits || [];
            options.loading = options.loading || true;
            options.title = options.title || new Date().getTime;
            options.text = options.text || { none: '暂无相关数据'  }
            options.autoSort = options.autoSort || true;
            options.id = options.id || options.elem;
            options.filter = options.filter || options.id || options.elem;
            options.filter = 'filter-' + options.filter;
            options.size = options.size || 'sm';
            // options.skin = options.skin || 'line';
            options.even = options.even || true;
            $(options.elem).attr('lay-filter' , options.filter);
            return options;
        },

        /**
         * 渲染表格
         * @param options
         * @returns {*} 返回表格参数
         */
        render : function (options) {
            options = cusGrid.getOptions(options);
            cusGrid.listen(options);
            return table.render(options);
        },

        /**
         * 获取字段信息
         * @param cols
         * @returns {*}
         */
        getCols: function(colss){
            $.each( colss ,function (i , cols) {
                $.each( cols , function ( ii , col ) {
                    if (!col.type){
                        var align = col.align || 'center';
                        var template = col.templet;
                        col = $.extend( cusGrid.getDefColOptions() , col );
                        col.templet = function (d) {
                            var temp = d[col.field];
                            if (template && template instanceof Function){
                                temp = template(d);
                            }
                            if (d[col.field]){
                                return '<div lay-cus-title="' + d[col.field] + '" style="text-align: ' + align + '"> ' + temp + ' </div>';
                            }
                            return '<div style="text-align: ' + align + '"> ' + temp + ' </div>';
                        };
                        col.align = 'center';
                        cols[ii] = col;
                    }
                });
            });
            return colss;
        },
        /**
         * 获取默认的字段
         */
        getDefColOptions: function(){
            return {
                /** minWidth	Number	局部定义当前常规单元格的最小宽度（默认：60），一般用于列宽自动分配的情况。其优先级高于基础参数中的 cellMinWidth	100    */
                minWidth : 60,
                /** type	String	设定列类型。默认： normal（常规列，无需设定） */
                type : 'normal',
                /** LAY_CHECKED	Boolean	是否全选状态（默认：false）。必须复选框列开启后才有效，如果设置 true，则表示复选框默认全部选中。	true    */
                LAY_CHECKED : false,
                /** hide	Boolean	是否初始隐藏列，默认：false。layui 2.4.0 新增	true    */
                hide : false,
                /** 是否开启该列的自动合计功能，默认：false。    */
                totalRow : false,
                /** 是否允许排序（默认：false）。如果设置 true，则在对应的表头显示排序icon，从而对列开启排序功能。    */
                sort : false,
                /** 	是否禁用拖拽列宽（默认：false）。    */
                unresize : false,
                /** align	String	单元格排列方式。可选值有：left（默认）、center（居中）、right（居右）	center    */
                align : 'center',
                /** toolbar	String	绑定工具条模板。可在每行对应的列中出现一些自定义的操作性按钮    */
                toolbar: ''
            };
        },
        /**
         * 获取默认的分页配置参数
         */
        getDefPageOptions: function () {
            return {
                /** count	数据总数。一般通过服务端得到	Number    */
                // count : 20,
                /** limit	每页显示的条数。laypage将会借助 count 和 limit 计算出分页数。	Number    */
                limit : 20,
                /** limits	每页条数的选择项。如果 layout 参数开启了 limit，则会出现每页条数的select选择框    */
                limits : [20, 30, 50 , 100 , 500 , 1000],
                /** curr 起始页。一般用于刷新类型的跳页以及HASH跳页    */
                curr: 1,
                /** groups	连续出现的页码个数	Number	5    */
                groups : 5,
                /** prev	自定义“上一页”的内容，支持传入普通文本和HTML	String	上一页    */
                prev : '上一页',
                /** next	自定义“下一页”的内容，同上	String	下一页    */
                next : '下一页',
                /** first	自定义“首页”的内容，同上	String	1    */
                first: '首页',
                /** last	自定义“尾页”的内容，同上	String	总页数值    */
                last : '尾页',
                /** layout	自定义排版。可选值有：count（总条目输区域）、prev（上一页区域）、page（分页区域）、next（下一页区域）、limit（条目选项区域）、refresh（页面刷新区域。注意：layui 2.3.0 新增） 、skip（快捷跳页区域）	Array    */
                layout : ['prev', 'page', 'next' , 'count' , 'limit', 'skip' , 'refresh' ]
            };
        },

        /**
         * 解析数据
         */
        getParseData : function(result){
            return  {
                /** 解析接口状态    */
                code: result.code,
                /** 解析提示文本    */
                msg: result.msg,
                /** 解析数据长度    */
                total: result.data.total,
                /** 解析数据列表    */
                rows: result.data.records
            };
        },
        /**
         * 设置请求参数
         * @returns {{limitName: string, pageName: string}}
         */
        getRequest: function(){
            return {
                /** 页码的参数名称，默认：page    */
                pageName: $.cus.pageKey ,
                /** 每页数据量的参数名，默认：limit    */
                limitName: $.cus.limitKey
            };
        },

        /**
         * 设置响应参数
         * @returns {{countName: string, statusName: string, msgName: string, dataName: string, statusCode: number}}
         */
        getResponse: function(){
            return {
                /** 规定数据状态的字段名称，默认：code    */
                statusName: 'code'
                /** 规定成功的状态码，默认：200    */
                ,statusCode: 200
                /** 规定状态信息的字段名称，默认：msg    */
                ,msgName: 'msg'
                /** 规定数据总数的字段名称，默认：total    */
                ,countName: 'total'
                /** 规定数据列表的字段名称，默认：rows    */
                ,dataName: 'rows'
            };
        },
        /**
         * 设定全局默认参数。options即各项基础参数
         * @param options
         */
        set : function (options) {
            table.set(options);
        },

        /**
         * 获取表格选中行（下文会有详细介绍）。id 即为 id 参数对应的值
         * @param gridId
         * @returns {*}
         */
        checkStatus : function(gridId){
            return table.checkStatus(gridId);
        },

        /**
         * 表格重载
         */
        reload : function(gridId , options , deep){
            table.reload(gridId, options, deep);
        },


        /**
         * 重置表格尺寸
         */
        resize : function(gridId){
            table.resize(gridId);
        },
        /**
         * 导出数据
         * @param id
         * @param data
         * @param type
         */
        exportFile : function(gridId, data, type){
            table.exportFile(gridId, data, type);
        },

        /**
         * 用于获取表格当前页的所有行数据（layui 2.6.0 开始新增）
         * @param gridId
         * @returns {*}
         */
        getData : function(gridId){
            return table.getData(id);
        },

        /**
         * 初始化监听
         * @param options
         */
        listen : function (options) {
            /** 开始初始化监听    */
            if (options.listener){
                $.each(options.listener , function (key , callback) {
                    var event = key + '(' + options.filter + ')';
                    if ( key == 'row' ){
                        table.on(event, function (obj) {
                            callback(obj);
                        });
                    }else{
                        table.on(event, callback);
                    }
                });
            }
        }
    };

    exports("customer_grid", cusGrid);
});