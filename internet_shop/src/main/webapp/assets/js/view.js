$(document).ready(function () {
   var id = getId();
   $.ajax({
       method:'GET',
       url:"/shop/view",
       data:{"id":id},
       complete:function (data) {
           var product = JSON.parse(data.responseText);
            var div ='<div id="block-content-info">';
            div+='<div id="block-mini-description">';
            div+='<p id="content-title">'+product.name+'</p>';
            div+='<p id="style-price" >'+product.price+'руб</p>';
            div+='<a class="add-cart" id="'+product.id+'" ></a>';
            div+='<p id="content-text">'+product.description+'</p>';
            div+='</div></div>';
           $('#block-content').html(div);
       }
   })
});
function getId() {
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('=');
    return sURLVariables[1];
}