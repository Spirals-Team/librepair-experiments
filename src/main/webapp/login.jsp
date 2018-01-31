<%-- @auhor danis.tazeev@gmail.com --%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/default.css"/>"/>
    <style type="text/css">
        caption {
            border-bottom: 1px solid darkgrey;
        }
        td { padding: 2px 10px }
        tfoot { text-align: right }
    </style>
</head>
<body>
    <form action="<c:url value="/login"/>" method="post">
        <table>
            <caption>Login as:</caption>
            <tbody>
                <c:forEach var="user" items="${requestScope.users}">
                    <tr>
                        <td><input type="radio" name="id" value="${user.id}"/></td>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot><tr><td colspan="3"><input type="submit"/></td></tr></tfoot>
        </table>
    </form>
</body>
</html>
