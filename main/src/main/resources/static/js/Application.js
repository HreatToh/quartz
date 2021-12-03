/**
 * @author  Chengshx
 * @create  2021/11/23 14:46
 * @desc    Application.js 全局配置Js
 *
 **/

$(function () {

    jQuery.Application = {
        // token
        Authorization : '',
        // licence 许可证
        licence : null,
        // 成功的对话框
        success : function(layer , msg , callback){
            var index = 0;
            if (callback && callback instanceof Function){
                index = layer.msg(msg, {icon: 1} , callback);
            }else{
                index = layer.msg(msg, {icon: 1});
            }
            return index;
        },
        //失败对话框
        fail : function(layer , msg , callback){
            var index = 0;
            if (callback && callback instanceof Function){
                index = layer.msg(msg, {icon: 2} , callback);
            }else{
                index = layer.msg(msg, {icon: 2});
            }
            return index;
        },

        baseTbColOpt: {
            // 列的唯一索引。字符串类型，必设项
            key: '',

            // 列的显示文本。[字符串 或 函数]类型，必设项。
            // 如果当前使用了angular、vue、react版本，这里可以直接使用框架模版
            text: '',

            // 是否显示, 默认值 true
            isShow: true,

            // 该列是否禁止使用个性配置功能(宽度调整、位置更换、列的显示隐藏)
            // 注意: 值为true时，width为必设项
            disableCustomize: false,

            // 指定当前列禁止触发行移动事件，默认为:false
            disableMoveRow: false,

            // 指定当前列禁止触发行选中事件，默认为:false
            disableRowCheck: false,

            // 是否将相同数据列合并，在配置template的情况下会以执行结果进行比对
            // 两种值: 'text'指定比对td.innerText, 'html'指定比对td.innerHTML
            // merge: 'text',

            // 列所占宽度, 字符串类型，非必设项
            // 需要注意的是:
            // 1.如果th实际占用宽度大于该指定宽度时，会导致显示异常。
            // 2.请不要设置所有的列，最少需要保留一列交由gridmanager进行自适应(注意该列的isShow必须为true)
            // 3.只支持px单位的值，可简写为`width: 100`
            // width: 100,

            // 列文本对齐信息，字符串类型，非必设项。默认值为空字符，效果与left相同。
            // 三种值: 'left', 'center', 'right'
            align: 'center',
            // 固定列, 使用后 必须存在width 且 disableCustomize 将强制变更为true
            // 当前列存在fixed时，指定方向一侧的其它列也应该配置相同的fixed
            // 两种值: 'left', 'right'
            fixed: undefined,
            // 列的排序信息。字符串类型，非必设项
            // 1、'': 该列支持排序，但初始化时不指定排序类型
            // 2、'DESC': 该列支持排序，并指定为降序。可通过参数[sortDownText]来指定降序所使用的字符串
            // 3、'ASC': 该列支持排序，并指定为升序。可通过参数[sortUpText]来指定升序所使用的字符串
            sorting: '',
            // 列的表头提醒内容。[string | object]非必设项
            // remind: '文本介绍', // string形式
            // remind: {  // object形式
            //     text: '',
            //     style: {
            //         'width': '100px',
            //         'font-size': '14px'
            //     }
            // },

            // 表头筛选条件, 该值由用户操作后会将选中的值以{key: value}的形式覆盖至query参数内。非必设项
            // filter: null ,

            // 自定义列模板，非必设项.
            // 字段类型:
            // 1.函数类型,需返回DOM string 或 DOM node.
            // 2.字符串类型,DOM string.
            // 触发刷新后，GridManager会使用template中返回的字符串，进行重新渲染。
            // 如果没有配置template，将会使用当前配置项属性key所对应的响应数据。

            // 函数参数:
            // cell: 当前单元格的渲染数据
            // row: 当前单元格所在行的渲染数据, 本例中: 参数nodeData=== rowData.url
            // index: 索引
            // key: 列唯一键值

            // 事件绑定:
            // 1、通过javascript原生事件绑定,如onclick
            // 2、通过element.addEventListener方法绑定

            // 关于前端框架:
            // 1、gridmanager-vue 中 template 请使用vue模板方式，具体请参考 gridmanager-vue
            // 2、gridmanager-angular-1.x 中 template 请使用angular模板方式，具体请参考 gridmanager-angular-1.x
            // 3、gridmanager-react 中 template请使用react组件方式，具体请参考 gridmanager-react
            template: function(cell, row, index, key){
                return '<span title="' + cell + '">' + cell + '</span>>';
            },

            // 子项配置, 通过该项可以实现嵌套表头
            // 存在该项时，将禁用: supportConfig、supportDrag、supportAdjust、columnData.fixed
            // 数组对像与columnData对象配置相同，可多层嵌套。
            // 注意: 现有版本中最后一层的宽度会平分上一层的宽度
            // children: []

        },
        baseTbOpt:{
            gridManagerName: null
            // 配置是否关闭用户记忆，默认关闭用户记忆。
            // 未禁用记忆时，会将用户所调整的宽度、列位置、列的显示隐藏状态及每页显示条数进行存储，刷新后会使用存储数据进行显示。
            // 在未禁用时，存在以下几种情况会触发记忆清除:
            // 1、GridManager 版本升级
            // 2、配置项columnData变更, 包含元素顺序和元素属性[width, isShow]的变更
            // 3、手动调用clear()方法
			, disableCache:false
            // ajaxData的类型比较灵活, 同时支持:
            // 1、url string
            // 2、response data
            // 3、function
            , ajaxData: null
            // ajax请求类型，分为'GET' 和 'POST'两种类型。
            // 配置项ajaxData的值(或函数的返回值)为string url时， ajaxType才会生效。
            // 注意事项: 使用三方库(如axios)时，该参数无效。
            , ajaxType: 'POST'
            // 汇总行处理程序, 通过该函数可以动态生成汇总行。
            // summaryHandler函数传递参数为当前页所使用的数据，函数需返回与columnData中key相匹配的Object。
            // 参数data为当前页所使用的数据
            // summaryHandler: function(data){
            //     let readNumberAll = 0;
            //     data.forEach(item => {
            //         readNumberAll += item.readNumber;
            //     });
            //
            //     // 返回与columnData中key相匹配的Object
            //     // 注意: columnData中存在key===readNumber的列配置项时，以下汇总数据才会生效
            //     return {
            //         readNumber: readNumberAll
            //     };
            // },
            , summaryHandler : null
            // 单行数据渲染时执行程序。
            // 通过修改row选项来对行数据进行更改, 并且可以通过修改以下GM定义属性:
            // 1. gm_checkbox: 当前行是否选中
            // 2. gm_checkbox_disabled: 当前行是否禁用选择功能
            // 3. gm_order: 当前行的序号
            // 4. gm_row_class_name: 为当前行增加className
            // rowRenderHandler: (row, index) => {
            //     // 选中id为92的行
            //     row.gm_checkbox = row.id === 92;
            //     // 第2行禁用选中
            //     row.gm_checkbox_disabled = index === 1;
            //     // 为当前行增加一个calssName
            //     row.gm_row_class_name="class-name"
            //     // 第3行的title替换为`这里有什么`
            //     if (index === 2) {
            //         row.title = '这里有什么';
            //     }
            //     return row;
            // },
            , rowRenderHandler : null
            // 响应后处理程序, 通过该函数可以修改远端返回的数据。
            // 仅在请求成功后该函数才会执行。
            // responseHandler 函数传递参数为 response, 该参数为从远端请求回来的数据. 函数返回值将做为表格渲染时所使用的数据
            , responseHandler : function ( response ) {
                response.total = response.data.total;
                response.data = response.data.records;
                return response;
            }
            // 请求前处理程序, 通过该函数可以修改全部的请求参。
            // requestHandler 函数传递参数为 request, 该参数为即将发送的请求参数，函数返回值将作为请求时的参数。
            // 需要注意的是:
            //     分页及排序所使用的字段被修改后同样会生效。
            , requestHandler : function (request) {
                request.token = '11111';
                return request;
            }
            // 配置ajax请求时的头信息，与XMLHttpRequest对象使用方式相同。
            // 比如配置请求体为json格式, 示例: ajaxHeaders: {'Content-Type': 'application/json'}
            , ajaxHeaders : {}
            // 配置接口请求参数，可用于搜索条件传递;
            // 配置方式: {'name': '拭目以待', 'sex': '男'} // 传递请求参数
            // 注意事项:
            // 1. 当query的key与分页及排序等字段冲突时将会被忽略。
            // 2. 如果是对已实例化表格增加请求参数，请使用.setQuery()方法。
            , query: {}
            // 配置初次进入时每页的显示条数，需要与sizeData中的值匹配。
            // 注意事项:
            //     在启用本地缓存的情况下，每页的显示数为上次用户调整后的数值。
            , pageSize: 20
            // 配置每页显示条数的下拉项，数组元素仅允许为正整数。
            , sizeData: [20 , 30 , 50 , 100 , 500 , 1000 , 5000 ]
            // 分页区域自定义模板
            // ,ajaxPageTemplate:
            , columnData: []
            // 指定返回数据总条数的key键值。
            // 在接口返回数据格式不匹配时，可以通过该配置项进行修改。
            , totalsKey: 'total'
            // 请求参数中当前页key键值,默认为page_num
            , currentPageKey: 'page_num'
            // 请求参数中每页显示条数key健值, 默认为page_size_num
            , pageSizeKey: 'page_size_num'
            // 配置是否合并排序字段, 对应传送参数格式如下:
            // false => {sort_createDate: 'DESC', sort_title: 'ASC'}
            // true => sort: 'createDate: "DESC"'
            , mergeSort: false
            // ajax请求中排序字段所使用的前缀，如：
            // 当指定sortKey =‘sort_’时，createDate列对应的传参排序字段为'sort_ createDate'.
            , sortKey: 'sort_'
            // 更改排序操作所使用的升序标识。
            // 配置成功后，在发送数据请求中包含升序操作时，排序字段的值将为sortUpText配置的值。
            // 如果配置了该参数，那么在初始化时，参数columnData中的sorting所使用的值也一并要改变。
            // 配置示例:
            // sortUpText: 'up',
            // isCombSorting: true,
            // columnData: [{
            //     key: 'createDate',
            //     name: '创建时间',
            //     sorting: 'DESC'
            // },{
            //     key: 'age',
            //     name: '生日',
            //     sorting: 'up' // sortUpText配置为'up', 所以这里也需要使用'up'
            // }]
            , sortUpText: 'ASC'

            // 配置排序操作所使用的降序标识。
            // 配置成功后，在发送数据请求中包含降序操作时，排序字段的值将为sortDownText配置的值。
            // 配置方式与sortUpText相同，请参考sortUpText配置方式。
            , sortDownText: 'DESC'
            // 配置td内的文本是否进行断字处理，未配置或配置为false时不进行断字处理且超出的文本会被隐藏。当useWordBreak配置为true时除了文本会被断字处理外，overflow也将被调整为visible。
            , isIconFollowText: false
            // 配置是否支持选择与反选。
            , supportCheckbox : true
            // 选择框配置
            , checkboxConfig: {
                // 选择列宽度配置
                width: 40

                // 是否通过点击行来进行选中
                , useRowCheck: false

                // 当前选中操作是否使用单选
                , useRadio: false

                // 触发刷新类操作时(搜索、刷新、分页、排序、过滤)，是否禁用状态保持
                , disableStateKeep: undefined

                // 指定选中操作精准匹配字段，该值需保证每条数据的唯一性。默认不指定，对整条数据进行匹配。
                // 配置此项可提升选中操作性能, 数据量越大越明显。
                , key: undefined

                // 复选时最大可选数，生效条件: supportCheckbox === true && useRadio === false
                , max: undefined

                // 是否使用固定列, 默认为undefined
                // 接收两种值: 'left', 'right'
                , fixed: undefined
            }
            // 是否支持数据导出
            , supportExport : true
            , exportConfig : {
                // 导出的方式: 默认为static
                // 1.static: 前端静态导出, 无需后端提供接口，该方式导出的文件并不完美。
                // 2.blob: 通过后端接口返回二进制流。`nodejs`可使用`js-xlsx`, `java`可使用 `org.apache.poi`生成二进制流。
                // 3.url: 通过配置或由后端返回下载地址
                mode: 'static'

                // 导出文件的名称, 字符串或函数类型，为函数时需返回一个字符串。该字符串不包含后缀名，该值不设置将默认使用gridManagerName
                // 参数query为当前查询的参数
                , fileName: function(query) {
                    return new Date().getTime();
                }

                // 导出的后缀名, 可选项['xls', 'csv'], 默认为`xls`
                , suffix: 'xls',

                // 导出处理器函数, mode === 'static'时可以选择性配置该项，其它mode必须配置
                // fileName： 导出文件名
                // query: 查询参数
                // pageData: 分页数据
                // sortData: 排序数据
                // selectedList: 当前选中的数据
                // tableData: 当前页数据
                handler: (fileName, query, pageData, sortData, selectedList, tableData) => {
                    // mode === 'static': handler函数return 二维数组，若未正确返回数组则使用当前DOM进行导出
                    // return [["title", "content", "createData"],["typescript", "this is typescript", "2015-01-01"]]

                    // mode === 'blob': handler函数需要返回resolve(blob)的promise
                    // 需要通过promise中的resolve()返回二进制流(blob)，有两种返回格式:
                    // 1. return new Promise(resolve => {resolve(blob)});
                    // 2. return new Promise(resolve => {resolve({data: blob})});

                    // mode === 'url': handler函数需要返回url或返回resolve(url)的promise
                    // 1. return 'xxx.xxx.com/xxx.xls';
                    // 2. return new Promise(resolve => {resolve('xxx.xxx.com/xxx.xls')})
                }
            }
            // 用于配置是否支持树型表格，配置树型数据后行移动功能将无效。
            , supportTreeData : false
            , treeConfig : {
                // 指定树展开操作按键所属容器
                // insertTo: 'title', // 此处配置columnData的key值，未配置时默认选择columnData的第一项
                // 层级关键字段
                treeKey: 'children',
                // 初始打开状态
                openState: false
            }
            // 是否支持打印功能
            , supportPrint : true
            // 配置是否开启行移动功能。
            , supportMoveRow : false
            // 行移动功能配置
            , moveRowConfig: {
                // 指定移动后需要更新的字段, 该字段未配置时将只对DOM进行更新
                // key: 'priority',

                // 单列移动模式: 为true时将生成单独的一列
                useSingleMode: false,

                // 列固定: 仅在单列移动模式下生效, 如果右侧存在固定列则该列必须配置为left
                fixed: undefined,

                // 移动后执行的程序，可在该程序中完成与后端的交互
                handler: (list, tableData) => {
                    console.log(list, tableData);
                }
            }
            // 是否支持自动序号
            , supportAutoOrder : true
            // 自动序号配置
            , autoOrderConfig: {
                // 序号列宽度配置
                width: 50,
                // 固定列, 默认为undefined
                // 接收两种值: 'left', 'right'
                fixed: undefined
            }
            // 指定列表是否支持分页。
            // 注意事项:
            //     分页实现需要后端程序通过 GridManager 传递的参数[ cPage(当前页) 和 pSize(每页显示条数) ] 返回对应的数据。
            , supportAjaxPage: true
            // 是否支持拖拽
            , supportDrag: false
            // 用于配置是否支持宽度调整功能。
            , supportAdjust: false
            // 配置是否支持右键菜单功能，禁用后右键功能将失效。
            , supportMenu: false
            // 右键菜单处理程序，通过在menuHandler内将修改后的list返回可自定义菜单项。
            , menuHandler: null
            // ,menuHandler: function ( list ) {
            //     list.unshift({
            //         content: '自定义菜单',
            //         line: true,
            //         onClick: _ => {
            //             alert(_);
            //         }
            //     });
            //     return list;
            // }
            // 是否禁用自动loading，true值适用于通过showLoading()和hideLoading()方法自主控制loading状态时。
            , disableAutoLoading: true
            // 配置是否禁用边框线
            , disableBorder : false
            // 配置是否禁用单元格分割线
            , disableLine : false
            // 配置表格区域的高度, 需要带单位，超过这个高度会出现滚动条。支持以下写法:
            // 1. '100px'
            // 2. '50%'
            // 3. '100% - 40px'
            // 4.'100vh - 64px - 57px'
            , height: '100% - 40px'
            // 与height使用方式相同。
            , maxHeight : '100%'
            // 与height使用方式相同。
            , minHeight : '10%'
            // 分页前事件
            , pagingBefore: function(query){
                console.log('pagingBefore', query);
            }
            // 分页后事件
            , pagingAfter: function(data){
                console.log('pagingAfter', data);
            }
            // 排序前事件
            , sortingBefore: function (data) {
                console.log('sortBefore', data);
            }
            // 排序后事件
            , sortingAfter: function (data) {
                console.log('sortAfter', data);
            }
            // 宽度调整前事件
            , adjustBefore: function (event) {
                console.log('adjustBefore', event);
            }
            // 宽度调整后事件
            , adjustAfter: function (event) {
                console.log('adjustAfter', event);
            }
            // 拖拽前事件
            , dragBefore: function (event) {
                console.log('dragBefore', event);
            }
            // 拖拽后事件
            , dragAfter: function (event) {
                console.log('dragAfter', event);
            }
        },
        getTable : function ( o ) {
            // o { url , params , columns , toolbar , options}
            var me = this, i = 0 , col = {} , grid , columnData = [] , options = {};
            for ( ; o && o.columns && i < o.columns.length ; i ++) {
                col = {
                    key : o.columns[i].name
                    , text : o.columns[i].label
                }
                if ( o.columns[i].remind || o.columns[i].desc ){
                    col.remind = { text : o.columns[i].desc || o.columns[i].label };
                }
                columnData.push(jQuery.extend({} , me.baseTbColOpt , o.columns[i] , col ));
            }
            o.options.gridManagerName = o.options.gridManagerName || jQuery(o.element).attr('id');
            options = jQuery.extend({} , me.baseTbOpt , o.options, {ajaxData : o.url , columnData : columnData  , query : o.params});
            jQuery(o.element).wrap('<div class="layuimini-container" style="padding: 0 10px;"><div class="layuimini-main"></div></div>');
            jQuery(o.element).GM(options);
            grid = GridManager.get(o.options.gridManagerName)
            // 渲染ToolBar
            if ( o.toolbar && o.toolbar.length > 0 ){
                var i , id , name , label , icon , handler , width , toolBar = o.toolbar , template ;
                template = [ '<fieldset class="layui-elem-field" style="padding: 5px;">' , '<legend style="font-size: x-small">工具栏</legend>' , '<div class="layui-btn-group">'];
                for (i = 0 ; i < toolBar.length ; i ++ ){
                    var temp = '<button type="button" class="layui-btn layui-btn-primary layui-btn-sm " gmId="' + i + '" '
                    id = toolBar[i]['id'] , name = toolBar[i]['name'] , label = toolBar[i]['label'] , icon = toolBar[i]['icon'] , width = toolBar[i]['width'] ;
                    if(id){ temp += ' id="' + id + '" '; };
                    if(name){ temp += ' name="' + name + '" '; };
                    temp += ' > '
                    if(icon){ temp += ' <i class="fa ' + icon + '"></i> '; };
                    if(label){ temp += label + '</button>'; };
                    template.push(temp);
                }
                template.push('</div>' , '</fieldset>')
                jQuery(o.element).parent('div').parent('div.table-wrap').before( template.join('') );

                for (i = 0 ; i < toolBar.length ; i ++ ){
                    handler = toolBar[i]['handler'];
                    if (handler && handler instanceof Function ){
                        jQuery('button[gmId="' + i + '"]').bind( 'click' ,{grid : grid , gridId : o.options.gridManagerName } , handler );
                    }
                }
            }
        },
        formConfig : {
            date : function (val , options) {
                var format = options.format || 'YYYY-MM-DD';
                return new Date(val).format(format);
            },
            input : function (val , options) {

            },
            select : function (val , options) {

            },
            time : function (val , options) {

            }
        }

    }
    /**
     * 对象值格式化
     * @param fmt
     * @returns {*}
     */
    jQuery.fn.format = function(type , options ){
        var ele = this , val = $(ele).val();
        return jQuery.Application.formConfig[type].call(val , options);
    }
})