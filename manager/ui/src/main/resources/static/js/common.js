var base64Image;
$(document).ready(function () {
  $(".card-loader").hide();
  $(".loader").hide();

  $("#loginForm").submit(function () {
    $(".card-loader").show();
    $(".loader").show();
    $("#loginForm").hide();
  });
  $("#connectToFacebook").submit(function () {
    $(".card-loader").show();
    $(".loader").show();
    $("#connectToFacebook").hide();
  });
  fix_height();
});

$(function () {
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");
  $(document).ajaxSend(function (e, xhr) {
    xhr.setRequestHeader(header, token);
  });
});

function formatDate(date) {
  return date;
}

function displayError(error) {

  var causes = error.causes;
  var message = "";
  if (causes && causes.length > 0) {
    for (var i = 0; i < causes.length; i++) {
      var cause = causes[i];
      message += cause.message + "<br>";
    }
  }

}

function update(formToToggle, loader, cardLoader, url, urlFallBack, dataToSend,
    stay) {
  hideFeedback();
  formToToggle.hide();
  loader.show();
  cardLoader.show();
  var data = JSON.stringify(dataToSend);
  return $.ajax({
    type: "PUT",
    url: url,
    data: data,
    contentType: "application/json; charset=utf-8",
    crossDomain: true,
    dataType: "json"
  });
}

function create(formToToggle, loader, cardLoader, url, urlFallBack,
    dataToSend) {
  hideFeedback();
  formToToggle.hide();
  loader.show();
  cardLoader.show();
  var data = JSON.stringify(dataToSend);
  return $.ajax({
    type: "POST",
    url: url,
    data: data,
    contentType: "application/json; charset=utf-8",
    crossDomain: true,
    dataType: "json"
  });
}

function deleteEntity(formToToggle, loader, cardLoader, url, urlFallBack) {
  hideFeedback();
  formToToggle.hide();
  loader.show();
  cardLoader.show();
  return $.ajax({
    type: "DELETE",
    url: url
  });
}

function deleteAndHandleResult(formToToggle, loader, cardLoader, url,
    urlFallBack) {
  deleteEntity(formToToggle, loader, cardLoader, url, urlFallBack).done(
      function (result) {
        handleDeleteSuccess(urlFallBack);
      }).fail(function (error) {
    handleDeleteError();
  })
}

function upload(formToToggle, loader, cardLoader, url, urlFallBack,
    dataToSend) {
  hideFeedback();
  formToToggle.hide();
  loader.show();
  cardLoader.show();
  var data = dataToSend;
  return $.ajax({
    type: "POST",
    url: url,
    enctype: 'multipart/form-data',
    processData: false,
    data: data,
    contentType: false,
    crossDomain: true,
    cache: false
  });
}

function createThenUpload(formToToggle, loader, cardLoader, url, urlFallBack,
    dataToSend, mediaToSend) {
  formToToggle.hide();
  loader.show();
  cardLoader.show();
  create(formToToggle, loader, cardLoader, url, urlFallBack,
      dataToSend).done(function (result) {
    var urlMedia = "/manager/news/" + result.createdEntityId + "/media";
    upload(formToToggle, loader, cardLoader, urlMedia, urlFallBack,
        mediaToSend).done(function (data) {
      handleSuccessPostResult(data, cardLoader, loader, formToToggle, url);
    }).fail(function (error) {
      handleErrorPostResult(loader, cardLoader, formToToggle);
    });
  }).fail(function (error) {
    handleErrorPostResult(loader, cardLoader, formToToggle
    );
  });

}

function updateThenUpload(formToToggle, loader, cardLoader, url, urlFallBack,
    dataToSend, mediaToSend) {
  formToToggle.hide();
  loader.show();
  cardLoader.show();
  update(formToToggle, loader, cardLoader, url, urlFallBack,
      dataToSend).done(function (result) {
    var urlMedia = "/manager/news/" + result.createdEntityId + "/media";
    upload(formToToggle, loader, cardLoader, urlMedia, urlFallBack,
        mediaToSend).done(function (data) {
      handleSuccessPostResult(data, cardLoader, loader, formToToggle,
          urlFallBack);
    }).fail(function (error) {
      handleErrorPostResult(loader, cardLoader,
          formToToggle);
    });
  }).fail(function (error) {
    handleErrorPutResult(loader, cardLoader, formToToggle);
  });

}

function handleSuccessPostResult(data, cardLoader, loader, formToToggle, url) {
  cardLoader.hide();
  window.location.href = url;
}

function handleErrorPostResult(loader, cardLoader, form) {
  loader.hide();
  cardLoader.hide();
  form.show();
}

function handleSuccessPutResult(data, cardLoader, loader, formToToggle,
    urlFallBack, stay) {
  cardLoader.hide();

  if (!stay) {
    setTimeout(function () {
      window.location.href = urlFallBack;
    }, 600);

  }
  loader.hide();
  formToToggle.show();

}

function handleErrorPutResult(loader, cardLoader, form) {
  loader.hide();
  cardLoader.hide();
  form.show();
}

function fix_height() {
  $('#page-wrapper').css("min-height", $(window).height() - 60 + "px");
}

function handleDeleteSuccess(urlFallBack) {
  window.location.href = urlFallBack;
}

function handleDeleteError(error, form, cardLoader, loader) {
  $.notify({message: "INTERNAL SERVER ERROR"}, {type: 'danger'});
  formToToggle.show();
  loader.hide();
  cardLoader.hide();
}

function hideFeedback() {
  $(":input").each(function () {
    if ($(this).hasClass("is-invalid")) {
      $(this).removeClass("is-invalid");
    }
  });
}

function displayFeedBack(errors) {
  for (var errorIndex in errors) {
    var currentError = errors[errorIndex];
    var errorField = currentError.field;
    var errorMessage = currentError.defaultMessage;
    var capitalizedErrorField = errorField.charAt(0).toUpperCase()
        + errorField.substring(1);

    var formId = "#formInput" + capitalizedErrorField;
    var formInputId = "#" + errorField;
    var feedBackId = "#feedBack" + capitalizedErrorField;
    var feedBackDiv = $(feedBackId);
    if (feedBackDiv.length > 0) {
      feedBackDiv.html(errorMessage);
    } else {
      var feedBackDiv = '<div class="invalid-feedback" id="'
          + feedBackId.substring(1) + '">'
          + errorMessage
          + '</div>';
      $(formId).append(feedBackDiv);
    }
    $(formInputId).addClass("is-invalid");
  }
}