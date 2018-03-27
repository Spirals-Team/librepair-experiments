$(document).ready(function () {
    var id = getId();
    $.ajax({
        method:"POST",
        url:"/shop/orders",
        data:{"id":id},
        complete:function (d) {
            var product = JSON.parse(d.responseText);
            var div = '';
            div+='<p class="order-number" >Заказ № '+id+' - оплачен </p>';
            div+='<TABLE align="center" CELLPADDING="10" WIDTH="100%">';
            div+='<TR><TH>id</TH><TH>Наименование товара</TH><TH>Цена</TH><TH>Количество</TH><TH>Сумма</TH></TR>';
            div+='<TR><TD  align="CENTER" >'+product.id+'</TD>';
            div+='<TD  align="CENTER" >'+ product.name+'</TD>';
            div+='<TD  align="CENTER" >'+product.price+' руб</TD>';
            div+='<TD  align="CENTER" >'+product.amount+'</TD>';
            div+='<TD  align="CENTER" >'+product.amount*product.price+'</TD></TR>';
            $('#order').html(div);
        }
    });
    $.ajax({
        method:"POST",
        url:"/shop/consumer",
        data:{"id":id},
        complete:function (d) {
            var user = JSON.parse(d.responseText);
            var div = '';
            div+='<p class="order-number" >Покупатель </p>';
            div+='<TABLE align="center" CELLPADDING="10" WIDTH="100%">';
            div+='<TR><TH>id</TH><TH>Логин</TH><TH>Пароль</TH></TR>';
            div+='<TR><TD  align="CENTER" >'+user.id+'</TD>';
            div+='<TD  align="CENTER" >'+ user.name+'</TD>';
            div+='<TD  align="CENTER" >'+user.password+'</TD><TD></TD></TR>';

            $('#consumer').html(div);
        }
    });

});
function getId() {
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('=');
    return sURLVariables[1];
}
