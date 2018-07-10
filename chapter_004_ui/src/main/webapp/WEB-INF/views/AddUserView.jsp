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
    <a href="#" class="navbar-brand">Hello, <c:out value="${login}"/></a>
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

<c:if test="${role !='GUEST'}">
    <form action="${pageContext.servletContext.contextPath}/add" method="post"
          class="needs-validation p-3" novalidate>
        <div class="form-row">
            <div class="form-group col-md-3">
                <label for="name">Name: </label>
                <input type="text" name="name" id="name" value=""
                       class="form-control" placeholder="Name" required
                       autofocus autocomplete="off">
                <div class="valid-feedback">
                    Looks good.
                </div>
                <div class="invalid-feedback">
                    Please provide a name.
                </div>
            </div>
            <div class="form-group col-md-3">
                <label for="login">Login: </label>
                <input type="text" name="login" id="login" value=""
                       class="form-control" placeholder="login" required
                       autocomplete="off">
                <div class="valid-feedback">
                    Looks good.
                </div>
                <div class="invalid-feedback">
                    Please provide a login.
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
        </div>
        <div class="form-row">
            <c:if test="${role =='ADMIN'}">
                <div class="form-group col-md-3">
                    <label for="adminrole">Role: </label>
                    <select name="role" id="adminrole" class="form-control">
                        <c:forEach items="${roles}" var="r">
                            <option value="${r}"
                                    <c:if test="${r == 'GUEST'}">selected</c:if>
                            >
                                <c:out value="${r}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </c:if>
            <c:if test="${role == 'USER'}">
                <div class="form-group col-md-3">
                    <label for="userrole">Role: </label>
                    <select name="role" id="userrole" class="form-control">
                        <c:forEach items="${roles}" var="r">
                            <c:if test="${r !='ADMIN'}">
                                <option value="${r}"
                                        <c:if
                                            test="${r == 'GUEST'}">selected</c:if>
                                >
                                    <c:out value="${r}"/>
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </c:if>
            <div class="form-group col-md-3">
                <label for="region">Region: </label>
                <select name="region" id="region"
                        onchange="loadCountries(this.value)"
                        class="form-control"></select>
                <div class="valid-feedback">
                    Looks good.
                </div>
                <div class="invalid-feedback">
                    Please select region.
                </div>
            </div>
            <div class="form-group col-md-3">
                <label for="country">Country: </label>
                <select name="country" id="country" class="form-control">
                    <option value="default" selected>Choose country...</option>
                </select>
                <div class="valid-feedback">
                    Looks good.
                </div>
                <div class="invalid-feedback">
                    Please select country.
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
                    if (form[5].value.toUpperCase() === 'Choose Region...'.toUpperCase()) {
                        form[5].setCustomValidity("Invalid field.");
                        form[6].setCustomValidity("Invalid field.");
                    } else {
                        form[5].setCustomValidity("");
                        form[6].setCustomValidity("");
                    }
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

<script crossorigin="anonymous">
    var locations;
    window.addEventListener('load', function () {
        var dropdown = document.getElementById('region');

        var defaultOption = document.createElement('option');
        defaultOption.text = 'Choose Region...';
        defaultOption.selected;
        dropdown.add(defaultOption);
        dropdown.selectedIndex = 0;

        var url = 'https://cors-anywhere.herokuapp.com/http://countryapi.gear.host/v1/Country/getCountries';

        fetch(url)
            .then(
                function (response) {
                    if (response.status !== 200) {
                        console.warn('Looks like there was a problem. Status Code: ' +
                            response.status);
                        return;
                    }

                    response.json().then(function (data) {
                        locations = data.Response;
                        var regionsWithDuplicates = locations.map(function (loc) {
                            return loc.Region;
                        }).filter(function (loc) {
                            return loc.localeCompare("");
                        });
                        var regions = new Set(regionsWithDuplicates);
                        regions.forEach(function (region) {
                            var option = document.createElement('option');
                            option.text = region;
                            option.value = region;
                            dropdown.add(option);
                        });
                    });
                }
            )
            .catch(function (err) {
                console.error('Fetch Error -', err);
            });
    });

    function loadCountries(value) {
        var dropdown = document.getElementById('country');
        locations.filter(function (loc) {
            return loc.Region.toUpperCase() === value.toUpperCase();
        }).forEach(function (loc) {
            var option = document.createElement('option');
            option.text = loc.Name;
            option.value = loc.Name;
            dropdown.add(option);
        })
    }
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
