//生产编辑器
var editor = UE.getEditor("content",{toolbars:[[
        'toggletool','fullscreen', 'source', '|', 'undo', 'redo', '|',
        'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist','|',
        'simpleupload', 'insertimage','|', 'selectall', 'cleardoc',"emotion"
    ]],"initialFrameWidth":450,"initialFrameHeight":150});

//格式化时间
function dateFtt(fmt,date) {   //author: meizz
    var o = {
        "M+" : date.getMonth()+1,                 //月份
        "d+" : date.getDate(),                    //日
        "h+" : date.getHours(),                   //小时
        "m+" : date.getMinutes(),                 //分
        "s+" : date.getSeconds(),                 //秒
        "q+" : Math.floor((date.getMonth()+3)/3), //季度
        "S"  : date.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

//获取留言
function getMsgFn(base64){
    var formData = new FormData();
    var status = base64.encode("show");
    formData.append("status", status);
    $.ajax({
        type:"POST",
        url:"board",
        contentType:false,
        processData: false,
        data:formData,
        success:function (data) {
            $(data).each(function (i) {
                var msg = $(" <li>\n" +
                    "                       <div class=\"msg\">\n" +
                    "                           <p>\n" +
                    "                                <div class=\"title\"><label>标题:</label><span >" + data[i].title + " </span></div>\n" +
                    "                                <div class=\"author\"><label>作者:</label><span >" + data[i].author + "</span></div>\n" +
                    "                           </p>\n" +
                    "                           <div class=\"content\"><label class='key'>留言:</label>" + data[i].content + "</div>\n" +
                    "                           <p class='msg-bottom'>\n" +
                    "                               <span><i class=\"fa fa-thumbs-o-up\"></i>点赞" + data[i].thumbs_up + "</span>\n" +
                    "                               <span><i class=\"fa fa-thumbs-o-down\"></i>点踩" + data[i].thumbs_down + " </span>\n" +
                    "                               <label>时间:</label><span class=\"time\">" + data[i].time + " </span>\n" +
                    "                           </p>\n" +
                    "                       </div>\n" +
                    "                   </li>");

                var $ul = $(".text ul");
                $ul.append(msg);
            });
        }
    });
}

$(function () {



    var base64 = new Base64();
    getMsgFn(base64);//渲染留言信息

    //我要发言
    $(".send").click(function () {
        if(parseInt($(".smart-green").css("top")) <= -600){
            $(".smart-green").animate({"top":"100px"},600);
        }else{
            $(".smart-green").animate({"top":"-600px"},600);
        }
    });


    $("#release").click(function () {
        //实例化form对象(能够支持文件上传)
        var formData = new FormData();
        var status = base64.encode("insert");
        var title = $("#title").val();
        var author = $("#author").val();
        var content = editor.getContent();
        formData.append("status", status);
        formData.append("title", title);
        formData.append("author", author);
        formData.append("content", content);

        //开始提交留言
        $.ajax({
            type:"POST",
            url:"board",
            processData : false,
            contentType : false,
            data:formData,
            success:function (data) {
                if(data.code === 200){
                    alert("留言成功!");
                    $(".text ul").empty();
                    getMsgFn(base64);
                }else{
                    alert("留言失败!");
                }
                $(".smart-green").animate({"top":"-600px"},600);
                $("#title").val("");
                $("#author").val("");
                editor.setContent("");
            },
            error:function (data) {
                alert(data.status + "|" + data.responseText);
            }
        })
    })
});
