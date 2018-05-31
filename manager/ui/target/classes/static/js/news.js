function validateAndCreateNewsEntry() {
  var newsEntry = computeNewsEntry();
  return newsEntry;
}

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

function validateAndUpdateNewsEntry() {
  var newsEntry = computeNewsEntryUpdate();
  return newsEntry;
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

  var content = computeNewsContentUpdate();
  if (hasContent(content)) {
    newsEntry.content = content;
  }
  var image = computeNewsImageUpdate();
  if (hasImageMetaData(image)) {
    newsEntry.image = image;
  }
  return newsEntry;

}

function computeNewsContentUpdate() {
  var content = {};

  var inputId = $("#content\\.id");
  var inputCreationDate = $("#content\\.creationDate");
  var inputModificationDate = $("#content\\.modificationDate");
  var inputCreationUser = $("#content\\.creationUser");
  var inputModificationUser = $("#content\\.modificationUser");

  content.content = CKEDITOR.instances.newsContent.getData();
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

function computeNewsImageUpdate() {
  var image = {};

  var inputLegend = $("#image\\.legend");
  var inputAlt = $("#image\\.alt");
  var inputId = $("#image\\.id");
  var inputCreationDate = $("#image\\.creationDate");
  var inputModificationDate = $("#image\\.modificationDate");
  var inputCreationUser = $("#image\\.creationUser");
  var inputModificationUser = $("#image\\.modificationUser");

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
        validateAndCreateNewsEntry(), formData);
  } else {
    create($("#newsEntryCreateForm"), $(".loader"), $(".card-loader"), url, url,
        validateAndCreateNewsEntry()).done(function (data) {
      handleSuccessPostResult(data, $(".card-loader"), $(".loader"),
          $("#newsEntryCreateForm"), url)
    }).fail(function (error) {
      handleErrorPostResult(urlFallback);
    });
  }

}

function postUpdateNewsForm() {
  var newsEntryToUpdate = validateAndUpdateNewsEntry();
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
      handleErrorPutResult(urlFallback);
    });
  }

}

function hasMediaToUpload() {
  return (droppedFiles && droppedFiles[0]) || ($(
      "#newsEntryEditForm")[0] && $(
      "#newsEntryEditForm")[0][4] && $(
      "#newsEntryEditForm")[0][4].files && $(
      "#newsEntryEditForm")[0][4].files[0]);
}



