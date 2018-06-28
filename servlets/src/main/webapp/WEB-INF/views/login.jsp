<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title></title>
</head>
<body>
<c:if test="${error !=''}">
    <div style="background-color: red">
        <c:out value="${error}"/>
    </div>
</c:if>
<form action="${pageContext.servletContext.contextPath}/signin" method="post">
    Login : <input type="text" name="login"><br/>
    Password : <input type="password" name="password"><br/>
    <select name="role">
        <option disabled>Выберите роль</option>
        <c:forEach items="${roles}" var="role">
            <option value="<c:out value="${role}"></c:out>">${role}</option>
        </c:forEach>
    </select><br/>
    <input type="submit">
</form>

<form action="${pageContext.servletContext.contextPath}/signout" method="post">
    <input type="submit" value="exit">
</form>

</body>
</html>