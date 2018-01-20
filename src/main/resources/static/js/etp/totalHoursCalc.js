/*
 * Находим вcе input'ы плана конкретной строки, пересчитываем строки
 * Обновляем total
 */
function totalHoursCalc(e) {
    var idAsArray = e.target.id.split('.')
    // Пример id html элемента: emaModules[0].plan.lectures
    var template = idAsArray.slice(0, idAsArray.length -1).join('.')
    var id = "input[id*='" + template + "']"
    var totalHours = 0.0
    $(id).each(function (index, element) {
        if(!~element.id.indexOf('totalHours')) {
            totalHours += parseFloat(element.value)
        }
    })
    $("input[id='" + template + ".totalHours']").val(totalHours)
}
