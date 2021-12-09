$(function () {
    var status = new Base64().encode("session");
    //查找session中的[username,password],存在则放入input中
    $.post("user",{status:status},function (data) {
        if(data === null){
            window.location.href = "login.html";
        }else{
            $(".pic img").attr("src", data.portrait);
            $(".nav-pic img").attr("src", data.portrait);
            $(".user").html(data.username);
            $("#author").val(data.username);
            $("#author").attr("uid",data.id);
        }
    });
})