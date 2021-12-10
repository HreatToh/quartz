/**
 * date: 2021/12/08
 * author: chengshx
 * description:此处放layui自定义扩展
 * version:1.0.01
 */

window.rootPath = document.getElementById("ctxPath").getAttribute("content");

layui.config({
    base: rootPath + "/static/js/lay-module/",
    version: true
}).extend({
    /** 自定义后台扩展    */
    customer: "customer/customer",
    /** 自定义菜单扩展    */
    customer_menu: "customer/customer_menu",
    /** 自定义 tab扩展    */
    customer_tab: "customer/customer_tab",
    /** 自定义 主题扩展    */
    customer_theme: "customer/customer_theme",
    /** 自定义 tree组件    */
    customer_tree: "customer/customer_tree",
    /** 自定义 table组件    */
    customer_grid: "customer/customer_grid",
    /** layuimini 统计扩展    */
    miniTongji: "layuimini/miniTongji",
    /** 分步表单扩展    */
    step: 'step-lay/step',
    /** table树形扩展    */
    treetable: 'treetable-lay/treetable',
    /** table选择扩展    */
    tableSelect: 'tableSelect/tableSelect',
    /** fa图标选择扩展    */
    iconPickerFa: 'iconPicker/iconPickerFa',
    /** echarts图表扩展    */
    echarts: 'echarts/echarts',
    /** echarts图表主题扩展    */
    echartsTheme: 'echarts/echartsTheme',
    /** wangEditor富文本扩展    */
    wangEditor: 'wangEditor/wangEditor',
    /** 省市县区三级联动下拉选择器    */
    layarea: 'layarea/layarea',
});
