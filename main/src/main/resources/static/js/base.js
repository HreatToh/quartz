/**
 * @author  Chengshx
 * @create  2021/11/23 17:53
 * @desc    基础
 **/


// 日期格式化,不是插件的代码,只用于处理时间格式化
Date.prototype.format = function(fmt){
    var o = {
        "M+": this.getMonth() + 1, //月份
        "D+": this.getDate(), //日
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/([Y,y]+)/.test(fmt)){
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o){
        if(new RegExp("(" + k + ")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}
/**
 * @method  isNotNull
 * @params  o
 * @return  boolean
 * @desc    判断变量是否为空 如果 true 则不为空 false 则为空
 **/
function isNotNull( o ) {
    return o != null && o != undefined && o != '';
}
/**
 * @method  isNull
 * @params  o
 * @return  boolean
 * @desc    判断变量是否为空 如果 true 则为空 false 则不为空
 **/
function isNull( o ) {
    return !isNotNull(o);
}