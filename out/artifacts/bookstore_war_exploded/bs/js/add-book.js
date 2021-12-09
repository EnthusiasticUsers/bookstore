$(function () {
    /*$("#imageFile").change(function() {
       var fileReader = new FileReader();
       fileReader.onload = function(e) {
           $("#previewImage").append("<span class='center-block text-success'>图像预览</span><image class='img-thumbnail' style='max-width:400px;height:auto;' src="+e.target.result+"/>");
       }
       var imageFile = this.files[0];
       fileReader.readAsDataURL(imageFile);
   });*/

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
                if (code < 48 || code > 57) {
                    $(this)[0].focus();
                    $(this).val("");
                    alert("请输入数字!");
                    break;
                }
            }
    });

    //提交增加后的图书信息
    $(".btn-1").click(function () {
        if($("#name").val().length > 20 || $("#name").val().length < 1){
            alert("图书名长度不够,应该在1~20之间");
        }else if(parseFloat($("#price").val()) < 5 ||  parseFloat($("#price").val()) > 500){
            alert("图书价格,应该在之间5~500之间");
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
                    $("#name").val("");
                    $("#price").val("");
                    $("#imageFile").val(null);
                    $("#menu option:eq(0)").attr("selected", true);
                    alert(data.msg);
                },
                error:function (data) {
                    console.log(data);
                    alert(data.status + "|" + data.responseText);
                }
            })
        }
    });
});