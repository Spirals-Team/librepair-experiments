<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/login" method="post">
    <table style="margin: auto; margin-top: 20%;" >
        <tr><td>Login:</td><td><input type="text" name="login" value="${requestScope.get("login")}"></td></tr>
        <tr><td>Password:</td><td><input type="password" name="password" value=""></td></tr>
        <tr><td></td><td><input type="submit" value="Join"></td></tr>
    </table>
</form>
<h5 align="center"><a href="${pageContext.servletContext.contextPath}/new">Регистрация</a></h5>
<c:if test="${error ne ''}">
    <p align="center">
            ${requestScope.get("error")}
    </p>
</c:if>
</body>
</html>
