$(document).ready(function () {
    $('#block-header').load("assets/include/block-header.html");
    $('#block-category').load("assets/include/block-category.html");
    $('#block-parameter').load("assets/include/block-parameter.html");
    $('#block-footer').load("assets/include/block-footer.html");
    if ($.cookie('select_style')==null) {
        $.cookie('select_style', 'grid');
        $('#style-grid').attr('src', 'assets/imj/icon-grid-active.png');
        $.cookie('sort','Without_Sorting');
        showProductsGrid('Without_Sorting');
    } else if ($.cookie('select_style')=='grid') {
        $('#block-tovar-grid').show();
        $('#block-tovar-list').hide();
        $('#style-grid').attr('src', 'assets/imj/icon-grid-active.png');
        $('#style-list').attr('src', 'assets/imj/icon-list.png');
        showProductsGrid($.cookie('sort'));
    } else if ($.cookie('select_style')=='list') {
        $('#block-tovar-grid').hide();
        $('#block-tovar-list').show();
        $('#style-list').attr('src', 'assets/imj/icon-list-active.png');
        $('#style-grid').attr('src', 'assets/imj/icon-grid.png');
        showProductsList($.cookie('sort'));
    }

    $('#options-sort li').click(function () {
            $.cookie('sort',$(this).attr('id')) ;
            if ($.cookie('select_style')=='grid') {
                showProductsGrid($.cookie('sort'));
            }
            if ($.cookie('select_style')=='list') {
                showProductsList($.cookie('sort'));
            }
        }
    );
    $('#style-list').click(function () {
        showProductsList($.cookie('sort'));
        $('#block-tovar-grid').hide();
        $('#block-tovar-list').show();
        $('#style-list').attr('src', 'assets/imj/icon-list-active.png');
        $('#style-grid').attr('src', 'assets/imj/icon-grid.png');
        $.cookie('select_style', 'list');
    });

    $('#style-grid').click(function () {
        showProductsGrid($.cookie('sort'));
        $('#block-tovar-grid').show();
        $('#block-tovar-list').hide();
        $('#style-grid').attr('src', 'assets/imj/icon-grid-active.png');
        $('#style-list').attr('src', 'assets/imj/icon-list.png');
        $.cookie('select_style', 'grid');
    });
    $(function() {
        $("#newsticker").jCarouselLite({
            vertical:true,
            hoverPause:true,
            btnNext: "#news-next",
            btnPrev: "#news-prev",
            visible:3,
            auto:3000,
            speed:500
        });
    });
    checkSession();
    //добавление товара в корзину с главной страницы
    $(document).on('click','.add-cart-style-grid, .add-cart-style-list, .add-cart', function () {
        var id = $(this).attr('id');
        $.ajax({
            method:'POST',
            url:"/shop/cart",
            data:{'id':id},
            complete:function (data) {
                var result = JSON.parse(data.responseText);
                if (result !== '0') {
                    $("#reg_message").addClass("reg_message_error").fadeIn(400).html(result);
                } else {
                    loadCart();
                }
            }
        })
    });
});
function loadCart() {
    $.ajax({
        method:"POST",
        url:"/shop/loadcart",
        complete:function (data) {
            var result = JSON.parse(data.responseText);
            if(result ==='') {
                $("#block-basket > a").html("Корзина пуста");
            } else {
                $("#block-basket > a").html(result);
            }
        }
    });
}
function showProductsGrid(id) {
    $.ajax({
        method:"GET",
        url:"/shop/products",
        data:{sort:id},
        complete:function (data) {
            var product = JSON.parse(data.responseText);
            var li ='<ul id="block-tovar-grid">';
            for (var i =0; i<product.length; i++ ) {
                li += '<li><p class="style-title-grid"><a href="http:/shop/view_content.html?id=' + product[i].id + '">' + product[i].name + '</a></p>';
                //отзывы и просмотры
                li += '<ul class="reviews-and-counts-grid">';
                li += '<li><img src="assets/imj/eye-icon.png"/><p>'+product[i].views+'</p></li>';
                li += '<li><img src="assets/imj/comment-icon.png" /><p>0</p></li>';
                li += '</ul>';
                //кнопка корзины
                li += '<a class="add-cart-style-grid" id="'+ product[i].id +'" ></a>';
                li += '<p class="style-price-grid" ><strong>' + product[i].price + '</strong> руб.</p>';
                li+= '<div class="mini-features" >' + product[i].miniDescription + '</div>';
                li += '</li>';
            }
            $('#product-grid').html(li + '</ul>');
        }
    });
}
function showProductsList(id) {
    $.ajax({
        method:"GET",
        url:"/shop/products",
        data:{sort:id},
        complete:function (data) {
            var product = JSON.parse(data.responseText);
            var li ='<ul id="block-tovar-list">';
            for (var i =0; i<product.length; i++ ) {
                li += '<li><p class="style-title-list"><a href="http:/shop/view_content.html?id=' + product[i].id + '">' + product[i].name + '</a></p>';
                //отзывы и просмотры
                li += '<ul class="reviews-and-counts-list">';
                li += '<li><img src="assets/imj/eye-icon.png"/><p>'+product[i].views+'</p></li>';
                li += '<li><img src="assets/imj/comment-icon.png" /><p>0</p></li>';
                li += '</ul>';
                //кнопка корзины
                li += '<a class="add-cart-style-list" id="'+ product[i].id +'""></a>';
                li += '<p class="style-price-list" ><strong>' + product[i].price + '</strong> руб.</p>';
                li+= '<div class="style-text-list" >' + product[i].description + '</div>';
                li += '</li>';

            }
            $('#product-list').html(li + '</ul>');
        }
    });
}
function checkSession() {
    $.ajax({
        method: 'GET',
        url: "/shop/authorization",
        data: {'session': 'exist'},
        complete: function (data) {
            var res = JSON.parse(data.responseText);
            if (res != null) {
                $('#reg-auth-title').replaceWith('<p id="auth-user-info" align="right"><img src="assets/imj/user.png"/>Здравствуйте, ' + res + '!</p>');
                loadCart();
            }
        }
    });
}
function showUserCart() {
    $.ajax({
        method:'GET',
        url:"/shop/userCart",
        data:{"action":"showCart"},
        complete:function (data) {
            var list = JSON.parse(data.responseText);
            if (list != null) {
                var div = "";
                var itog=0;
                for (var i = 0; i<list.length; i++) {
                    var p = list[i].price;
                    var q = list[i].quantity;
                    var summ = p*q;
                    itog+=summ;
                    div +='<div class="block-list-cart">';
                    div +='<div class="title-cart">';
                    div +='<p><a href="http:/shop/view_content.html?id='+list[i].productId+'">'+ list[i].title +'</a></p>';
                    div +='<p class="cart-mini-features">'+ list[i].description +'</p></div>';

                    div+='<div class="count-cart">';
                    div+='<ul class="input-count-style">';
                    div+='<li><p align="center" iid="'+list[i].id+'" count="'+list[i].quantity+'" class="count-minus">-</p></li>';

                    div+='<li><p align="center"><input id="input-id'+list[i].id+'" iid="'+list[i].id+'" class="count-input" maxlength="3" type="text" value="'+list[i].quantity+'" /></p></li>';

                    div+='<li><p align="center" iid="'+list[i].id+'" class="count-plus">+</p></li>';
                    div+='</ul></div>';
                    div+='<div id="tovar'+list[i].productId+'" class="price-product"><h5><span class="span-count" >'+list[i].quantity+'</span> x <span>'+list[i].price+'</span></h5><p>'+summ+' руб</p>';
                    div+='</div>';
                    div+='<div class="delete-cart">';
                    div+='<a onclick="deleteProduct('+list[i].id+')"><img src="assets/imj/bsk_item_del.png" /></a>';
                    div+='</div>';
                    div+='<div id="bottom-cart-line"></div>';
                    div+='</div>';
                }
                div+='<h2 class="itog-price" align="right">Итого: <strong>'+itog+'</strong> руб</h2>';
                div+='<p align="right" class="button-next" ><a href="" >Оплатить</a></p>';
                $('#show-cart').html(div);
            } else {
                $('#show-cart').html('<h3 id="clear-cart" align="center">Корзина пуста</h3>');
            }
        }
    });
}
