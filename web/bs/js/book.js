$(function () {
    var base = new Base64();
    var status = base.encode("session");
    //查找session中的[username,password],存在则放入input中
    $.post("user",{status:status},function (data) {
        if(data.code === 500){
            window.location.href = "login.html";
        }else{
          $(".user").html(data.username);
        }
    });



    //获取图书信息
    $("#search").click(function () {
        var formData = new FormData();
        var status = base.encode("show");
        var key = $("#key").val();
        formData.append("status", status);
        formData.append("key", key);
        $.ajax({
            type:"POST",
            url:"book",
            processData : false,
            contentType : false,
            data:formData,
            success:function (data) {
                //替换关键字
                data = replaceKeyFn(key, data);
                //直接对接收的数据进行分页
                var currentPage = 1;//当前页
                var size = 5;//每页显示几条数据
                var totalPage = data.length <= size ? 1 : Math.floor((data.length + size - 1) / size);//总页数


                $("#total-page").html(data.length);//总共几条数据
                $("#now-page").html(currentPage);//当前页

                //首页
                $("#begin").click(function () {
                    currentPage = 1;
                    $(this).removeClass("active");
                    $("#prev").removeClass("active");
                    $("#next").addClass("active");
                    $("#end").addClass("active");
                    $("#now-page").html(currentPage);//当前页
                    pageFn(currentPage, size);

                });

                //尾页
                $("#end").click(function () {
                    currentPage = totalPage;
                    $(this).removeClass("active");
                    $("#next").removeClass("active");
                    $("#prev").addClass("active");
                    $("#begin").addClass("active");
                    $("#now-page").html(currentPage);//当前页
                    pageFn(currentPage, size);
                });

                //上一页
                $("#prev").click(function () {
                    if(currentPage <= 2){
                        $(this).removeClass("active");
                        $("#begin").removeClass("active");
                        currentPage = 1;
                    }else{
                        currentPage--;
                        $(this).addClass("active");
                    }
                    $("#next").addClass("active");
                    $("#end").addClass("active");
                    $("#now-page").html(currentPage);//当前页
                    pageFn(currentPage, size);
                });

                //下一页
                $("#next").click(function () {
                    if(currentPage >= totalPage-1){
                        $(this).removeClass("active");
                        $("#end").removeClass("active");
                        currentPage = totalPage;
                    }else{
                        currentPage++;
                        $(this).addClass("active");
                    }
                    $("#prev").addClass("active");
                    $("#begin").addClass("active");
                    $("#now-page").html(currentPage);//当前页
                    pageFn(currentPage, size);
                });

                //跳转页
                $(".page-btn").bind("click",function () {
                    var page = $(".page-input").val();
                    if(isNaN(page)){
                        alert("请输入数字");
                    }else if(page < 1 || page > totalPage){
                        alert("请输入正确的页码在1~" + totalPage + "之间");
                    }else{
                        currentPage = parseInt(page);
                        $("#next").addClass("active");
                        $("#end").addClass("active");
                        $("#prev").addClass("active");
                        $("#begin").addClass("active");
                        if(currentPage < 2) {
                            $("#prev").removeClass("active");
                            $("#begin").removeClass("active");
                            $("#next").addClass("active");
                            $("#end").addClass("active");
                        }
                        if(currentPage > totalPage-1){
                            $("#next").removeClass("active");
                            $("#end").removeClass("active");
                            $("#prev").addClass("active");
                            $("#begin").addClass("active");
                        }
                        $("#now-page").html(currentPage);//当前页
                        pageFn(currentPage, size);
                    }
                });


                //直接先显示一次
                pageFn(currentPage, size);

                function pageFn(currentPage, size) {
                    var $table = $("#book-table");
                    $("#book-table .book-msg").remove();

                    //开始添加数据
                    for(var i = (currentPage-1)*size; i < currentPage*size; i++){
                        if(i > data.length-1)break;
                        var $tr = $("<tr class='book-msg'>\n" +
                            "                            <td>\n" +
                            "                                <div class=\"table-list\">\n" +
                            "                                    <label for=\" " + data[i].id + " \">\n" +
                            "                                        <input type=\"checkbox\" class=\"rember\" id=\"" + data[i].id + "\" value=\"" + data[i].id + "\" index=\"" + i + "\">\n" +
                            "                                        <span class=\"checked\"><i class=\"fa fa-check-square-o\"></i></span>\n" +
                            "                                        <span class=\"unchecked\"><i class=\"fa fa-square-o\"></i></span>\n" +
                            "                                    </label>\n" +
                            "\n" +
                            "                                    <span class=\"table-number\">" + data[i].id + "</span>\n" +
                            "                                </div>\n" +
                            "                            </td>\n" +
                            "                            <td><img src=\"image/" + data[i].image + "\" alt=\"\"></td>\n" +
                            "                            <td>" + data[i].name + "</td>\n" +
                            "                            <td>￥" + data[i].price + "</td>\n" +
                            "                            <td>" + data[i].type + "</td>\n" +
                            "                            <td>\n" +
                            "                                <ul>\n" +
                            "                                    <li class=\"opera\"><span class='hide_book'><i class=\"fa fa-eye\"></i></span></li>\n" +
                            "                                    <li class=\"opera\"><span class='edit_book' num='" + data[i].id + "'><i class=\"fa fa-edit\"></i></span></li>\n" +
                            "                                    <li class=\"opera\"><span class='del_book' num='" + data[i].id + "' index=\"" + i + "\"><i class=\"fa fa-trash\"></i></span></li>\n" +
                            "                                </ul>\n" +
                            "                           </td>\n" +
                            "</tr>");
                        $table.append($tr);
                        //先解除事件,避免点击依次执行多次删除事件
                        $(".del_book").unbind("click");
                        //删除图书信息
                        $(".del_book").bind("click",function () {
                            var isDel = confirm("确定删除图书信息吗?");
                            var index= parseInt($(this).attr("index"));
                            if(isDel){
                                var formData = new FormData();
                                var status = base.encode("delete");
                                var id = $(this).attr("num");
                                formData.append("status", status);
                                formData.append("id", id);
                                $(this).parent().parent().parent().parent().remove();
                                data.splice(index,1);
                                totalPage = data.length <= size ? 1 : Math.floor((data.length + size - 1) / size);//重新计算总页数
                                $("#total-page").html(data.length);
                                $.ajax({
                                    type:"POST",
                                    url:"book",
                                    processData : false,
                                    contentType : false,
                                    data:formData,
                                    success:function (data) {
                                        alert(data.msg);
                                    }
                                })
                            }
                            //通过关键字获取图书信息
                            return false;
                        });

                        //修改图书信息
                        $(".edit_book").bind("click",function () {
                            var id = $(this).attr("num");
                            window.location.href = "edit-book.html?id=" + id;
                        });

                        //先解除事件,避免点击依次执行多次删除事件
                        $("#delMore").unbind("click");

                        //批量删除图书信息
                        $("#delMore").click(function () {
                            var formData = new FormData();
                            var status = base.encode("batchDelete");
                            var ids = getIdFn($(".rember"));
                            formData.append("status", status);
                            formData.append("ids", JSON.stringify(ids));
                            for(var j in ids){
                                $("#" + ids[j].id).parent().parent().parent().parent().remove();
                                for(var k in data){
                                    if(ids[j].id == data[k].id){
                                        data.splice(k, 1);
                                        break;
                                    }
                                }
                            }
                            totalPage = data.length <= size ? 1 : Math.floor((data.length + size - 1) / size);//重新计算总页数
                            $("#total-page").html(data.length);
                            $.ajax({
                                type:"POST",
                                url:"book",
                                processData : false,
                                contentType : false,
                                data:formData,
                                success:function (data) {
                                    alert(data.msg);
                                }
                            })
                        });

                        //隐藏图书信息
                        $(".hide_book").click(function () {
                            $(this).parent().parent().parent().parent().addClass("hide");
                        });

                        //取消隐藏图书信息
                        $(".cancel-hide").click(function () {
                            $(".hide_book").each(function () {
                                $(this).parent().parent().parent().parent().removeClass("hide");
                            })
                        })
                    }
                }

            },
            error:function (res) {
                alert(res.responseText);
            }
        })
    });




    //获取所有选中的图书ID
    function getIdFn(ele){
        var list = [];
        ele.each(function () {
            if($(this).is(":checked")){
                var id = {"id" : $(this).val()};
                list.push(id);
            }
        });
        return list;
    }

    //将关键字以红色特殊字体显示
    function replaceKeyFn(key, data){
        for(var i = 0; i < data.length; i++){
            if(key !== ""){
                data[i].name = data[i].name.replaceAll(key, "<span style='font-weight:bold;color:red'>" + key + "</span>");
                data[i].price = (data[i].price+"").replaceAll(key, "<span style='font-weight:bold;color:red'>" + key + "</span>");
                if(isNaN(data[i].type))
                    data[i].type = data[i].type.replaceAll(key, "<span style='font-weight:bold;color:red'>" + key + "</span>");
            }
        }
    }

    //自动触发点击事件
    $("#search").trigger("click");

});