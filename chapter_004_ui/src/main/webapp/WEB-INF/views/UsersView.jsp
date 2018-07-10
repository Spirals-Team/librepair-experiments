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
    <a href="#" class="navbar-brand">Hello, <c:out value="${login}"/></a>
    <button class="navbar-toggler" data-toggle="collapse"
            data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav ml-auto">
            <c:if test="${role !='GUEST'}">
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
            <fieldset <c:if test="${role == 'GUEST'}">disabled</c:if>>
                <form action="${pageContext.servletContext.contextPath}/update"
                      method="post" class="needs-validation p-3" novalidate>
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label
                                for="<c:out value="name-${user.id}"/>">Name: </label>
                            <input type="text" name="name"
                                   id="<c:out value="name-${user.id}"/>"
                                   value="<c:out value="${user.name}"/>"
                                   <c:if
                                       test="${(user.login != login && role != 'ADMIN')}">disabled</c:if>
                                   class="form-control" placeholder="Name"
                                   required
                                   autofocus autocomplete="off"
                            >
                            <div class="valid-feedback">
                                Looks good.
                            </div>
                            <div class="invalid-feedback">
                                Please provide a name.
                            </div>
                        </div>
                        <div class="form-group col-md-3">
                            <label
                                for="<c:out value="login-${user.id}"/>">Login: </label>
                            <input type="text" name="login"
                                   id="<c:out value="login-${user.id}"/>"
                                   value="<c:out value="${user.login}"/>"
                                   <c:if
                                       test="${(user.login != login && role != 'ADMIN')}">disabled</c:if>
                                   class="form-control" placeholder="login"
                                   required autocomplete="off">
                            <div class="valid-feedback">
                                Looks good.
                            </div>
                            <div class="invalid-feedback">
                                Please provide a login.
                            </div>
                        </div>
                        <div class="form-group col-md-3">
                            <label
                                for="<c:out value="passw-${user.id}"/>">Password: </label>
                            <input type="password" name="passw"
                                   id="<c:out value="passw-${user.id}"/>"
                                   value="<c:out value=""/>"
                                   <c:if
                                       test="${(user.login != login && role != 'ADMIN')}">disabled</c:if>
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
                                for="<c:out value="email-${user.id}"/>">Email: </label>
                            <input type="text" name="email"
                                   id="<c:out value="email-${user.id}"/>"
                                   value="<c:out value="${user.email}"/>"
                                   <c:if
                                       test="${(user.login != login && role != 'ADMIN')}">disabled</c:if>
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
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label
                                for="<c:out value="role-${user.id}"/>">Role: </label>
                            <select name="role"
                                    id="<c:out value="role-${user.id}"/>"
                                    <c:if
                                        test="${role != 'ADMIN'}">disabled</c:if>
                                    class="form-control">
                                <c:forEach items="${roles}" var="r">
                                    <option value="${r}"
                                            <c:if
                                                test="${r == user.role}">selected
                                        <c:set var="selectedRole"
                                               scope="session"
                                               value="${r}"/>
                                    </c:if>
                                    >
                                        <c:out value="${r}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <c:if test="${role != 'ADMIN'}">
                            <input type="hidden" name="role"
                                   value="<c:out value = "${selectedRole}"/>"/>
                        </c:if>
                        <input type="hidden" name="id"
                               id="<c:out value="id-${user.id}"/>"
                               value="<c:out value="${user.id}"/>">
                        <div class="form-group col-md-3">
                            <label for="<c:out value="region-${user.id}"/>">Region: </label>
                            <select name="region"
                                    onfocus="loadRegions(this.value, '${user.country}', ${user.id})"
                                    onchange="loadCountries(this.value, '${user.country}', ${user.id})"
                                    id="<c:out value="region-${user.id}"/>"
                                    <c:if
                                        test="${(user.login != login && role != 'ADMIN')}">disabled</c:if>
                                    class="form-control">
                                <option value="<c:out value="${user.region}"/>"
                                        selected>${user.region}</option>
                            </select>
                            <div class="valid-feedback">
                                Looks good.
                            </div>
                            <div class="invalid-feedback">
                                Please select region.
                            </div>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="<c:out value="country-${user.id}"/>">Country: </label>
                            <select name="country"
                                    id="<c:out value="country-${user.id}"/>"
                                    onfocus="loadCountries('${user.region}', this.value, ${user.id})"
                                    <c:if
                                        test="${(user.login != login && role != 'ADMIN')}">disabled</c:if>
                                    class="form-control">
                                <option value="<c:out value="${user.country}"/>"
                                        selected>${user.country}</option>
                            </select>
                            <div class="valid-feedback">
                                Looks good.
                            </div>
                            <div class="invalid-feedback">
                                Please select country.
                            </div>
                        </div>
                        <input type="submit" value="Edit"
                               <c:if
                                   test="${(user.login != login && role != 'ADMIN')}">disabled</c:if>
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
                    console.log(form);
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
                    });
                }
            )
            .catch(function (err) {
                console.error('Fetch Error -', err);
            });
    });

    function loadCountries(region, country, userId) {
        var countryDd = document.getElementById('country-' + userId);
        countryDd.innerHTML = '';
        locations.filter(function (loc) {
            return loc.Region.toUpperCase() === region.toUpperCase();
        }).forEach(function (loc) {
            var option = document.createElement('option');
            option.text = loc.Name;
            option.value = loc.Name;
            if (country.toUpperCase() === loc.Name.toUpperCase()) {
                option.selected;
            }
            countryDd.add(option);
        })
    }

    function loadRegions(region, country, userId) {
        var dropdown = document.getElementById('region-' + userId);
        dropdown.innerHTML = '';
        var regionsWithDuplicates = locations.map(function (loc) {
            return loc.Region;
        }).filter(function (loc) {
            return loc.localeCompare("");
        });
        var regions = new Set(regionsWithDuplicates);
        regions.forEach(function (loc) {
            var option = document.createElement('option');
            option.text = loc;
            option.value = loc;
            if (loc.toUpperCase() === region.toUpperCase()) {
                option.selected;
            }
            dropdown.add(option);
        });
        loadCountries(region, country, userId);
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
