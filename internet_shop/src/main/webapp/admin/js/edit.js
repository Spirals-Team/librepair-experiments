$(document).ready(function () {
    var id = getId();
    $.ajax({
        method:'GET',
        url:"/shop/view",
        data:{"id":id},
        complete:function (data) {
            var product = JSON.parse(data.responseText);
            var form='<form  id="edit_product" method="post">';
            form+='<ul id="edit-tovar">';
            form+='<li><label>Название товара</label><input type="text" name="form_title" id="form_title" value="'+product.name+'"/></li>';
            form+='<li><label>Цена</label><input type="text" name="form_price" value="'+product.price+'"/></li>';
            form+='<li><label>Количество</label><input type="text" name="form_amount" value="'+product.amount+'"/></li></ul>';
            form+='<h3 class="h3click" >Краткое описание товара</h3>';
            form+='<div class="div-editor1" >';
            form+='<textarea id="editor1" name="txt1" cols="90" rows="10">'+product.miniDescription+'</textarea></div>';
            form+='<h3 class="h3click" >Описание товара</h3>';
            form+='<div class="div-editor2" >';
            form+='<textarea id="editor2" name="txt2" cols="90" rows="15">'+product.description+'</textarea></div>';
            form+='<p><input type="hidden" name="id" value="'+ id +'"></p>';
            form+='<p><input type="hidden" name="action" value="update"></p>';
            form+='<p align="right" ><input type="submit" id="submit_form" name="submit_add" value="Изменить товар"/></p></form>';

            //
            // form+='<p><input type="hidden" name="account_number" value="15"></p>';
           $('#edit').html(form);
        }
    });
     $(document).on('submit','#edit_product', function () {
         var form = $(this).serialize();
         $.ajax({
             method:"POST",
             url:"/shop/products",
             data:form,
             complete:function (d) {
                 var res = JSON.parse(d.responseText);
                 if (res == "ok") {
                     $("#edit_product").fadeOut(300,function() {
                         $("#message").addClass("message_good").fadeIn(400).html("Товар успешно обновлен.");
                         $("#form_submit").hide();
                     });
                 }
                 else {
                     $("#message").addClass("message_error").fadeIn(400).html(res);
                 }
             }
         });
         return false;
     })
});
function getId() {
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('=');
    return sURLVariables[1];
}