var ajaxUrl = "/ajax/admin/meals/";
var datatableApi;

function updateTable(menuId) {
    $.get(ajaxUrl + "?menuId=" + menuId, updateTableByData);
}

$(function () {
    datatableApi = $("#mealDatatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "price"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            }, {
                "defaultContent": "Vote",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
    makeEditable();
});

function addMeal(menuId) {
    form.find(":input").val("");
    $("#editRow").modal().find('#menuId').val(menuId);
}

function saveMeal(menuId) {
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize()
    }).done(function () {
            $("#editRow").modal("hide");
            updateTable(menuId);
            successNoty("Saved");
        }
    );
}

function updateMealsRow(id, menuId) {
    $.get(ajaxUrl + id + "?menuId=" + menuId, function (data) {

        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
        });

        $('#editRow').modal().find('#menuId').val(menuId);
    });
}

function deleteMeal(mealId, menuId) {
    $.ajax({
        url: ajaxUrl + mealId,
        type: "DELETE",
        data: {"menuId": menuId}
    }).done(function () {
            updateTable(menuId);
            successNoty("Deleted");
        }
    );
}

function redirectToMenus(restId) {
    document.location.href = "menus/?restId=" + restId;
}
