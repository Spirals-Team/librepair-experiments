function setUpDragAndDrop(imageSrcId) {
  var holder = $(".drop_zone");
  setUpDragOver(holder);
  setUpDragLeave(holder);
  setUpDropImage(imageSrcId, holder);
}

function setUpDragAndDropForMedia(mediaSrcId) {
  var holder = $(".drop_zone");
  setUpDragOver(holder);
  setUpDragLeave(holder);
  setUpDropMedia(mediaSrcId, holder);
}

function setUpDragOver(holder) {
  holder.on("dragover", function (event) {
    event.preventDefault();
    event.stopPropagation();
    $(this).addClass("dropping");
  });
}

function setUpDragLeave(holder) {
  holder.on("dragleave", function (event) {
    event.preventDefault();
    event.stopPropagation();
    $(this).removeClass("dropping");
  });
}

function setUpDropImage(imageSrcId, holder) {
  holder.on("drop", function (event) {
    event.preventDefault();
    event.dataTransfer = event.originalEvent.dataTransfer;
    droppedFiles = event.dataTransfer.files;
    var file = droppedFiles[0];
    var reader = new FileReader();
    reader.onload = function (eventOnload) {
      $(imageSrcId).show();
      $(imageSrcId).attr('src', eventOnload.target.result);
      $(imageSrcId).hide();
      previewFile();
    }
    reader.readAsDataURL(file);
    $(this).removeClass("dropping");
  });
}

function setUpDropMedia(mediaSrcId, holder) {
  holder.on("drop", function (event) {
    event.preventDefault();
    event.dataTransfer = event.originalEvent.dataTransfer;
    droppedFiles = event.originalEvent.dataTransfer.files;

    for (var index = 0; index < droppedFiles.length; index++) {
      (function (index) {
        var file = droppedFiles[index];
        var reader = new FileReader();
        reader.onload = function (eventOnload) {
          $(mediaSrcId).show();
          $(mediaSrcId).attr('src', eventOnload.target.result);
          $(mediaSrcId).hide();
          previewFileMedia(index);
        }
        reader.readAsDataURL(file);
      })(index);

    }

    $(this).removeClass("dropping");
  });
}

function previewFile() {
  var preview = document.querySelector('#imagePreview');
  var inputFile = document.querySelector('input[type=file]');
  var file = inputFile.files[0];
  var src = inputFile.src;
  var reader = new FileReader();

  reader.addEventListener("load", function () {
    preview.src = reader.result;
    base64Image = reader.result;
  }, false);

  if (file) {
    reader.readAsDataURL(file);
  } else if (src) {
    var blob = createBlobFromBase64Image(src);
    reader.readAsDataURL(blob);
  }
}

function previewFileMedia(index) {
  var preview;
  if (index == 0) {
    preview = document.querySelector('#imagePreview');
  } else {
    $("#formPreviewImage").append("<img class='img-fluid mx-auto d-block' src='' id='imagePreview_"
        + index + "'/>")
    preview = document.querySelector('#imagePreview_' + index);
  }
  var inputFile = document.querySelector('input[type=file]');
  var file = inputFile.files[index];
  var src = inputFile.src;
  var reader = new FileReader();

  reader.addEventListener("load", function () {
    preview.src = reader.result;
  }, false);

  if (file) {
    reader.readAsDataURL(file);
  } else if (src) {
    var blob = createBlobFromBase64Image(src);
    reader.readAsDataURL(blob);
  }
}

function createBlobFromBase64Image(base64Image) {
  var splittedSrc = base64Image.split(",");
  var header = splittedSrc[0];
  var base64 = splittedSrc[1];
  var byteCharacters = atob(base64);
  var byteNumbers = new Array(byteCharacters.length);
  for (var i = 0; i < byteCharacters.length; i++) {
    byteNumbers[i] = byteCharacters.charCodeAt(i);
  }
  var byteArray = new Uint8Array(byteNumbers);
  var contentType = header.split(":")[1].split(";")[0];
  return new Blob([byteArray], {type: contentType});

}