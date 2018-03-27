$(document).ready(function () {
    $('#aaa').load("aaa.html");
    $(function() {
        $(".any-class").jCarouselLite({
            btnNext: ".next",
            btnPrev: ".prev",
            vertical:true,
            auto:3000,
            speed:500
        });
    });
});
