$(function () {
    $("input[id*='plan']").each(function (index, element) {
        element.onchange = totalHoursCalc
    })

    $("input[id*='plan']").inputmask('decimal', {
        digits: 2,
        allowMinus: false,
        placeholder: "0.0"
    })
});