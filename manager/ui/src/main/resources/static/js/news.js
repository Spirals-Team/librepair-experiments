function computeNewsEntry() {
  var newsEntry = {};

  var inputTitle = $("#title");
  var inputAuthor = $("#author");
  var inputTags = $("#tags");

  newsEntry.title = inputTitle.val();
  newsEntry.author = inputAuthor.val();
  newsEntry.tags = inputTags.val();
  var content = computeNewsContent();
  if (hasContent(content)) {
    newsEntry.content = content;
  }
  var image = computeNewsImage();
  if (hasImageMetaData(image)) {
    newsEntry.image = image;
  }

  return newsEntry;

}

function hasContent(content) {
  return content.content;
}

function hasImageMetaData(image) {
  return image.alt || image.legend || image.width || image.height;
}

function computeNewsContent() {
  var content = {};
  content.content = CKEDITOR.instances.newsContent.getData();
  return content;
}

function computeNewsImage() {
  var image = {};

  var inputLegend = $("#image\\.legend");
  var inputAlt = $("#image\\.alt");

  image.alt = inputAlt.val();
  image.legend = inputLegend.val();

  return image;
}

function cancelUpdateNews() {
  window.location.href = "/manager/news";
}

function computeNewsEntryUpdate() {
  var newsEntry = {};

  var inputTitle = $("#title");
  var inputAuthor = $("#author");
  var inputTags = $("#tags");
  var inputId = $("#id");
  var inputCreationDate = $("#creationDate");
  var inputModificationDate = $("#modificationDate");
  var inputCreationUser = $("#creationUser");
  var inputModificationUser = $("#modificationUser");

  newsEntry.title = inputTitle.val();
  newsEntry.author = inputAuthor.val();
  newsEntry.tags = inputTags.val();
  newsEntry.id = inputId.val();
  newsEntry.creationDate = formatDate(inputCreationDate.val());
  newsEntry.modificationDate = formatDate(inputModificationDate.val());
  newsEntry.creationUser = inputCreationUser.val();
  newsEntry.modificationUser = inputModificationUser.val();

  var content = computeNewsContentUpdate(false);
  if (hasContent(content)) {
    newsEntry.content = content;
  }
  var image = computeNewsImageUpdate(false);
  if (hasImageMetaData(image)) {
    newsEntry.image = image;
  }
  return newsEntry;

}

function computeNewsContentUpdate(contentOnly) {
  var content = {};

  var inputId = contentOnly ? $("#id") : $("#content\\.id");
  var inputCreationDate = contentOnly ? $("#creationDate") : $(
      "#content\\.creationDate");
  var inputModificationDate = contentOnly ? $("#modificationDate")
      : $("#content\\.modificationDate");
  var inputCreationUser = contentOnly ? $("#creationUser") : $(
      "#content\\.creationUser");
  var inputModificationUser = contentOnly ? $("#modificationUser") : $(
      "#content\\.modificationUser");
  var contentInput = contentOnly ? $(
      "#content") : $(
      "#content\\.content");

  var contentEditor = CKEDITOR.instances.newsContent;
  if (contentEditor) {
    content.content = contentEditor.getData();
  } else {
    content.content = contentInput.val();
  }

  if (inputId.val()) {
    content.id = inputId.val();
  }
  if (inputCreationDate.val()) {
    content.creationDate = formatDate(inputCreationDate.val());
  }
  if (inputModificationDate.val()) {
    content.modificationDate = formatDate(inputModificationDate.val());
  }
  if (inputCreationUser.val()) {
    content.creationUser = inputCreationUser.val();
  }
  if (inputModificationUser.val()) {
    content.modificationDate = inputModificationUser.val();
  }
  return content;
}

function computeNewsImageUpdate(imageOnly) {
  var image = {};

  var inputLegend = imageOnly ? $("#legend") : $("#image\\.legend");
  var inputAlt = imageOnly ? $("#alt") : $("#image\\.alt");
  var inputId = imageOnly ? $("#id") : $("#image\\.id");
  var inputCreationDate = imageOnly ? $("#creationDate") : $(
      "#image\\.creationDate");
  var inputModificationDate = imageOnly ? $("#modificationDate") : $(
      "#image\\.modificationDate");
  var inputCreationUser = imageOnly ? $("#creationUser") : $(
      "#image\\.creationUser");
  var inputModificationUser = imageOnly ? $("#modificationUser") : $(
      "#image\\.modificationUser");
  var inputMediaId = imageOnly ? $("#media\\.id") : $("#image\\.media\\.id");

  image.alt = inputAlt.val();
  image.legend = inputLegend.val();
  if (inputId.val()) {
    image.id = inputId.val();
  }
  if (inputCreationDate.val()) {
    image.creationDate = formatDate(inputCreationDate.val());
  }
  if (inputModificationDate.val()) {
    image.modificationDate = formatDate(inputModificationDate.val());
  }
  if (inputCreationUser.val()) {
    image.creationUser = inputCreationUser.val();
  }
  if (inputModificationUser.val()) {
    image.modificationUser = inputModificationUser.val();
  }

  var media = {};
  media.id = inputMediaId.val();
  image.media = media;
  return image;
}

