<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Add new user</title>
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

<c:if test="${role !='USER'}">
    <form action="${pageContext.servletContext.contextPath}/add" method="post"
          class="needs-validation p-3" novalidate>
        <div class="form-row">
            <div class="form-group col-md-3">
                <label for="email">Email: </label>
                <input type="email" name="email" id="email" value=""
                       class="form-control" placeholder="mark@email.com"
                       required autocomplete="off">
                <div class="valid-feedback">
                    Looks good.
                </div>
                <div class="invalid-feedback">
                    Please provide an email.
                </div>
            </div>
            <div class="form-group col-md-3">
                <label for="passw">Password: </label>
                <input type="password" name="passw" id="passw" value=""
                       class="form-control" placeholder="*****" required
                       autocomplete="off">
                <div class="valid-feedback">
                    Looks good.
                </div>
                <div class="invalid-feedback">
                    Please provide a password.
                </div>
            </div>
            <c:if test="${role =='ADMIN'}">
                <div class="form-group col-md-3">
                    <label for="adminrole">Role: </label>
                    <select name="role" id="adminrole" class="form-control">
                        <c:forEach items="${roles}" var="r">
                            <option value="${r.type}"
                                    <c:if
                                        test="${r.type == 'USER'}">selected</c:if>
                            >
                                <c:out value="${r.type}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </c:if>
            <c:if test="${role == 'MANDATOR'}">
                <div class="form-group col-md-3">
                    <label for="userrole">Role: </label>
                    <select name="role" id="userrole" class="form-control">
                        <c:forEach items="${roles}" var="r">
                            <c:if test="${r.type !='ADMIN'}">
                                <option value="${r.type}"
                                        <c:if
                                            test="${r.type == 'USER'}">selected</c:if>
                                >
                                    <c:out value="${r.type}"/>
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </c:if>
            <div class="form-group col-md-3">
                <label for="usergenre">Music Genres: </label>
                <select name="genres" id="usergenre" class="form-control"
                        multiple>
                    <c:forEach items="${genres}" var="g">
                        <option value="${g.genre}">
                            <c:out value="${g.genre}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-3">
                <label for="country">Country: </label>
                <input type="text" name="country" id="country" value=""
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
                <label for="city">City: </label>
                <input type="text" name="city" id="city" value=""
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
                <label for="street_address">Street Address: </label>
                <input type="text" name="street_address" id="street_address"
                       value=""
                       class="form-control" placeholder="Street Address..."
                       required autocomplete="off">
                <div class="valid-feedback">
                    Looks good.
                </div>
                <div class="invalid-feedback">
                    Please provide an street_address.
                </div>
            </div>
            <input type="submit" id="btn" value="Add"
                   class="nav-link btn btn-link" style="padding-top: 26px">
        </div>
    </form>
</c:if>

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