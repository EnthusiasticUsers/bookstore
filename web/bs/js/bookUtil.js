function tips(msg, res){
    if(res){
        $content = $(" <div class=\"alert alert-success alert-dismissible\">\n" +
            "    <strong>成功:</strong>" + msg + "\n" +
            "  </div>");
    }else{
        $content = $("<div class=\"alert alert-danger alert-dismissible\">\n" +
            "    <strong>错误!</strong>" + msg + "\n" +
            "  </div>");
    }
    $("#alert").append($content);
    $(".alert").fadeIn(2000,function () {
        $(this).timer = setInterval(() => {
            $(this).fadeOut(2000,function () {
                clearInterval($(this).timer);
                $(this).remove();
            });
        },1500);
    });
}

function preview(file) {
    var url = null ;
    if (window.createObjectURL!=undefined) { // basic
        url = window.createObjectURL(file.files[0]) ;
    } else if (window.URL!=undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file.files[0]) ;
    } else if (window.webkitURL!=undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file.files[0]) ;
    }
    return url;
}

function validate_img(ele){
    // 返回 KB，保留小数点后两位
    //alert((ele.files[0].size/(1024*1024)).toFixed(2));
    var file = ele.value;
    if(!/.(gif|jpg|png|GIF|JPG|bmp|docx|xls|txt)$/.test(file)){

        tips("图片类型必须是gif|jpg|png|GIF|JPG|bmp|doc|xls|txt中的一种", false);
        return false;

    }else{
        //alert((ele.files[0].size).toFixed(2));
        //返回Byte(B),保留小数点后两位
        if(((ele.files[0].size).toFixed(2))>=(1024*1024)){

            tips("演示文件不能过大", false);
            return false;
        }
    }
    tips("恭喜你图片通过!", true);
    return true;
}

