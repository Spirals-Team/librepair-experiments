$(document).ready(function () {
    showUserCart();
    $(document).on('click','.count-plus', function () {
        var iid = $(this).attr('iid');
        $.ajax({
            method:'GET',
            url:"/shop/userCart",
            data:{"action":"increment", "id":iid},
            complete:function () {
                showUserCart();
                loadCart();
            }
        });
    });
    $(document).on('click','.count-minus', function () {
        var iid = $(this).attr('iid');
        var q = $(this).attr('count');
        if (q > 1) {
        $.ajax({
            method: 'GET',
            url: "/shop/userCart",
            data: {"action": "discrement", "id": iid},
            complete: function () {
                showUserCart();
                loadCart();
            }
        });
    }
    });
    $(document).on('keypress','.count-input', function (e) {
        if (e.keyCode==13) {
            var id = $(this).attr('iid');
            var count =$('#input-id'+id).val();
            $.ajax({
                method:"GET",
                url:"/shop/userCart",
                data:{"action":"setCount","cart_id":id,"count":count},
                complete:function (d) {
                    showUserCart();
                    loadCart();
                }
            });
        }
    });
$(document).on('click','.button-next', function () {
    $.ajax({
        method:"POST",
        url:"/shop/userCart",
        success:function (f) {
            if (f=="ok") {
                $("#block-content").fadeOut(300, function () {
                    $("#message").addClass("message_good").fadeIn(400).html("Ваш заказ принят!");
                    $("#block-content").hide();
                    loadCart();
                });
            } else {
                $("#message").addClass("message_error").fadeIn(400).html(f);
            }
        }
    });
    return false;
});
});
function deleteCart() {
    $.ajax({
        method:'GET',
        url:"/shop/userCart",
        data:{"action":"deleteAll"},
        complete:function () {
            showUserCart();
        }
    })
}
function deleteProduct(id){
   $.ajax({
       method:'GET',
       url:"/shop/userCart",
       data:{"action":"deleteProduct","id":id},
       complete:function () {
           showUserCart();
           loadCart();
       }
   })
}
