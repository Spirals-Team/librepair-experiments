<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Author: Anton Serdyuchenko.
  Date: 20.11.2017
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User storage</title>
</head>
<body>
    <input id="inp1" type="button" value="Get users" onclick="location.href='getUser';"/><br>
    <input id="inp3" type="button" value="Edit users" onclick="location.href='editUser';"/><br>

    <c:if test="${role == 'admin'}">
        <input id="inp2" type="button" value="Add users" onclick="location.href='addUser';"/><br>
        <input id="inp4" type="button" value="Delete users" onclick="location.href='deleteUser';"/><br>
    </c:if>

    <form action='${pageContext.servletContext.contextPath}/signin' method='post'>
        <button id="inp5" type="submit" value="exit">Exit</button>
    </form>
    <br>
</body>
</html>
