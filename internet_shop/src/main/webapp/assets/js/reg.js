$(document).ready(function() {
    $('#block-header').load("assets/include/block-header.html");
    $('#block-category').load("assets/include/block-category.html");
    $('#block-parameter').load("assets/include/block-parameter.html");
    $('#block-footer').load("assets/include/block-footer.html");

    $('#form_reg').validate({
        // правила для проверки
        rules:{
            "reg_login":{
                required:true,
                minlength:5,
                maxlength:15,
                remote: {
                    type: "post",
                    url: "/shop/login"
                }
            },
            "reg_pass":{
                required:true,
                minlength:7,
                maxlength:15
            },
        },

        // выводимые сообщения при нарушении соответствующих правил
        messages:{
            "reg_login":{
                required:"Укажите Логин!",
                minlength:"От 5 до 15 символов!",
                maxlength:"От 5 до 15 символов!",
                remote: "Логин занят!"
            },
            "reg_pass":{
                required:"Укажите Пароль!",
                minlength:"От 7 до 15 символов!",
                maxlength:"От 7 до 15 символов!"
            },
        },

    });
    $('#form_reg').submit(function () {
        var form = $(this).serialize();
        $.ajax({
            method:'POST',
            url:'/shop/register',
            data:form,
            complete:function (data) {
                var valid = JSON.parse(data.responseText);
                if (valid =='true') {
                    $("#block-form-registration").fadeOut(300,function() {
                        $("#reg_message").addClass("reg_message_good").fadeIn(400).html("Вы успешно зарегистрированы!");
                        $("#form_submit").hide();
                    });
                }
                else {
                    $("#reg_message").addClass("reg_message_error").fadeIn(400).html(valid);
                }
            }
        });
        return false;
    });
    $('#gen_pass').click(function () {
        $.ajax({
        type:'GET',
            url:'/shop/login',
            dataType:'html',
            success(data){
            $('#reg_pass').val(data);
            }
        });

    });
});
