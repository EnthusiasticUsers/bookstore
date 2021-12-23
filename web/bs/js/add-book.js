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

    //实例化base64
    var base = new Base64();

    $.post("book/type/show",function (data) {
        $menu = $("#menu");
        $(data).each(function () {
            var option = $("<option value='" + this.id + "'>" + this.type + "</option>");
            $menu.append(option);
        })
    });

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
                    tips("请输入数字!", false);
                    break;
                }
            }
    });

    //提交增加后的图书信息
    $(".btn-1").click(function () {
        if($("#name").val().length > 20 || $("#name").val().length < 1){
            tips("图书名长度不够,应该在1~20之间", false);
        }else if(parseFloat($("#price").val()) < 5 ||  parseFloat($("#price").val()) > 500){
            tips("图书价格,应该在之间5~500之间", false);
        }else{
            var status = base.encode("add");
            var name = $("#name").val();
            var price = $("#price").val();
            var imageFile = $("#imageFile").get(0).files[0];
            var type = $("#menu option:selected").val();
            var formData = new FormData();
            formData.append("status", status);
            formData.append("name", name);
            formData.append("price", price);
            formData.append("imageFile", imageFile);
            formData.append("type", type);

            $.ajax({
                type:"POST",
                url:"book",
                data:formData,
                contentType : false,
                processData : false,
                success:function (data) {
                    if(data.code === 200){
                        tips(data.msg , true);
                    }else{
                        tips(data.msg , false);
                    }
                    $("#name").val("");
                    $("#price").val("");
                    $("#imageFile").val(null);
                    $("#previewImage").empty();
                    $("#menu option:eq(0)").attr("selected", true);
                },
                error:function (data) {
                    console.log(data);
                    alert(data.status + "|" + data.responseText);
                }
            })
        }
    });
});