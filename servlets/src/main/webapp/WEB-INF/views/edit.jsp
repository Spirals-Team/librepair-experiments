<%@ page import="zuryanov.servlets.logic.ValidateService" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<form method="post" action="${pageContext.servletContext.contextPath}/edit?id=${id}">
    <input type="text" name="login" value="${user}">
    <input type="submit">
</form>
</body>
</html>
</body>
</html>
