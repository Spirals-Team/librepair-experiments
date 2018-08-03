var droppedFiles = false;
$(document).ready(function () {
  $('#uploadMediaForm').submit(function (evt) {
    evt.preventDefault();

    var url = "/manager/medias";
    var urlFallBack = "/manager/medias";
    var formData = new FormData($('#uploadMediaForm')[0]);
    if (droppedFiles && droppedFiles.length > 0) {
      for (var i = 0; i < droppedFiles.length; i++) {
        formData = new FormData();
        formData.append("media", droppedFiles[i]);
        upload($('#uploadMediaForm'), $(".loader"), $(".card-loader"), url,
            urlFallBack, formData).done(function (data) {
          handleSuccessPostResult(data, $(".card-loader"), $(".loader"),
              $("#uploadMediaForm"), url)
        }).fail(function (error) {
          handleErrorPostResult($(".loader"), $(".card-loader"),
              $("#uploadMediaForm"));
        });
      }
    } else {
      upload($('#uploadMediaForm'), $(".loader"), $(".card-loader"), url,
          urlFallBack, formData).done(function (data) {
        handleSuccessPostResult(data, $(".card-loader"), $(".loader"),
            $("#uploadMediaForm"), url)
      }).fail(function (error) {
        handleErrorPostResult($(".loader"), $(".card-loader"),
            $("#uploadMediaForm"));
      });
    }

  });
})
