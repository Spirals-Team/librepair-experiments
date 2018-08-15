<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Main page</title>
    </head>
    <body>
        <form action="${pageContext.servletContext.contextPath}/exit" method="post">
            <input style="position: absolute; top: 1%; left: 30%" type="submit" value="exit">
        </form>
        <table style="border: 1p solid black;" cellpadding="1" cellspacing="1" border="1">
            <tr>
                <th> name </th>
                <th> login </th>
                <th> email </th>
            </tr>
            <tr>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.login}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td>
                    <form action="${pageContext.servletContext.contextPath}/edit">
                        <input type="submit" value="edit user">
                        <input type="hidden" name="id" value="${user.id}">
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>