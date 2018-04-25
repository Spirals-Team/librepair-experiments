<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Get user</title>
</head>
<body>
    <input id="inp" type="button" value="Home" onclick="location.href='home';"/>
    <br>
    <table style="border: #000000" cellpadding="1" border="1">
        <tr>
            <th>Login</th>
            <th>Name</th>
            <th>Email</th>
            <th>Create date</th>
            <th>Role</th>
            <c:if test="${role == 'admin'}">
                <th>Password</th>
            </c:if>
        </tr>
        <c:forEach items="${users}" var="user">
        <tr>
            <td><c:out value="${user.login}"></c:out></td>
            <td><c:out value="${user.name}"></c:out></td>
            <td><c:out value="${user.email}"></c:out></td>
            <td><c:out value="${user.createDate}"></c:out></td>
            <td><c:out value="${user.role}"></c:out></td>
            <c:if test="${role == 'admin'}">
                <td><c:out value="${user.password}"></c:out></td>
            </c:if>
        </tr>
        </c:forEach>
    </table>
</body>
</html>
