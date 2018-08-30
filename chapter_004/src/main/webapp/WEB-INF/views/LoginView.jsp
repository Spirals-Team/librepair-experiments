<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sign in</title>
</head>
<body>
<c:if test="${error != ''}">
    <div style="background-color: red">
        <c:out value="${error}"/>
    </div>
</c:if>

<form action="${pageContext.servletContext.contextPath}/signin" method="post">
    <label for="login">Login: </label>
    <input type="text" name="login" id="login" value=""
           placeholder="Your login" autofocus/>
    <br>
    <label for="passw">Password: </label>
    <input type="password" name="passw" id="passw" value=""
           placeholder="Your password"/>
    <br>
    <input type="submit" value="Sign in">
</form>
</body>
</html>
