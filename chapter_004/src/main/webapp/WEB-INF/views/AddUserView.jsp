<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add new user</title>
</head>
<body>
<h4>Hello, <c:out value="${login}"/></h4>
<c:if test="${error != ''}">
    <div style="background-color: red">
        <c:out value="${error}"/>
    </div>
</c:if>
<form action="${pageContext.servletContext.contextPath}/signout" method="post">
    <input type="submit" value="Sign out">
</form>
<br>
<c:if test="${role !='GUEST'}">
    <form action="${pageContext.servletContext.contextPath}/add" method="post">
        <label for="name">Name: </label>
        <input type="text" name="name" id="name" value="">
        <label for="login">Login: </label>
        <input type="text" name="login" id="login" value="">
        <label for="passw">Password: </label>
        <input type="password" name="passw" id="passw" value="">
        <label for="email">Email: </label>
        <input type="text" name="email" id="email" value="">
        <c:if test="${role =='ADMIN'}">
            <select name="role">
                <c:forEach items="${roles}" var="r">
                    <option value="${r}"
                            <c:if test="${r == 'GUEST'}">selected</c:if>
                    >
                        <c:out value="${r}"/>
                    </option>
                </c:forEach>
            </select>
        </c:if>
        <c:if test="${role == 'USER'}">
            <select name="role">
                <c:forEach items="${roles}" var="r">
                    <c:if test="${r !='ADMIN'}">
                        <option value="${r}"
                                <c:if test="${r == 'GUEST'}">selected</c:if>
                        >
                            <c:out value="${r}"/>
                        </option>
                    </c:if>
                </c:forEach>
            </select>
        </c:if>
        <input type="submit" value="Add">
    </form>
</c:if>
</body>
</html>
