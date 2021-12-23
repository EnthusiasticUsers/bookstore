$(function () {
    $("#imageFile").change(function() {
        var ans = validate_img($(this)[0]);
        if(ans){
            var img_src =preview($(this)[0]);
            var $img = $("<img src=\"" + img_src + "\" alt=\"\" width=\"240\" height=\"160\">");
            $("#previewImage").empty().append($img);
        }else{
            $(this)[0].val("");
        }
    });

    //实例化base64加密
    var base = new Base64();

    var status = base.encode("session");


    //检测价格只能数字
    $("#price").blur(function () {
        var $val = $(this).val();
        var code;	//charAt()获取指定位置字符串,charCodeAt()返回该字符串的编码
        // 0的ASCII是48,9的ASCII是57
        for (var i = 0; i < $val.length; i++) {
            code = $val.charAt(i).charCodeAt(0);
            if (!(code === 46 || code >= 48 && code <= 57)) {
                $(this)[0].focus();
                $(this).val("");
                alert("请输入数字!");
                break;
            }
        }
    });

    //获取图书类别
    $.post("book/type/show",function (data) {
        $menu = $("#menu");
        $(data).each(function () {
            var option = $("<option value='" + this.id + "'>" + this.type + "</option>");
            $menu.append(option);
        });

        //获取图书信息ID
        var formData = new FormData();
        var id = getUrlParam("id");
        var status = base.encode("id");
        formData.append("status", status);
        formData.append("id", id);
        //获取图书信息
        $.ajax({
            type:"POST",
            url:"book",
            processData: false,
            contentType: false,
            data:formData,
            success:function (data) {
                $(data).each(function () {
                    $("#id").val(this.id);
                    $("#name").val(this.name);
                    $("#price").val(this.price);
                    $("#image").val(this.image);
                    $("#menu option[value='" + this.type + "']").attr("selected", true);
                })
            },
            error:function (res) {
                alert(res.responseText);
            }
        });

        //提交修改后的图书信息
        $(".btn-1").click(function () {
            if ($("#name").val().length > 20 || $("#name").val().length < 1) {
                alert("图书名长度不够,应该在1~20之间");
            } else if (parseFloat($("#price").val()) < 5 || parseFloat($("#price").val()) > 500) {
                alert("图书价格,应该在之间5~500之间");
            } else {
                var formData = new FormData();
                var status = base.encode("update");
                var id = getUrlParam("id");
                var name = $("#name").val();
                var price = $("#price").val();
                var imageFile = $("#imageFile").get(0).files[0];
                var type = $("#menu option:selected").val();
                formData.append("status", status);
                formData.append("id", id);
                formData.append("name", name);
                formData.append("price", price);
                formData.append("imageFile", imageFile);
                formData.append("type", type);

                $.ajax({
                    type: "POST",
                    url: "book",
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function (data) {
                        if(data.code === 200){
                            tips(data.msg, true);
                        }else{
                            tips(data.msg, false);
                        }
                        $("#name").val("");
                        $("#price").val("");
                        $("#imageFile").val(null);
                        $("#menu option:eq(0)").attr("selected", true);
                        $("#previewImage").empty();
                    },
                    error: function (data) {
                        alert(data.status);
                    }
                })
            }
        })
    });

    //获取url中的参数
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }
});