<%@ page import="zuryanov.servlets.logic.ValidateService" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<table>
    <c:forEach items="${users}" var="user">
        <form method="post" action="${pageContext.servletContext.contextPath}/list">
            <tr>
                <td> <c:out value="${user}"></c:out> </td>
                <td>
                    <c:if test="${(rolesession=='admin') || (login eq user) }">
                    <input name="submit" type='submit' value="edit"/>
                </td>
                <td>
                    <input name="submit" type="submit" value="delete"/>
                </td>

                </c:if>
                <input type="hidden" name="user" value="${user}"/>
            </tr>
        </form>
    </c:forEach>
    <c:if test="${rolesession=='admin'}">
        <form method="post" action="${pageContext.servletContext.contextPath}/list">
            <select name="rolechange">
                <option disabled>Выберите роль</option>
                <c:forEach items="${roles}" var="rolechange">
                    <option value="<c:out value="${rolechange}"></c:out>">${rolechange}</option>
                </c:forEach>
            </select>
            <input name="submit" type="submit" value="change role"/>
        </form>
    </c:if>
</table>
</body>
</html>