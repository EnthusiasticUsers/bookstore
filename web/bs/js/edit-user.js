var base64 = new Base64();
var status = "";
var id = getUrlParam("id");
//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}


$(function () {
    status = base64.encode("id");
    $.post("user",{
        status:status,
        id:id
    },function (data) {
       $("#username").val(data.username);
       $("#password").val(data.password);
    });

    $("#update").click(function () {
        status = base64.encode("update");
        var username = $("#username").val();
        var password = $("#password").val();
        $.post("user",{
            status:status,
            id:id,
            username:username,
            password:password
        },function (data) {
            if(data.code === 200){
                tips(data.msg, true);
            }else{
                tips(data.msg, false);
            }

        })
    });
});