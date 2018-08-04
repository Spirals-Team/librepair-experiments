/**
 * checks whether the current field equals a current sequence condition
 * @param sequence a sequence condition
 * @param id an id of the current field
 * @returns {boolean} true if the current field isn't empty,
 * otherwise - false
 */
function isEmpty(id) {
    var value = $(id);
    var result = false;
    if (!value.val()) {
        result = true;
        alert(value.attr('name').concat(' is empty'));
    }
    return result;
}

/**
 * checks whether the current email is valid
 * @param id an id of the current field
 * @returns {boolean} true if an email is valid,
 * otherwise - false
 */
function isEmailValid(id) {
    var reg = /^([A-Za-z0-9_\-.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    var mail = $(id).val();
    var result = true;
    if(!reg.test(mail)) {
        result = false;
        alert('An invalid e-mail');
    }
    return result;
}

/**
 * checks whether the current password is valid
 * @param id an id of the current field
 * @returns {boolean} true if an password is valid,
 * otherwise - false
 */
function isPasswordValid(id) {
    var min = 3; //a min length of a password
    var length = $(id).val().length;
    var result = true;
    if (length < min) {
        result = false;
        alert('A length of password has to have at least 3 symbols');
    }
    return result;
}

/**
 * if all fields are valid then submit
 * a form to the server
 * @param t a listner of submit button
 */
function validFields(t) {
    if (!isEmpty('#name')
        & !isEmpty('#login')
        & isEmailValid('#email')
        & isPasswordValid('#password')
        & !isEmpty('#states')) {
        t.submit();
    }
}

/**
 * Fills the <code>select</code> of cities getting
 * an ajax get query to the server
 */
function fillCities(param) {
    $.ajax({
        url: '/jsonCity',
        method: 'GET',
        data: "stateId=" + param,
        complete: function(response) {
            var result = "";
            var cities = JSON.parse(response.responseText);
            for (var i = 0; i < cities.length; i++) {
                var city = cities[i];
                result +=
                    "<option value=\"" + city.id + "\">" + city.name + "</option>";
            }
            var table = document.getElementById("cities");
            table.innerHTML = result;
        }
    });
}