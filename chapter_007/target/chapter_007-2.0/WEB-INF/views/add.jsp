<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add user</title>
</head>
    <body>
        <br>
        <form action="${pageContext.servletContext.contextPath}/add" method="get">
            <table>
                <td> Name: <input type="text" name="name" value="${requestScope.get("name")}"></td>
                <td> Login: <input type="text" name="login" value="${requestScope.get("login")}"></td>
                <td> E-mail: <input type="text" name="email" value="${requestScope.get("email")}"></td>
                <td> <input type="submit" value="Add new user"></td>
            </table>
        </form>
        <br>
        <form action="${pageContext.servletContext.contextPath}/" method="get">
            <input type="submit" value="Back">
        </form>
        <c:if test="${error != ''}">
            ${requestScope.get("error")}
        </c:if>
    </body>
</html>
