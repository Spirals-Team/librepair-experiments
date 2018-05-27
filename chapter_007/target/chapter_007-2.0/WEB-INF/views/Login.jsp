<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Autoresation</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/SignIn" method="post">
    <table style="margin: auto; margin-top: 20%;" >
        <tr><td>Login:</td><td><input type="text" name="login" value="${requestScope.get("login")}"></td></tr>
        <tr><td>E-mail:</td><td><input type="text" name="email" value="${requestScope.get("email")}"></td></tr>
        <tr><td></td><td> <input type="submit" value="Join"></td></tr>
    </table>
</form>
<c:if test="${error ne ''}">
    <p align="center">
            ${requestScope.get("error")}
    </p>
</c:if>
</body>
</html>
