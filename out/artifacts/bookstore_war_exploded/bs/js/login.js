$(function () {
    var base = new Base64();
    var status = base.encode("session");
    //查找session中的[username,password],存在则放入input中
    $.post("user",{status:status},function (data) {
        if(!(data.username == null || data.password == null)){
            $("#username").val(data.username);
            $("#password").val(data.password);
        }
    });

    //利用ajax验证用户名和密码是否正确
    $("#login").click(function () {
        var status = base.encode("login");
        var username = $("#username").val();
        var password = $("#password").val();
        $.ajax({
            type:"POST",
            url:"user",
            data:{
                status:status,
                username:username,
                password:password
            },
            success:function (data) {
                if(data.code === 200){
                    $("#username").val("");
                    $("#password").val("");
                    window.location.href = "msg-board.html";
                }else{
                    alert(data.msg);
                }
            },
            error:function (data) {
                alert(data.responseText + "|" + data.status);
            }
        })
    })
});