jQuery(document).ready(function($) {
    $("#newCourseForm").submit(function(event) {

        // Prevent the form from submitting via the browser.
        event.preventDefault();
        searchAjax();

    });
});

function searchAjax() {
    var data = {};
    data["name"] = $("#name").val();
    data["description"] = $("#description").val();

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "/courses",
        data : JSON.stringify(data),
        dataType : 'json',
        timeout : 100000,
        success : function(data) {
            console.log("SUCCESS: ", data);
            $("#success").append("SUCCESS: Courses - ", JSON.stringify(data.name), " added   ");
        },
        error : function(e) {
            console.log("ERROR: ", e);
        },
        done : function(e) {
            console.log("DONE");
        }
    });
}