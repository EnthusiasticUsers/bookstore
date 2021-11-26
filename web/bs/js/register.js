$(function () {
    //获取学历
   $.post("degree",function (data) {
       var $degree = $("#degree");
       $(data).each(function () {
           var option = $("<option value='" + this.id + "'>" + this.degree + "</option>");
           $degree.append(option);
       })
   });
    //获取爱好
    $.post("hobby",function (data) {
        var $hobby = $("#hobby");
        $(data).each(function () {
            var checkbox = $("<div class='el-hobby'><label>" + this.hobby + "</label><input class='s-hobby' type='checkbox' value='" + this.id + "'></div>");
            $("#hobby").append(checkbox);
        })
    });


    //查看是否勾选【同意注册协议】
    var base = new Base64();
    $("#register").click(function () {
        if(!$(".rember").is(":checked")){
            alert("请勾选同意注册协议");
        }else if(Number($("#username").val()) || ($("#username").val().length < 3 && $("#username").val().length > 8)){
            alert("用户名不能是数字,并且长度在3~8之间");
        } else if($("#password").val() != $("#repassword").val()){
            alert("两次密码不一致");
        }else if(!checkFn($(".el-hobby input[type=checkbox]"))){
            alert("爱好至少选一个");
        }else{
            //获取表单值
            var status = base.encode("register");
            var username = $("#username").val();
            var password = $("#password").val();
            var sex = $("input[type=radio]:checked").val();
            var degree = $("#degree option:selected").val( );
            var hobbies = getHobbyFn($(".s-hobby"));
            //console.log(username,password,sex,degree,hobbies);

            //通过ajax插入数据
            $.ajax({
                type:"POST",
                url:"user",
                data:{
                    status:status,
                    username:username,
                    password:password,
                    sex:sex,
                    degree:degree,
                    hobbies:JSON.stringify(hobbies)
                },
                traditional:true,
                success:function (data) {
                    if(data.code === 200){
                        alert("注册成功,正在跳转登录界面");
                        window.location.href = "login.html";
                    }else{
                        alert(data.code + ":" + data.msg);
                    }
                },
                error:function (msg) {
                    alert(msg.status + "|" + msg.errorCode);
                }
            })
        }
    });

    //检查爱好
    function checkFn(elementTagNames) {
        var flag = false;
        elementTagNames.each(function () {
            if($(this).is(":checked")){
                flag = true;
                return false;
            }
        });
        return flag;
    }

    //获取所有选中爱好
    function getHobbyFn(hobbies) {
        var list = [];
        hobbies.each(function () {
            if($(this).is(":checked")){
               var hobby = {"id":$(this).val(), "hobby":$(this).prev().text()};
               list.push(hobby);
            }
        });
        return list;
    }
});