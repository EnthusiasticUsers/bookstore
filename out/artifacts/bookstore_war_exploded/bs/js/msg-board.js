//生产编辑器
var editor = UE.getEditor("content",{toolbars:[[
        'toggletool','fullscreen', 'source', '|', 'undo', 'redo', '|',
        'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist','|',
        'simpleupload', 'insertimage','|', 'selectall', 'cleardoc',"emotion"
    ]],"initialFrameWidth":450,"initialFrameHeight":150});
var scroll_height = 0;//滚动高度
var currentPage = 1;//当前页
var size = 3;//每页显示几条数据
var totalPage = 0;//总共几页
var numberSize = 10;//页码每次做多显示几个
var data = null; //留言数据

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

//渲染留言
function renderMsg() {
    var low = (currentPage-1)*size;
    var high = currentPage*size;
    //清除前一次记录
    $(".msgCon ul").empty();
    for(var i = low; i < high; i++){
        if(i <= data.length-1){
            var msg = $(" <div class='msgBox'>\n" +
                "                       <div class=\"headUrl\">\n" +
                "                           <img src='" + data[i].portrait + "' width='50' height='50'>\n" +
                "                           <div>\n" +
                "                               <span class=\"title\">" + data[i].title + "</span>\n" +
                "                               <span class=\"time\">" + data[i].time + "</span>\n" +
                "                           </div>\n" +
                "                       </div>\n" +
                "                       <div class='msgTxt'>\n" +
                "                           "+ data[i].content + "\n" +
                "                       </div>\n" +
                "                       <div class=\"msgFooter\">\n" +
                "                           <span class='thumbs_up'><i class=\"fa fa-thumbs-o-up\"></i>点赞" + data[i].thumbs_up + "</span>\n" +
                "                           <span class='thumbs_down'><i class=\"fa fa-thumbs-o-down\"></i>点踩" + data[i].thumbs_down + "</span>\n" +
                "                           <span class='reply'><a href=\"reply.html?uid=" + data[i].id + "\">回复<i class='fa fa-reply'></i></a></span>\n" +
                "                       </div>\n" +
                "                   </div>");
            var $board = $(".msgCon ul");
            $board.append(msg);
        }
    }
}

//渲染页码
function renderPage() {
    //numberPage = totalPage <= numberSize ? numberSize : (totalPage + numberSize) / numberSize;
    var low = numberSize * Math.floor((currentPage-1) / numberSize) + 1;
    var high = numberSize * Math.floor((currentPage-1) / numberSize + 1);

    if(totalPage <= numberSize || high >= totalPage){
        high = totalPage;
    }


    //清除上一次渲染的页码
    $(".page-number").remove();
    for(var page = low; page <= high; page++){
        $page = $("   <li class=\"page-number\" onclick='pageFn(this)'>\n" +
                "     <span href=\"#\">"  + page + "</span>\n" +
                "     </li>");

        $("#next").before($page);

        $(".page-number").hover(function(){
            $(this).addClass("page-hover");
        },function () {
            $(this).removeClass("page-hover");
        });
    }

    var pageIndex = currentPage % numberSize - 1;
    $(".page-number").eq(pageIndex).addClass("active");

}

function pageFn(obj) {
    currentPage = parseInt($(obj).children("span").html());
    $(obj).addClass("active").siblings().removeClass("active");
    renderMsg(currentPage, size, totalPage, data);
}

function pageClick() {
    //上一页
    $("#prev").click(function () {
        currentPage = currentPage > numberSize ? Math.floor((currentPage-numberSize-1) / numberSize) * numberSize + 1 : currentPage;
        renderMsg();
        renderPage();
    });

    //下一页
    $("#next").click(function () {
       currentPage = Math.floor((currentPage+numberSize-1) / numberSize) * numberSize + 1;
       currentPage = currentPage >= totalPage ? totalPage : currentPage;
        renderMsg();
        renderPage();
    });

    //首页
    $("#begin").click(function () {
        currentPage = 1;
        renderMsg();
        renderPage();
    });

    //尾页
    $("#end").click(function () {
        currentPage = totalPage;
        renderMsg();
        renderPage();
    });


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
        success:function (list) {
             data = list;
             currentPage = 1;
             size = 3;
             totalPage = data.length <= size ? 1 : Math.floor((data.length + size) / size);
             numberSize = 10;
            //渲染数据
            renderMsg();
            //渲染页码
            renderPage();
            //分页点击
            pageClick()
        }
    });
}

$(function () {
    var base64 = new Base64();
    getMsgFn(base64);//渲染留言信息

    //查看自己
    $(".send").click(function () {

    });




    $("#release").click(function () {
        //实例化form对象(能够支持文件上传)
        var formData = new FormData();
        var status = base64.encode("insert");
        var title = $("#title").val();
        var author = Number($("#author").attr("uid"));
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
                    $(".msgCon ul").empty();
                    getMsgFn(base64);
                }else{
                    alert("留言失败!");
                }
                $("#title").val("");
                editor.setContent("");
            },
            error:function (data) {
                alert(data.status + "|" + data.responseText);
            }
        })
    })
});
