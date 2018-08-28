<%--
  Created by IntelliJ IDEA.
  User: CyMpak
  Date: 24.07.2018
  Time: 13:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Авторизация</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style type="text/css">
        #auth-form {
            position: absolute;
            height: 200px;
            width: 400px;
            top: 50%;
            left: 50%;
            margin-top: -100px;
            margin-left: -200px;
            padding: 0;
        }

        #auth-form .input-group {
            margin-bottom: 10px;
        }

        #auth-form .panel-body {
            text-align: center;
        }

        #auth-form .panel-title {
            text-align: center;
        }
    </style>
</head>
<body>
<c:if test="${error !=''}">
    <div style="background-color: red">
        <c:out value="${error}"></c:out>
    </div>
</c:if>
<div id="auth-form" class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">Авторизация</h3>
    </div>
    <form class="panel-body" method="post" action="${pageContext.servletContext.contextPath}/signin">
        <div class="input-group">
            <span class="input-group-addon">
            <span class="glyphicon glyphicon-user"></span>
            </span>
            <input type="text" id="login" name="login" class="form-control" placeholder="Login">
        </div>
        <div class="input-group">
            <span class="input-group-addon">
            <span class="glyphicon glyphicon-lock"></span>
            </span>
            <input type="password" id="password" name="password" class="form-control" placeholder="Password">
        </div>
        <button type="submit" class="btn btn-primary">Войти</button>
    </form>
</div>
</body>
</html>
