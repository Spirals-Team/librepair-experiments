<%--
  Created by IntelliJ IDEA.
  User: CyMpak
  Date: 27.07.2018
  Time: 13:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Изменить роль</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style type="text/css">
        #auth-form {
            position: absolute;
            height: auto;
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
<div id="auth-form" class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">Изменить роль</h3>
    </div>
    <form class="panel-body" method="post" action="${pageContext.servletContext.contextPath}/editrole">
        <label for="sel1">Выберете роль пользователя:</label>
        <div class="input-group">
            <span class="input-group-addon">
            <span class="glyphicon glyphicon-th-list"></span>
            </span>
            <select class="form-control" id="sel1" name="update_role">
                <c:forEach items="${allRole}" var="type">
                    <c:if test="${type.key == role}">
                        <option selected value="${type.key}">${type.value}</option>
                    </c:if>
                    <c:if test="${type.key != role}">
                        <option value="${type.key}">${type.value}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>

        <input type="hidden" name="id" value="${id}">
        <button type="submit" class="btn btn-primary">Изменить</button>
        <a href="${pageContext.servletContext.contextPath}/list">
            <button type="button" class="btn btn-primary">Отмена</button>
        </a>
    </form>
</div>
</body>
</html>
