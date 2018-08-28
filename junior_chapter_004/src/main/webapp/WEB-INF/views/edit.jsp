<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Изменить пользователя</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        function validate() {
            var login = $('#login').val();
            var name = $('#name').val();
            var email = $('#email').val();
            var password = $('#password').val();
            if (login == '') {
                alert($('#login').attr('placeholder'));
                return false;
            } else if (name == '') {
                alert($('#name').attr('placeholder'));
                return false;
            } else if (email == '') {
                alert($('#email').attr('placeholder'));
                return false;
            } else if (password == '') {
                alert($('#password').attr('placeholder'));
                return false;
            } else {
                return true;
            }
        }

        function loadCities() {
            $.ajax('./address', {
                type: 'POST',
                dataType: 'json',
                data: {"country": $('#selectCountry').val()},
                complete: function (data) {
                    var cities = JSON.parse(data.responseText);
                    var result = "";
                    var values = "${user.citi}";
                    for (i = 0; i !== cities.length; ++i) {
                        if (values == cities[i]) {
                            result += "<option selected value = \"" + cities[i] + "\">" + cities[i] + "</option>";
                        } else {
                            result += "<option value = \"" + cities[i] + "\">" + cities[i] + "</option>";
                        }
                    }
                    var citiesDiv = document.getElementById("selectCity");
                    citiesDiv.innerHTML = result;
                }
            })
        }

        $(
            $.ajax('./address', {
                type: 'GET',
                dataType: 'json',
                complete: function (data) {
                    var countries = JSON.parse(data.responseText);
                    var result = "";
                    var values = "${user.countries}";
                    for (var i = 0; i !== countries.length; ++i) {
                        if (values == countries[i]) {
                            result += "<option selected value=\"" + countries[i] + "\">" + countries[i] + "</option>";
                        } else {
                            result += "<option value=\"" + countries[i] + "\">" + countries[i] + "</option>";
                        }
                    }
                    var countriesDiv = document.getElementById("selectCountry");
                    countriesDiv.innerHTML = result;
                    loadCities();
                }
            })
        );
    </script>
    <style type="text/css">
        #create-form {
            position: absolute;
            height: auto;
            width: 400px;
            top: 50%;
            left: 50%;
            margin-top: -200px;
            margin-left: -200px;
            padding: 0;
        }

        #create-form .input-group {
            margin-bottom: 10px;
        }

        #create-form .panel-body {
            text-align: center;
        }

        #create-form .panel-title {
            text-align: center;
        }
    </style>
</head>
<body>
<div id="create-form" class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">Изменить пользователя</h3>
    </div>
    <form class="panel-body" method="post" action="${pageContext.servletContext.contextPath}/edit">
        <div class="input-group">
            <input type="hidden" id="id" name="id" class="form-control" placeholder="id"
                   value="<c:out value="${user.id}"></c:out>">
        </div>
        <div class="input-group">
            <span class="input-group-addon">
            <span class="glyphicon glyphicon-user"></span>
            </span>
            <input type="text" id="login" name="login" class="form-control" placeholder="Login"
                   value="<c:out value="${user.login}"></c:out>">
        </div>
        <div class="input-group">
            <span class="input-group-addon">
            <span class="glyphicon glyphicon-pencil"></span>
            </span>
            <input type="text" id="name" name="name" class="form-control" placeholder="Name"
                   value="<c:out value="${user.name}"></c:out>">
        </div>
        <div class="input-group">
            <span class="input-group-addon">
            <span class="glyphicon glyphicon-envelope"></span>
            </span>
            <input type="email" id="email" name="email" class="form-control" placeholder="E-mail"
                   value="<c:out value="${user.email}"></c:out>">
        </div>
        <div class="input-group">
            <span class="input-group-addon">
            <span class="glyphicon glyphicon-globe"></span>
            </span>
            <select class="form-control" id="selectCountry" name="countries" onchange="loadCities();">

            </select>
        </div>
        <div class="input-group">
            <span class="input-group-addon">
            <span class="glyphicon glyphicon-globe"></span>
            </span>
            <select class="form-control" id="selectCity" name="citi">

            </select>
        </div>
        <div class="input-group">
            <span class="input-group-addon">
            <span class="glyphicon glyphicon-lock"></span>
            </span>
            <input type="password" id="password" name="password" class="form-control" placeholder="Password"
                   value="<c:out value="${user.password}"></c:out>">
        </div>
        <button type="submit" class="btn btn-primary" onclick="return validate();">Изменить</button>
        <a href="${pageContext.servletContext.contextPath}/list">
            <button type="button" class="btn btn-primary">Отмена</button>
        </a>
    </form>
</div>
</body>
</html>
