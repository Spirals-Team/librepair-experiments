<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>edit user</title>
    </head>
    <body>
        <form id="id" action="${pageContext.servletContext.contextPath}/edit" method="post">
            <input type="hidden" name="login" value="${user.login}">
            name: <input type="text" name="name" value="<c:out value="${user.name}"/>">
            email: <input type="text" name="email" value="<c:out value="${user.email}"/>">
            password: <input type="password" name="password" selected-value="${user.password}" >
            <c:if test="${sessionScope.role == 'admin'}">
                <select name="role" id="role">
                    <option selected value="${user.role}">Choose role</option>
                    <option value="user">user</option>;
                    <option value="admin">admin</option>
                </select>
            </c:if>
            <input type="submit" value="edit">
        </form>
    </body>
</html>
