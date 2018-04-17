$(document).on("click", "#sign_up", function () {
    $("#sign_up_modal").modal("show");
});

$(document).on("click", "#register", function () {
    var name = document.getElementById('new_name').value;
    var email = document.getElementById('new_email').value;
    var password = document.getElementById('new_pass').value;
    var url = "/home?name=" + name + "&email=" + email + "&password=" + password;
    $.post(url, function (id) {
        if (id === 'null'){
            alert("Error while signing up. Try another name or password.");
        } else {
            alert("Signed up successful! Your id: " + id);
            window.location.href = "/user/" + id;
        }
    });
});

// $(document).on("click", "#log_in", function () {
//     var name = document.getElementById("name").value;
//     var password = document.getElementById("pass").value;
//     var url = "/login?name=" + name + "&password" + password;
//
// });

function showPass(){
    var x = document.getElementById("pass");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}

