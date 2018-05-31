function cancelUpdateUser() {
  window.location.href = "/manager/users";
}

function cancelCreateUser() {
  window.location.href = "/manager/users";
}

function validateAndUpdateUser() {
  var user = computeUserToUpdate();
  return user;
}

function validateAndCreateUser() {
  var user = computeUserToCreate();
  return user;
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
  var userToUpdate = validateAndUpdateUser();
  var url = "/manager/users/" + userToUpdate.id;
  var urlFallback = "/manager/users/" + userToUpdate.id;
  update($("#userUpdateForm"), $(".loader"), $(".card-loader"), url,
      urlFallback,
      userToUpdate).done(function (data) {
    handleSuccessPutResult(data, $(".card-loader"), $(".loader"),
        $("#userUpdateForm"), url)
  }).fail(function (error) {
    handleErrorPutResult(urlFallback);
  });
}

function postCreateUserForm() {
  var userToCreate = validateAndCreateUser();
  var url = "/manager/users/";
  var urlFallback = "/manager/users/";
  create($("#userCreateForm"), $(".loader"), $(".card-loader"), url,
      urlFallback, userToCreate).done(function (data) {
    handleSuccessPostResult(data, $(".card-loader"), $(".loader"),
        $("#userCreateForm"), url)
  }).fail(function (error) {
    handleErrorPostResult(urlFallback);
  });
}