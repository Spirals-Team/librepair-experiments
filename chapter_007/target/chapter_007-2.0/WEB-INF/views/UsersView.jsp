<%@ page import="ru.job4j.crudservlet.User" %>
<%@ page import="ru.job4j.servlet.UserStore" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
<h3><c:out value="Welcome, ${user.name}"/></h3>
<form action="${pageContext.servletContext.contextPath}/exit" method="post">
    <input type="submit" value="Exit" align="right">
</form>

<c:if test="${user.role eq 'admin'}">
    <br>
    <form action="${pageContext.servletContext.contextPath}/add" method="post">
        <input type="submit" value="Add new user">
    </form>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Login</th>
            <th>E-mail</th>
            <th>Role</th>
            <th>Time create</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${users}" var="userfor">
            <tr>
                <td>${userfor.id}</td>
                <td>${userfor.name}</td>
                <td>${userfor.login}</td>
                <td>${userfor.email}</td>
                <td>${userfor.role}</td>
                <td>${userfor.createdate}</td>
                <td>
                    <form action="${pageContext.servletContext.contextPath}/edit" method="post">
                        <input type="hidden" name="id" value="${userfor.id}">
                        <input type="hidden" name="name" value="${userfor.name}">
                        <input type="hidden" name="login" value="${userfor.login}">
                        <input type="hidden" name="email" value="${userfor.email}">
                        <input type="hidden" name="role" value="${userfor.role}">
                        <input type="hidden" name="mainrole" value="${user.role}">
                        <input type="submit" value="edit">
                    </form>
                </td>
                <td>
                    <form action="${pageContext.servletContext.contextPath}/delete" method="post">
                        <input type="hidden" name="del_id" value="${userfor.id}">
                        <input type="submit" value="delete">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <form action="${pageContext.servletContext.contextPath}/add" method="post">
        <input type="submit" value="Add new user">
    </form>
</c:if>
<c:if test="${user.role ne 'admin'}">
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Login</th>
            <th>E-mail</th>
            <th>Role</th>
            <th>Time create</th>
            <th>Edit</th>
        </tr>
        <tr>
            <td>${user.name}</td>
            <td>${user.login}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
            <td>${user.createdate}</td>
            <td>
                <form action="${pageContext.servletContext.contextPath}/edit" method="post">
                    <input type="hidden" name="id" value="${user.id}">
                    <input type="hidden" name="name" value="${user.name}">
                    <input type="hidden" name="login" value="${user.login}">
                    <input type="hidden" name="email" value="${user.email}">
                    <input type="hidden" name="role" value="${user.role}">
                    <input type="submit" value="edit">
                </form>
            </td>
        </tr>
    </table>
</c:if>
</body>
</html>
