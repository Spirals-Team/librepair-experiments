<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<c:url value="/main/login" var="loginUrl"/>
<form action="${loginUrl}" method="post">
    <table style="margin: auto; margin-top: 20%;" >
        <tr>
            <td><label for="username">Username:</label></td>
            <td><input type="text" name="username" id="username" value="${requestScope.get("login")}"></td></tr>
        <tr>
            <td><label for="password">Password:</label></td>
            <td><input type="password" id="password" name="password" value=""></td></tr>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <tr><td></td><td><input type="submit" value="LogIn"></td></tr>
    </table>
</form>
<div>
    <h5 align="center"><a href="${pageContext.servletContext.contextPath}/main/login/new">Регистрация</a></h5>
</div>
<div>
    <h5 align="center"><a href="${pageContext.servletContext.contextPath}/main">На главную</a></h5>
</div>
<p align="center" style="color: red">
    ${SPRING_SECURITY_LAST_EXCEPTION.message}
</p>
</body>
</html>
