<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Answer</title>
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
</table>
<h1 align="center"><c:out value="${title}"/></h1>
<c:if test="${ask != null}">
<table align="center">
    <tr >
        <td>
            <form  action="${pageContext.servletContext.contextPath}/delete" method="post">
                <input type="hidden" name="login" value="${ask}">
                <input type="submit"  value="Да">
            </form>
        </td>
        <td>
            <form action="${pageContext.servletContext.contextPath}/">
                <input type="submit" value="Нет">
            </form>
        </td>
    </tr>
</table>
</c:if>
<h2><a href="<c:out value="${pageContext.servletContext.contextPath}"/>">На главную</a></h2>
</body>
</html>
