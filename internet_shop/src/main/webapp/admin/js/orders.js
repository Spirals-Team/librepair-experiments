$(document).ready(function () {
    $.ajax({
        method:"POST",
        url:"/shop/orders",
        complete:function (d) {
            var res = JSON.parse(d.responseText);
            var div='';
            for (var i=0; i<res.length; i++) {
                div+='<p class="order-datetime" >'+res[i].data+'</p>';
                div+='<p class="order-number" >Заказ № '+res[i].id+' - оплачен</p>';
                div+='<p class="order-link" ><a class="green" href="view_order.html?id='+res[i].id+'" >Подробнее</a></p>';
            }
            $('#order').html(div);
        }
    });
});
