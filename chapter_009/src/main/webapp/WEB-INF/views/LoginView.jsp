<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>SignIn</title>
</head>
<body>
<c:if test="${error != null}">
    <h2><div align="center" style="background-color: red">
        <c:out value="${error}"/>
    </div></h2>
    <br>
</c:if>
<h1 align="center">Авторизация пользователя.</h1>
<form action="${pageContext.servletContext.contextPath}/signin" method="post">
    <table align="center">
        <tr>
            <th align="left">Login: </th>
            <td><input type="text"name="login"/></td>
        </tr>
        <tr>
            <th>Password: </th>
            <td><input type="password"name="password"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Вход">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
