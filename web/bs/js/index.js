var currentPage = 1;
var size = 8;
var totalPage = 0;
var books = null;

function pageFn(){
    var low = (currentPage-1)*size;
    var high = currentPage*size;
    if(high >= books.length || books.length <= size){
        high = books.length;
    }
    var $books = $(".product-ul");
    //开始添加数据
    $books.empty();
    for(var i = low; i < high; i++) {
        if (i > books.length - 1) break;
        $book = $("<li class=\"product-li\">\n" +
            "                <a href=\"#\"><img src=\"" + books[i].image + "\" alt=\"\"></a>\n" +
            "                <a href=\"#\"><p class=\"author\">" + books[i].name + "</p></a>\n" +
            "                <a href=\"#\"><p class=\"price\">￥" + books[i].price + "</p></a>\n" +
            "            </li>");
        $books.append($book);
    }
}

function pageClick(){
    $(".btn-prev").click(function () {
        if(currentPage < 2){
            currentPage = 1;
        }else{
            currentPage--;
        }
        pageFn();
    });

    $(".btn-next").click(function () {
        if(currentPage >= totalPage){
            currentPage = totalPage;
        }else{
            currentPage++;
        }
        pageFn();
    })
}

$(function () {
    var base64 = new Base64();
    var status = base64.encode("show");
    var formData = new FormData();
    formData.append("status", status);
    $.ajax({
        type:"POST",
        url:"book",
        processData : false,
        contentType : false,
        data:formData,
        success:function (data) {
            books = data;
            totalPage = books.length <= size ? 1 : Math.floor((books.length + size - 1) / size);
            pageFn();
            pageClick()


        },
        error:function (data) {
            alert(data.status + "|" + data.responseText);
        }
    });

});