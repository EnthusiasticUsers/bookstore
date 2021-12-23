$(function () {
    var base = new Base64();
    var status = base.encode("session");
    //查找session中的[username,password],存在则放入input中
    $.post("user",{status:status},function (data) {
        if(data != null){
            $("#username").val(data.username);
            $("#password").val(data.password);
            $(".code").addClass("hidden");
        }
    });


    //图片点击改变验证码
    $("#changeCode").click(function () {
        $("#codeImage").attr("src", "/bookstore/code?" + new Date().getTime());
    });

    //利用ajax验证用户名和密码是否正确
    $("#login").click(function () {
        var status = base.encode("login");
        var username = $("#username").val();
        var password = $("#password").val();
        var code = $("#code").val();
        $.ajax({
            type:"POST",
            url:"user",
            data:{
                status:status,
                username:username,
                password:password,
                code:code
            },
            success:function (data) {
                if(data.code === 200){
                    $("#username").val("");
                    $("#password").val("");
                    window.location.href = "list-book.html";
                }else{
                    alert(data.msg);
                }
                $("#codeImage").attr("src", "/bookstore/code?" + new Date().getTime());
            },
            error:function (data) {
                alert(data.responseText + "|" + data.status);
            }
        })
    })
});