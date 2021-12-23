var base64 = new Base64();
var status = "";
var currentPage = 1;
var size = 3;
var totalPage = 0;
var users = null;

function pageFn(){
   var power = null;
   var low = (currentPage-1)*size;
   var high = currentPage*size;
   if(high >= users.length || users.length <= size){
       high = users.length;
   }
   $("#all-user").empty();
   for(var i = low; i < high; i++){
       if(i > users.length-1)break;
           if(users[i].power === 1){
               power = "<input type=\"checkbox\" uid=\"" + users[i].id + "\" name=\"sex\" id=\""+ users[i].username + "\" class=\"chooseBtn\"   checked>";
           }else{
               power = "<input type=\"checkbox\" uid=\"" + users[i].id + "\" name=\"sex\" id=\""+ users[i].username + "\" class=\"chooseBtn\"   >";
           }

           var $user = $("<li><div class=\"user-box\">\n" +
               "                                   <br>\n" +
               "                                   <div class=\"portrait\">\n" +
               "                                       <img src=\"" + users[i].portrait + "\" alt=\"\">\n" +
               "                                   </div>\n" +
               "                                      <br>\n" +
               "                                   <div class=\"username\" title='" + users[i].username + "'>\n" +
               "                                       <label class=\"bold\">用户名: <span>" + users[i].username + "</span></label>\n" +
               "                                   </div>\n" +
               "                                   <div class=\"power\">\n" +
               "                                       <span class=\"bold\">开关: </span>\n" +
               "                                      " + power + "\n" +
               "                                       <label for=\"" + users[i].username + "\" class=\"choose-label\"></label>\n" +
               "                                   </div>\n" +
               "                                   <div class=\"handle\">\n" +
               "                                       <span class=\"bold\">操作: </span>\n" +
               "                                       <span class=\"edit-user\" id='" + users[i].id + "' onclick='editFn(this)'><i class=\"fa fa-edit\"></i></span>\n" +
               "                                       <span class=\"del-user\" num='" + i + "' id='" + users[i].id + "' onclick='deleteFn(this)'><i class=\"fa fa-trash\"></i></span>\n" +
               "                                   </div>\n" +
               "                               </div></li>");
           $("#all-user").append($user);
           $(".chooseBtn").click(function () {
               status = base64.encode("update");
               var power;
               var id = $(this).attr("uid");
               if($(this).is(":checked")){
                   $(this).prop("checked", false);
                   power = 0;
               }else{
                   $(this).prop("checked", true);
                   power = 1;
               }
               //console.log($(obj).prop("checked") + "|" + id + "|" + power);
               $.post("user",{
                   status:status,
                   id:id,
                   power:power
               },function (res) {
                   if(res.code === 200){
                       tips(res.msg, true);
                   }else{
                       tips(res.msg, false);
                   }
               });

           });
   }


}

function chooseFn(obj) {
    status = base64.encode("update");
    var power;
    var id = $(obj).attr("uid");
    if($(obj).is(":checked")){
        $(obj).prop("checked", false);
        power = 0;
    }else{
        $(obj).prop("checked", true);
        power = 1;
    }
    //console.log($(obj).prop("checked") + "|" + id + "|" + power);
    $.post("user",{
        status:status,
        id:id,
        power:power
    },function (res) {
        if(res.code === 200){
            tips(res.msg, true);
        }else{
            tips(res.msg, false);
        }
    });
}


function editFn(obj) {
    var id = $(obj).attr("id");
    window.location.href = "edit-user.html?id=" + id;
}

function deleteFn(obj) {
    var res = confirm("你确定要删除吗?");
    if(res){
        var id = $(obj).attr("id");
        status = base64.encode("delete");
        console.log(id);
        $.post("user",{
            status:status,
            id:id
        },function (data) {
            if(data.code === 200){
                var num = Number($(obj).attr("num"));
                users.splice(num,1);
                tips(data.msg, true);
            }else{
                tips(data.msg, false);
            }
            pageFn();

        });
    }
}

function pageClick(){
    $("#prev").click(function () {
        if(currentPage < 2){
            currentPage = 1;
        }else{
            currentPage--;
        }
        pageFn();
    });

    $("#next").click(function () {
        if(currentPage >= totalPage){
            currentPage = totalPage;
        }else{
            currentPage++;
        }
        pageFn();
    });
}

$(function () {
    status = base64.encode("show");
    //获取所有用户
    $.post("user",{status:status},function (data) {
        users = data;
        totalPage = users.length <= size ? 1 : Math.floor((users.length + size - 1) / size);
        pageFn();
        pageClick();
    })


});