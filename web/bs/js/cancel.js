$(function () {
    var status = new Base64().encode("cancel");

    $("#changeUser").click(function () {
        $.post("user",{status:status},function (data) {
            if(data.code === 200){
                window.location.href = "login.html";
            }
        })
    });

    $("#exit").click(function () {
        window.location.href = "index.html";
    })
});