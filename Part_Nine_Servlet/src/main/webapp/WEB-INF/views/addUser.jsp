<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  User: Anton
  Date: 20.11.2017
--%>
<html>
<head>
    <title>Add user</title>
</head>
<body>
<input id="inp" type="button" value="Home" onclick="location.href='home';"/></br>

<form action='${pageContext.servletContext.contextPath}/addUser' method='post'>
    Login : <input type='text' name='login'/><br/>
    Name : <input type='text' name='name'/><br/>
    Email : <input type='text' name='email'/><br/>
    Date : <input type='text' name='createDate'/><br/>
    Password : <input type='password' name='password'/><br/>
    Role :
        <select name="role">
            <option value="user"  name='role'>user</option>
            <option value="admin" name='role'>admin</option>
        </select>
    <br>
    <input type='submit' value="Добавить пользователя">
</form>


<table style="border: #000000"; cellpadding="1"; border="1">
    <tr>
        <th>Login</th>
        <th>Name</th>
        <th>Email</th>
        <th>Create date</th>
        <th>Password</th>
        <th>Role</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td><c:out value="${user.login}"></c:out></td>
            <td><c:out value="${user.name}"></c:out></td>
            <td><c:out value="${user.email}"></c:out></td>
            <td><c:out value="${user.createDate}"></c:out></td>
            <td><c:out value="${user.password}"></c:out></td>
            <td><c:out value="${user.role}"></c:out></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
