<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New User</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/main/login/new" method="post">
    <table style="margin: auto; margin-top: 20%;" >
        <tr><td>Login:</td><td><input type="text" name="login" value="${requestScope.get("login")}"></td></tr>
        <tr><td>Password:</td><td><input type="password" name="password" value=""></td></tr>
        <tr><td>Password:</td><td><input type="password" name="password2" value=""></td></tr>
        <tr><td></td><td><input type="submit" value="Created"></td></tr>
    </table>
</form>
<div>
    <h5 align="center"><a href="${pageContext.servletContext.contextPath}/main/login">Авторизоваться</a></h5>
</div>
<div>
    <h5 align="center"><a href="${pageContext.servletContext.contextPath}/main">На главную</a></h5>
</div>
<c:if test="${error ne ''}">
    <p align="center">
            ${requestScope.get("error")}
    </p>
</c:if>
</body>
</html>
