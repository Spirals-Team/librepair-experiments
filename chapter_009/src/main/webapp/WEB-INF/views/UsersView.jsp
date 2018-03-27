<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users</title>
</head>
<body>

<table align="right">
    <tr>
        <td>Welcome: <strong><c:out value="${login}"/></strong></td>
        <td>
            <form action="${pageContext.servletContext.contextPath}/" method="post">
                <input type="submit" name="exit" value="Log out">
            </form>
        </td>
    </tr>
</table><br>

<h1 align="center">Хранилище пользователей</h1>
<c:if test="${admin != null}">
<h2 align="center"><form action="${pageContext.servletContext.contextPath}/forward" method="post">
    <input type="submit" name="action" value="Add new User">
</form></h2>
</c:if>
<h3 align="center">Список пользователей</h3>
<table style="border: 1px solid black;" cellpadding="3" cellspacing="1" align="center" border="1" >
    <tr>
        <th>id</th>
        <th>Login</th>
        <th>Password</th>
        <th>Name</th>
        <th>Email</th>
        <th>Role</th>
        <th>Date</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td><c:out value="${user.id}"/></td>
            <td><c:out value="${user.login}"/></td>
            <td><c:out value="${user.password}"/></td>
            <td><c:out value="${user.name}"/></td>
            <td><c:out value="${user.email}"/></td>
            <td><c:out value="${user.role}"/></td>
            <td><c:out value="${user.date}"/></td>
            <td>
                <form action="${pageContext.servletContext.contextPath}/forward" method="post">
                    <input type="hidden" name="login" value="<c:out value="${user.login}"/>">
                    <input type="hidden" name="name" value="<c:out value="${user.name}"/>">
                    <input type="hidden" name="email" value="<c:out value="${user.email}"/>">
                    <input type="submit" name="action" value="Update">
                </form>
            </td>
            <td>
                <form action="${pageContext.servletContext.contextPath}/forward" method="post">
                    <input type="hidden" name="login" value="<c:out value="${user.login}"/>">
                    <input type="submit" name="action" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
