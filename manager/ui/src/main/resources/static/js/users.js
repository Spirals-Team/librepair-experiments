function cancelUpdateUser() {
  window.location.href = "/manager/users";
}

function cancelCreateUser() {
  window.location.href = "/manager/users";
}

function computeUserToUpdate() {
  var user = {};

  var inputLogin = $("#login");
  var inputEmail = $("#email");
  var inputLastConnection = $("#lastConnection");
  var inputId = $("#id");
  var inputCreationDate = $("#creationDate");
  var inputModificationDate = $("#modificationDate");
  var inputCreationUser = $("#creationUser");
  var inputModificationUser = $("#modificationUser");

  user.login = inputLogin.val();
  user.description = computeUserDescription();
  user.email = inputEmail.val();
  user.lastConnection = inputLastConnection.val();
  user.id = inputId.val();
  user.creationDate = formatDate(inputCreationDate.val());
  user.modificationDate = formatDate(inputModificationDate.val());
  user.creationUser = inputCreationUser.val();
  user.modificationUser = inputModificationUser.val();

  return user;

}

function computeUserToCreate() {
  var user = {};

  var inputLogin = $("#login");
  var inputEmail = $("#email");

  user.login = inputLogin.val();
  user.email = inputEmail.val();
  user.description = computeUserDescription();

  return user;

}

function computeUserDescription() {
  var description = "";
  description = CKEDITOR.instances.description.getData();
  return description;
}

function postUpdateUserForm() {
  var userToUpdate = computeUserToUpdate();
  var url = "/manager/users/" + userToUpdate.id;
  var urlFallback = "/manager/users/" + userToUpdate.id;
  update($("#userUpdateForm"), $(".loader"), $(".card-loader"), url,
      urlFallback,
      userToUpdate).done(function (data) {
    handleSuccessPutResult(data, $(".card-loader"), $(".loader"),
        $("#userUpdateForm"), url, true)
  }).fail(function (error) {
    $(".loader").hide();
    $(".card-loader").hide();
    $("#userUpdateForm").show();
  });
}

function postCreateUserForm() {
  var userToCreate = computeUserToCreate();
  var url = "/manager/users/";
  var urlFallback = "/manager/users/";
  create($("#userCreateForm"), $(".loader"), $(".card-loader"), url,
      urlFallback, userToCreate).done(function (data) {
    handleSuccessPostResult(data, $(".card-loader"), $(".loader"),
        $("#userCreateForm"), url)
  }).fail(function (error) {
    $(".loader").hide();
    $(".card-loader").hide();
    $("#userCreateForm").show();
  });
}