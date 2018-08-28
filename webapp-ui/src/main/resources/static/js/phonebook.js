"use strict";

function isConfirmSaveEmployeeByUser(event, form) {
    var message = "Уверены ли вы, что хотите сохранить изменения?";
    bootbox.confirm(message, function (result) {
        if (result) {
            form.submit();
        }
    });
}

function refreshPhonesListener() {
    var phones = $("#phones").find(".remove_phone");
    phones.off();
    phones.on("click", function () {
        var removeBtn = $(this);
        removePhone(removeBtn);
    });
}

function removePhone(removeBtn) {
    var parent = removeBtn.parent();
    parent.remove();
    recalculationIndexes();
}

function recalculationIndexes() {
    $("phoneList[index].number").each(function () {
        var $this = $(this);
        $this.attr("index", $this.attr("index").match(/\[\d+]/g, "[" + index + "]"));
    });
}

$(document).ready(function () {

    $("#saveEmployee").click(function (event) {
        event.preventDefault();
        var form = $("#employeeForm");
        isConfirmSaveEmployeeByUser(event, form);
    });

    $("#searchEmployee").typeahead({
        source: function (query, process) {
            var url = "/ajax/search";
            return $.post(
                url, {
                    query: query
                },
                function (data) {
                    process(data);
                },
                "json");
        },
        minLength: 3
    });

    $("#datepicker").datepicker({
        format: "dd.mm.yyyy",
        language: "ru",
        orientation: "bottom"
    });

    $(document).on("change", ".photo :file", function() {
        var input = this;
        var files = input.get(0).files;
        var length = files ? files.length : 1;
        var label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger("fileselect", [length, label]);
    });

    $("#file").change(function() {
        var files = this.files;
        if (files && files[0]) {
            var fileReader = new FileReader();
            fileReader.onload = function (e) {
                $("#photo").attr("src", e.target.result);
            };
            fileReader.readAsDataURL(files[0]);
        }
    });

    $("#addPhone").click(function () {
        var phoneItems = $(".phoneItem");
        var length = phoneItems.size();
        $("#phones").append("<div class=\"phone_item\">" +
            "<input type=\"text\" class=\"phone\" name=\"phoneList[" + length + "].number\">" +
            "<input type=\"button\" class=\"remove_phone\" value=\"remove\">" +
            "</div>");

        refreshPhonesListener();
    });
});
