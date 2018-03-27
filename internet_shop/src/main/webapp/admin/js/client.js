$(document).ready(function () {
    showClient();
});
function showClient() {
    $.ajax({
        method:'POST',
        data:{'action':'show'},
        url:"/shop/clients",
        complete:function (d) {
            var client = JSON.parse(d.responseText);
            var teg='';
            for(var i = 0; i<client.length; i++) {
                teg+='<p class="client-name" >Login: '+ client[i].name+'</p>';
                teg+='<p class="client-password" >Password: <strong>'+ client[i].password+'</strong></p>';
                teg+='<p class="client-links" ><a class="del" id="'+client[i].id+'">Удалить</a></p>';
            }
            $('.block-clients').html(teg);
        }
    });
}
$(document).on('click', '.del' ,function () {
    if (confirm("Удалить пользователя ?")) {
        var id = $(this).attr('id');
        $.ajax({
            method:"POST",
            url:"/shop/clients",
            data:{"action":"delete", "id":id},
            complete:function (d) {
                var res = JSON.parse(d.responseText);
                if (res==false) {
                    $("#message").addClass("message_error").fadeIn(400).html("У данного клиента есть оплаченные заказы.");
                }
              showClient();
            }
        })
    }
});