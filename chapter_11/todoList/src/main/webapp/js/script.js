$(document).ready(function () {
    showToDo();

});
function showToDo() {
    $.ajax({
        method:"POST",
        url:"/todo/items",
        data:{'status':'not'},
        complete:function (d) {
            var item = JSON.parse(d.responseText);
            var tab='<TABLE align="center" CELLPADDING="10" WIDTH="100%">';
            tab+='<TR><TH style="width: 15%">Data</TH><TH style="width: 70%">Description</TH><TH style="width: 15%">Status</TH></TR>';
            for (var i=0;i<item.length; i++) {
                tab+='<tr><td align="center" class="data">'+item[i].created+'</td><td align="left">'
                    + item[i].description+'</td><td align="center"><input class="check"  id="' + item[i].id + '" type="checkbox"></td><tr>';
            }
            $('#tab_items').html(tab);
        }
    });
}
function showAll() {
    $.ajax({
        method:"POST",
        url:"/todo/items",
        data:{'status':'all'},
        complete:function (d) {
            var item = JSON.parse(d.responseText);
            var tab='<TABLE align="center" CELLPADDING="10" WIDTH="100%">';
            tab+='<TR><TH style="width: 15%">Data</TH><TH style="width: 70%">Description</TH><TH style="width: 15%">Status</TH></TR>';
            for (var i=0;i<item.length; i++) {
                if (item[i].done) {
                    tab += '<tr><td align="center" class="data">' + item[i].created + '</td><td align="left">'
                        + item[i].description + '</td><td align="center"><input class="check" id="' + item[i].id + '" type="checkbox" checked="checked"></td><tr>';
                } else {
                    tab += '<tr><td align="center" class="data">' + item[i].created + '</td><td align="left">'
                        + item[i].description + '</td><td align="center"><input class="check" id="' + item[i].id + '" type="checkbox"></td><tr>';
                }
            }
            $('#tab_items').html(tab);
        }
    });
}

$(document).on('change', ':checkbox', function () {
    var id = $(this).attr('id');
    var check = $('#checkBox').prop('checked');
    if (id ==='checkBox') {
        if (check) {
            showAll();
        } else {
            showToDo();
        }
    } else {
        var setDone = $(this).prop('checked');
        console.log(setDone);
        $.ajax({
            method:"POST",
            url:"/todo/update",
            data:{'id': id, 'setDone':setDone},
            complete:function () {
                if (check) {
                    showAll();
                } else {
                    showToDo();
                }
            }
        });
        return false;
    }
});

$('#add_item').submit(function () {
    var item = $('#editor').val();
    $.ajax({
        method:"POST",
        url:"/todo/add",
        data:{'item': item},
        complete:function (d) {
            jQuery('#add_item')[0].reset();
            var check = $('#checkBox').prop('checked');
            if (check) {
                showAll();
            } else {
                showToDo();
            }
        }
    });
    return false;
});