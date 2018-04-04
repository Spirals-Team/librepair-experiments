jQuery(document).ready(function($) {
    $("#loForm").submit(function(event) {

        // Prevent the form from submitting via the browser.
        event.preventDefault();
        searchAjax();

    });
});

function searchAjax() {
    var data = {};
    data["year"] = $("#year").val();
    data["program"] = $("#program").val();

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "/displayCourseForProgram",
        data : JSON.stringify(data),
        dataType : 'json',
        timeout : 100000,
        success : function(data) {
            console.log("SUCCESS: ", data);
            $("#success").append("SUCCESS: Filtered - ", JSON.stringify(data.name));
        },
        error : function(e) {
            console.log("ERROR: ", e);
        },
        done : function(e) {
            console.log("DONE");
        }
    });
}