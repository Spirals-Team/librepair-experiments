<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit user</title>
</head>
<body>
    <form action="${pageContext.servletContext.contextPath}/edit" method="get">
        <table>
            <td> Name: <input type="text" name="name" value="${requestScope.get("name")}"></td>
            <td> E-mail: <input type="text" name="email" value="${requestScope.get("email")}"></td>
            <c:if test="${requestScope.get('mainrole') eq 'admin'}">
                <td> Role: <input list="rl" name="role" value="${requestScope.get("role")}">
                    <datalist id="rl">
                        <c:forEach items="${roles}" var="rol">
                            <option value="${rol}">
                        </c:forEach>
                    </datalist>
                </td>
            </c:if>
            <c:if test="${requestScope.get('mainrole') ne 'admin'}">
                <input type="hidden" name="role" value="${requestScope.get("role")}">
            </c:if>
            <input type="hidden" name="id" value="${requestScope.get("id")}">
            <input type="hidden" name="login" value="${requestScope.get("login")}">
            <td> <input type="submit" value="Edit user"></td>
        </table>
    </form>
    <br>
    <form action="${pageContext.servletContext.contextPath}/" method="get">
        <input type="submit" value="Back">
    </form>
</body>
</html>
