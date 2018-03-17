$(document).ready(function () {
    showtowns();
    $('#country_id').change(function () {
        var country_id = $(this).val();
        if (country_id == '0') {
            $('#city_id').html('<option>- выберите город -</option>');
            $('#city_id').attr('disabled', true);
            return(false);
        }
        selectcity(country_id);
    });
    checklogin();
    $("#ajax").submit(function () {
        var error = false;
        var res ='';
        var form = $(this);
        form.find('input').each(function () {
            if ($(this).val()=='') {
                error = true;
                alert('Заполните поле ' +$(this).attr('name') + '!');
                $(this).css('background-color', 'red');
                return false;
            }
        });
        if (!error) {
            send(form);
        }

    });
});

function showtowns() {
    $.ajax({
        method: "get",
        url: '/items/json',
        data: {select :'country'},
        complete:function (result) {
            var address = JSON.parse(result.responseText);
            var options = '';
            for (var i=0; i<address.length; i++) {
                options+='<option value="' + address[i].id + '">' + address[i].name + '</option>';
            }
            $('#country_id').html('<option value="0">-Выберите страну-</option>' + options);
        }
    });
}
function selectcity(country_id) {
    $('#city_id').attr('disabled', true);
    $('#city_id').html('<option>загрузка...</option>');
    $.ajax({
        method:"get",
        url:'/items/json',
        data: {select :'towns', id:country_id},
        complete:function (result) {
            var add = JSON.parse(result.responseText);
            var options = '';
            for (var i=0; i<add.length; i++) {
                options+='<option value="' + add[i].id + '">' + add[i].name + '</option>';
            }
            $('#city_id').html('<option value="0">-Выберите город-</option>' + options);
            $('#city_id').attr('disabled', false);
        }
    });
}
function checklogin() {
    $('#login').on('input', function () {
        var msg = $(this).val();
        $.ajax({
            method:"get",
            url:'/items/user',
            data:{select:'login', login:msg},
            complete:function (result) {
                var valid = JSON.parse(result.responseText);
                if (true==valid){
                    $("#errmsg").text("Логин занят");
                    $("#button").html('<button disabled type="submit" id="submit" class="btn btn-default btn-lg">Зарегистрироваться</button>');
                } else {
                    $("#errmsg").text("Логин доступен");
                    $("#button").html('<button type="submit" id="submit" class="btn btn-default btn-lg">Зарегистрироваться</button>');
                }
            }
        });
    });
}
function send(data) {
    var dataForm = data.serialize();
    $.ajax({
        method:"POST",
        url:'/items/user',
        data:dataForm,
        success:function (result) {
         alert(result);
         // document.querySelector('response').innerHTML = r.responseText;
         // document.querySelector('registration').style.display = 'none';
        }

    });

}