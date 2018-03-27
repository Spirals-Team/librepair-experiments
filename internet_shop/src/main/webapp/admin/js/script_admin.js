$(document).ready(function () {
checkSession();
        $('#header').load("include/header.html");
        showProduct();
    }
);
function checkSession() {
    $.ajax({
        method: 'GET',
        url: "/shop/adminLogin",
        data: {'session':'exist'},
        complete: function (data) {
            var res = JSON.parse(data.responseText);
            if (res ==='notExist') {
                 if (window.location.pathname.split('/')[3] !=='login.html' ) {
                     window.location = "http:/shop/admin/login.html";
                 }
            }
        }
    });
}
$('#reg').submit(function () {
    var form = $(this).serialize();
    $.ajax({
        method:'POST',
        url:'/shop/adminLogin',
        data:form,
        complete:function (data) {
            var result = JSON.parse(data.responseText);
            if (result===false) {
                $('#msgerror').show();
            } else {
                window.location = "http:/shop/admin/index.html";
            }
        }
    });
    return false;
});
$(document).on('click', '#log-out', function () {
    $.ajax({
        method:'GET',
        url:"/shop/adminLogin",
        data:{'id':'exit'},
        complete:function () {
            location.reload();
        }
    });
});
function showProduct() {
    $.ajax({
        method:"GET",
        url:"/shop/products",
        data:{sort:"Without_Sorting"},
        complete:function (d) {
            var product = JSON.parse(d.responseText);
            var li ="";
            for (var i=0; i<product.length; i++) {
                li+='<li><p>'+product[i].name+'</p>';
                li+='<p align="center" class="link-action" >';
                li+='<a class="green" href="edit_product.html?id='+product[i].id +'">Изменить</a> | <a id='+product[i].id+' class="delete" >Удалить</a>';
                li+='</p></li>';
            }
            $('#block-tovar').html(li);
            $('#count-style').html('<a>Всего товаров - <strong>'+product.length+'</strong></a>');
        }
    });
}
$(document).on('click', '.delete' ,function () {
    if (confirm("Удалить данный товар ?")) {
        var id = $(this).attr('id');
     $.ajax({
         method:"POST",
         url:"/shop/products",
         data:{"action":"delete", "id":id},
         complete:function () {
             showProduct();
         }
     })
    }
});
$('#add_product').submit(function () {
   var form = $(this).serialize();
    $.ajax({
        method:"POST",
        url:"/shop/products",
        data:form,
        complete:function (d) {
            var res = JSON.parse(d.responseText);
            if (res == "ok") {
                $("#add_product").fadeOut(300,function() {
                    $("#message").addClass("message_good").fadeIn(400).html("Товар успешно добавлен");
                    $("#form_submit").hide();
                });
            }
            else {
                $("#message").addClass("message_error").fadeIn(400).html(res);
            }
        }
   });
    return false;
});
