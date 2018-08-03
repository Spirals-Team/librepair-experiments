function cancelUpdateCarousel() {
  window.location.href = "/manager/carousels";
}

function cancelCreateCarousel() {
  window.location.href = "/manager/carousels";
}

function computeCarouselToUpdate() {
  var carousel = {};

  var inputName = $("#name");
  var inputId = $("#id");
  var inputCreationDate = $("#creationDate");
  var inputModificationDate = $("#modificationDate");
  var inputCreationUser = $("#creationUser");
  var inputModificationUser = $("#modificationUser");

  carousel.name = inputName.val();
  ;
  carousel.id = inputId.val();
  carousel.creationDate = formatDate(inputCreationDate.val());
  carousel.modificationDate = formatDate(inputModificationDate.val());
  carousel.creationUser = inputCreationUser.val();
  carousel.modificationUser = inputModificationUser.val();

  return carousel;

}

function computeCarouselToCreate() {
  var carousel = {};

  var inputName = $("#name");

  carousel.name = inputName.val();

  return carousel;

}

function postUpdateCarouselForm() {
  var carouselToUpdate = computeCarouselToUpdate();
  var url = "/manager/carousels/" + carouselToUpdate.id;
  var urlFallback = "/manager/carousels/" + carouselToUpdate.id;
  update($("#carouselEditForm"), $(".loader"), $(".card-loader"), url,
      urlFallback, carouselToUpdate, true);
}

function postCreateCarouselForm() {
  var carouselToCreate = computeCarouselToCreate();
  var url = "/manager/carousels/";
  var urlFallback = "/manager/carousels/";
  create($("#carouselCreateForm"), $(".loader"), $(".card-loader"), url,
      urlFallback, carouselToCreate).done(function (data) {
    handleSuccessPostResult(data, $(".card-loader"), $(".loader"),
        $("#carouselCreateForm"), url)
  }).fail(function (error) {
    handleErrorPostResult($(".loader"), $(".card-loader"),
        $("#carouselCreateForm"));
  });
}

