<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <title>User's List</title>
</head>
<body>
<c:if test="${(error != '' && error !=null)}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <strong><c:out value="${error}"/>!</strong>
        <button type="button" class="close" data-dismiss="alert"
                aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</c:if>

<nav class="navbar navbar-light bg-light navbar-expand-lg">
    <a href="#" class="navbar-brand">Hello, <c:out value="${email}"/></a>
    <button class="navbar-toggler" data-toggle="collapse"
            data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav ml-auto">
            <c:if test="${role !='MANDATOR'}">
                <li class="navbar-item">
                    <a href="${pageContext.servletContext.contextPath}/add"
                       class="nav-link">Add new user</a>
                </li>
            </c:if>
            <li class="navbar-item">
                <form action="${pageContext.servletContext.contextPath}/signout"
                      method="post">
                    <input type="submit" value="Sign out"
                           class="nav-link btn btn-link">
                </form>
            </li>
        </ul>
    </div>
</nav>

<ul style="list-style-type: none;">
    <c:forEach items="${users}" var="user">
        <li>
            <fieldset <c:if test="${role == 'USER'}">disabled</c:if>>
                <form action="${pageContext.servletContext.contextPath}/update"
                      method="post" class="needs-validation p-3" novalidate>
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label
                                for="<c:out value="email-${user.id}"/>">Email: </label>
                            <input type="text" name="email"
                                   id="<c:out value="email-${user.id}"/>"
                                   value="<c:out value="${user.email}"/>"
                                   <c:if
                                       test="${(user.email != email && role != 'ADMIN')}">disabled</c:if>
                                   class="form-control"
                                   placeholder="mark@email.com"
                                   required autocomplete="off">
                            <div class="valid-feedback">
                                Looks good.
                            </div>
                            <div class="invalid-feedback">
                                Please provide an email.
                            </div>
                        </div>
                        <div class="form-group col-md-3">
                            <label
                                for="<c:out value="passw-${user.id}"/>">Password: </label>
                            <input type="password" name="passw"
                                   id="<c:out value="passw-${user.id}"/>"
                                   value="<c:out value=""/>"
                                   <c:if
                                       test="${(user.email != email && role != 'ADMIN')}">disabled</c:if>
                                   class="form-control" placeholder="*****"
                                   required autocomplete="off">
                            <div class="valid-feedback">
                                Looks good.
                            </div>
                            <div class="invalid-feedback">
                                Please provide a password.
                            </div>
                        </div>
                        <div class="form-group col-md-3">
                            <label
                                for="<c:out value="role-${user.id}"/>">Role: </label>
                            <select name="role"
                                    id="<c:out value="role-${user.id}"/>"
                                    <c:if
                                        test="${role != 'ADMIN'}">disabled</c:if>
                                    class="form-control">
                                <c:forEach items="${roles}" var="r">
                                    <option value="${r.type}"
                                            <c:if
                                                test="${r.type == user.role.type}">selected
                                        <c:set var="selectedRole"
                                               scope="session"
                                               value="${r.type}"/>
                                    </c:if>
                                    >
                                        <c:out value="${r.type}"/>
                                    </option>
                                </c:forEach>
                            </select>
                            <c:if test="${role != 'ADMIN'}">
                                <input type="hidden" name="role"
                                       value="<c:out value = "${selectedRole}"/>"/>
                            </c:if>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="usergenre">Music Genres: </label>
                            <select name="genres" id="usergenre"
                                    class="form-control"
                                    <c:if
                                        test="${(user.email != email && role != 'ADMIN')}">disabled</c:if>
                                    multiple>
                                <c:forEach items="${genres}" var="g">
                                    <option value="${g.genre}"
                                            <c:if
                                                test="${user.genres.contains(g)}">selected</c:if>
                                    >
                                        <c:out value="${g.genre}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="<c:out value="country-${user.id}"/>">Country: </label>
                            <input type="text" name="country"
                                   id="<c:out value="country-${user.id}"/>"
                                   value="<c:out value="${user.address.country}"/>"
                                   <c:if
                                       test="${(user.email != email && role != 'ADMIN')}">disabled</c:if>
                                   class="form-control" placeholder="Country..."
                                   required autocomplete="off">
                            <div class="valid-feedback">
                                Looks good.
                            </div>
                            <div class="invalid-feedback">
                                Please provide an country.
                            </div>
                        </div>
                        <div class="form-group col-md-3">
                            <label
                                for="<c:out value="city-${user.id}"/>">City: </label>
                            <input type="text" name="city"
                                   id="<c:out value="city-${user.id}"/>"
                                   value="<c:out value="${user.address.city}"/>"
                                   <c:if
                                       test="${(user.email != email && role != 'ADMIN')}">disabled</c:if>
                                   class="form-control" placeholder="City..."
                                   required autocomplete="off">
                            <div class="valid-feedback">
                                Looks good.
                            </div>
                            <div class="invalid-feedback">
                                Please provide an city.
                            </div>
                        </div>
                        <div class="form-group col-md-3">
                            <label
                                for="<c:out value="street_address-${user.id}"/>">Street
                                Address: </label>
                            <input type="text" name="street_address"
                                   id="<c:out value="street_address-${user.id}"/>"
                                   value="<c:out value="${user.address.streetAddress}"/>"
                                   <c:if
                                       test="${(user.email != email && role != 'ADMIN')}">disabled</c:if>
                                   class="form-control"
                                   placeholder="Street Address..."
                                   required autocomplete="off">
                            <div class="valid-feedback">
                                Looks good.
                            </div>
                            <div class="invalid-feedback">
                                Please provide an street_address.
                            </div>
                        </div>
                        <input type="hidden" name="id"
                               id="<c:out value="id-${user.id}"/>"
                               value="<c:out value="${user.id}"/>">
                        <input type="submit" value="Edit"
                               <c:if
                                   test="${(user.email != email && role != 'ADMIN')}">disabled</c:if>
                               class="nav-link btn btn-link"
                               style="padding-top: 26px">
                    </div>
                </form>
            </fieldset>
            <c:if test="${role == 'ADMIN'}">
                <form
                    action="${pageContext.servletContext.contextPath}/remove"
                    method="post">
                    <input type="hidden" name="id"
                           id="<c:out value="id-remove-${user.id}"/>"
                           value="<c:out value="${user.id}"/>">
                    <input type="submit" value="Remove"
                           class="nav-link btn btn-link"
                           style="padding-top: 26px">
                </form>
            </c:if>
        </li>
    </c:forEach>
</ul>

<script>
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            var forms = document.getElementsByClassName('needs-validation');
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script
    src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
    integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
    crossorigin="anonymous"></script>
<script
    src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
    integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
    crossorigin="anonymous"></script>
</body>
</html>
