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
    var urlMedia = "/manager/news/media/" + result.createdEntityId;
    upload(formToToggle, loader, cardLoader, urlMedia, urlFallBack,
        mediaToSend).done(function (data) {
      handleSuccessPostResult(data, cardLoader, loader, formToToggle, url);
    }).fail(function (error) {
      handleErrorPostResult(urlFallBack);
    });
  }).fail(function (error) {
    handleErrorPostResult(urlFallBack);
  });

}

function updateThenUpload(formToToggle, loader, cardLoader, url, urlFallBack,
    dataToSend, mediaToSend) {
  formToToggle.hide();
  loader.show();
  cardLoader.show();
  update(formToToggle, loader, cardLoader, url, urlFallBack,
      dataToSend).done(function (result) {
    var urlMedia = "/manager/news/media/" + result.createdEntityId;
    upload(formToToggle, loader, cardLoader, urlMedia, urlFallBack,
        mediaToSend).done(function (data) {
      handleSuccessPostResult(data, cardLoader, loader, formToToggle, url);
    }).fail(function (error) {
      handleErrorPostResult(urlFallBack);
    });
  }).fail(function (error) {
    handleErrorPutResult(urlFallBack);
  });

}

function handleSuccessPostResult(data, cardLoader, loader, formToToggle, url) {
  cardLoader.hide();
  if (data.error) {
    loader.hide();
    formToToggle.show();
    displayError(data.error);
  } else {
    window.location.href = url;
  }
}

function handleErrorPostResult(urlFallbak) {
  window.location.href = urlFallbak;
}

function handleSuccessPutResult(data, cardLoader, loader, formToToggle,
    urlFallBack, stay) {
  cardLoader.hide();
  if (data.error) {
    loader.hide();
    formToToggle.show();
    displayError(data.error);
  } else {
    if (!stay) {
      setTimeout(function () {
        window.location.href = urlFallBack;
      }, 600);

    }
    loader.hide();
    formToToggle.show();
  }
}

function handleErrorPutResult(urlFallBack) {
  window.location.href = urlFallBack;
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