function postCreateNewsForm() {
  $("#newsEntryCreateForm").hide();
  var url = "/manager/news";

  if (hasMediaToUpload()) {
    var formData = new FormData();
    if (droppedFiles && droppedFiles[0]) {
      formData.append("media", droppedFiles[0]);
    } else {
      formData.append("media", $("#newsEntryCreateForm")[0][4].files[0]);
    }

    createThenUpload($("#newsEntryCreateForm"), $(".loader"), $(".card-loader"),
        url, url,
        computeNewsEntry(), formData);
  } else {
    create($("#newsEntryCreateForm"), $(".loader"), $(".card-loader"), url, url,
        computeNewsEntry()).done(function (data) {
      handleSuccessPostResult(data, $(".card-loader"), $(".loader"),
          $("#newsEntryCreateForm"), url)
    }).fail(function (error) {
      handleErrorPostResult($(".loader"), $(".card-loader"),
          $("#newsEntryCreateForm"));
    });
  }

}

function postUpdateNewsEntryForm() {
  var newsEntryToUpdate = computeNewsEntryUpdate();
  var url = "/manager/news/" + newsEntryToUpdate.id;
  var urlFallback = "/manager/news/" + newsEntryToUpdate.id;

  if (hasMediaToUpload()) {
    var formData = new FormData();
    if (droppedFiles && droppedFiles[0]) {
      formData.append("media", droppedFiles[0]);
    } else {
      formData.append("media", $("#newsEntryEditForm")[0][4].files[0]);
    }

    updateThenUpload($("#newsEntryEditForm"), $(".loader"), $(".card-loader"),
        url, urlFallback,
        newsEntryToUpdate, formData);
  } else {
    update($("#newsEntryEditForm"), $(".loader"), $(".card-loader"), url,
        urlFallback, newsEntryToUpdate, true).done(function (data) {
      handleSuccessPutResult(data, $(".card-loader"), $(".loader"),
          $("#newsEntryEditForm"), url, true)
    }).fail(function (error) {
      handleErrorPutResult($(".loader"), $(".card-loader"),
          $("#newsEntryEditForm"));
    });
  }

}

function postUpdateNewsContentForm() {
  var newsEntryId = $("#newsEntryId").val();
  var newsContentToUpdate = computeNewsContent(true);
  var url = "/manager/news/" + newsEntryId + "/content";
  var urlFallback = "/manager/news/" + newsEntryId;

  update($("#newsEntryEditForm"), $(".loader"), $(".card-loader"), url,
      urlFallback, newsContentToUpdate, true).done(function (data) {
    handleSuccessPutResult(data, $(".card-loader"), $(".loader"),
        $("#newsEntryEditForm"), url, true)
  }).fail(function (error) {
    handleErrorPutResult($(".loader"), $(".card-loader"),
        $("#newsEntryEditForm"));
  });

}

function postUpdateNewsImageForm() {
  var newsEntryId = $("#newsEntryId").val();
  var newsEntryToUpdate = computeNewsImageUpdate(true);
  var url = "/manager/news/" + newsEntryId + "/image";
  var urlFallback = "/manager/news/" + newsEntryId;

  if (hasMediaToUpload()) {
    var formData = new FormData();
    if (droppedFiles && droppedFiles[0]) {
      formData.append("media", droppedFiles[0]);
    } else {
      formData.append("media", $("#imageMedia")[0].files[0]);
    }

    updateThenUpload($("#newsEntryEditForm"), $(".loader"), $(".card-loader"),
        url, urlFallback,
        newsEntryToUpdate, formData);
  } else {
    update($("#newsEntryEditForm"), $(".loader"), $(".card-loader"), url,
        urlFallback, newsEntryToUpdate, true).done(function (data) {
      handleSuccessPutResult(data, $(".card-loader"), $(".loader"),
          $("#newsEntryEditForm"), url, true)
    }).fail(function (error) {
      handleErrorPutResult($(".loader"), $(".card-loader"),
          $("#newsEntryEditForm"));
    });
  }

}

function hasMediaToUpload() {
  return (droppedFiles && droppedFiles[0]) || ($(
      "#imageMedia")[0] && $(
      "#imageMedia")[0].files && $(
      "#imageMedia")[0].files[0]);
}



