function cancelUpdateStyle() {
  window.location.href = "/manager/styles";
}

function validateAndUpdateStyle() {
  var style = computeUpdateStyle();
  return style;
}

function computeStyleToCreate() {
  var style = {};

  var inputName = $("#name");

  style.name = inputName.val();
  style.content = computeStyleContent();

  return style;

}

function computeUpdateStyle() {
  var style = {};

  var inputId = $("#id");
  var inputName = $("#name");
  var inputCreationDate = $("#creationDate");
  var inputModificationDate = $("#modificationDate");
  var inputMediaName = $("#mediaName");
  var inputMediaId = $("#mediaId");
  var inputCreationUser = $("#creationUser");
  var inputModificationUser = $("#modificationUser");

  style.id = inputId.val();
  style.content = computeStyleContent();
  style.name = inputName.val();
  style.mediaName = inputMediaName.val();
  style.mediaId = inputMediaId.val();
  style.creationUser = inputCreationUser.val();
  style.modificationUser = inputModificationUser.val();
  style.creationDate = inputCreationDate.val();
  style.modificationDate = inputModificationDate.val();
  return style;

}

function computeStyleContent() {
  if (codeMirrorStyleContent) {
    return codeMirrorStyleContent.getValue();
  }
  return $("#styleContent").val();
}

function postUpdateStyleForm() {
  var styleToUpdate = validateAndUpdateStyle();
  var url = "/manager/styles/_edit";
  var urlFallback = "/manager/styles";
  update($("#styleUpdateForm"), $(".loader"), $(".card-loader"), url,
      urlFallback, styleToUpdate).done(function (data) {
    handleSuccessPutResult(data, $(".card-loader"), $(".loader"),
        $("#styleUpdateForm"), url, true)
  }).fail(function (error) {
    handleErrorPutResult($(".loader"), $(".card-loader"),
        $("#styleUpdateForm"));
  });
}

function postCreateStyleForm() {
  var styleToCreate = computeStyleToCreate();
  var url = "/manager/styles/";
  var urlFallback = "/manager/styles/";
  create($("#styleCreateForm"), $(".loader"), $(".card-loader"), url,
      urlFallback, styleToCreate).done(function (data) {
    handleSuccessPostResult(data, $(".card-loader"), $(".loader"),
        $("#styleCreateForm"), url);
  }).fail(function (error) {
    handleErrorPostResult($(".loader"), $(".card-loader"),
        $("#styleCreateForm"));
  });
}