<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  User: Anton
  Date: 26.11.2017
--%>

<html>
<head>
    <title>Edit user</title>
</head>
<body>
<input id="inp" type="button" value="Home" onclick="location.href='home';"/><br>

<form action='${pageContext.servletContext.contextPath}/editUser' method='post'>

    Name : <input type='text' name='name'/>
    <br/>
    Email : <input type='text' name='email'/>
    <br/>
    Date : <input type='text' name='createDate'/>
    <br/>

    <c:if test="${role == 'admin'}">
        Login : <input type='text' name='login'/>
        <br/>
        Password : <input type='text' name='password'/>
        <br/>
        Role : <input type='text' name='role'/>
        <br/>
    </c:if>

    <c:if test="${role == 'user'}">
        Login : <c:out value="${login}"></c:out>
        <br/>
        Role : <c:out value="${role}"></c:out>
        <br/>
    </c:if>

    <input type='submit'>
</form>

<table style="border: #000000"; cellpadding="1"; border="1">
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
