<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Форма пользователей</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style type="text/css">
        #user-table {
            position: center;
            margin-left: 10px;
            margin-right: 10px;
        }

        #user-menu {
            margin-top: 10px;
            margin-left: 10px;
            margin-right: 10px;
        }
    </style>
</head>
<body>
<div id="user-menu" class="btn-group">
    <div class="btn-group">
        <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
            Пользователь <span class="caret"></span></button>
        <ul class="dropdown-menu" role="menu">
            <li><a href="${pageContext.servletContext.contextPath}/create">Создать пользователя</a></li>
        </ul>
    </div>
    <form class="btn-group" method="get" action="${pageContext.servletContext.contextPath}/signout">
        <input type="submit" class="btn btn-primary" value="Выход">
    </form>
</div>
<div id="user-table" class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">Список пользователей</h3>
    </div>
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>#</th>
            <th>Логин</th>
            <th>Имя</th>
            <th>Электронная почта</th>
            <th>Страна</th>
            <th>Город</th>
            <th>Дата создания</th>
            <th>Редактировать</th>
            <th>Удалить</th>
            <th>Редактировать роль</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <form method="get" action="${pageContext.servletContext.contextPath}/edit">
                <input type="hidden" name="id" value="<c:out value="${user.id}"></c:out>">
                <tr>
                    <td><c:out value="${user.id}"></c:out></td>
                    <td><c:out value="${user.login}"></c:out></td>
                    <td><c:out value="${user.name}"></c:out></td>
                    <td><c:out value="${user.email}"></c:out></td>
                    <td><c:out value="${user.countries}"></c:out></td>
                    <td><c:out value="${user.citi}"></c:out></td>
                    <td><c:out value="${user.getCreateDate().getTime()}"></c:out></td>
                    <td>

                        <button class="btn btn-primary btn-xs" type="submit">Редактировать</button>
                    </td>
                    <td>

                        <button class="btn btn-primary btn-xs" type="submit" formmethod="post"
                                formaction="${pageContext.servletContext.contextPath}/delete">Удалить
                        </button>
                    </td>
                    <td>

                        <button class="btn btn-primary btn-xs" type="submit" formmethod="get"
                                formaction="${pageContext.servletContext.contextPath}/editrole">Редактировать роль
                        </button>
                    </td>
                </tr>
            </form>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
