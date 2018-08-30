<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users</title>
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
    <a href="${pageContext.servletContext.contextPath}/add">Add new user</a>
</c:if>

<ul>
    <c:forEach items="${users}" var="user">
        <li>
            <fieldset <c:if test="${role == 'GUEST'}">disabled</c:if>>
                <form action="${pageContext.servletContext.contextPath}/update"
                      method="post">
                    <label for="<c:out value="name-${user.id}"/>">Name: </label>
                    <input type="text" name="name"
                           id="<c:out value="name-${user.id}"/>"
                           value="<c:out value="${user.name}"/>"
                           <c:if
                               test="${(user.login != login && role != 'ADMIN')}">disabled</c:if>
                    >
                    <label
                        for="<c:out value="login-${user.id}"/>">Login: </label>
                    <input type="text" name="login"
                           id="<c:out value="login-${user.id}"/>"
                           value="<c:out value="${user.login}"/>"
                           <c:if
                               test="${(user.login != login && role != 'ADMIN')}">disabled</c:if>
                    >
                    <label
                        for="<c:out value="passw-${user.id}"/>">Password: </label>
                    <input type="password" name="passw"
                           id="<c:out value="passw-${user.id}"/>"
                           value="<c:out value=""/>"
                           <c:if
                               test="${(user.login != login && role != 'ADMIN')}">disabled</c:if>
                    >
                    <label
                        for="<c:out value="email-${user.id}"/>">Email: </label>
                    <input type="text" name="email"
                           id="<c:out value="email-${user.id}"/>"
                           value="<c:out value="${user.email}"/>"
                           <c:if
                               test="${(user.login != login && role != 'ADMIN')}">disabled</c:if>
                    >
                    <label for="<c:out value="role-${user.id}"/>">Role: </label>
                    <select name="role" id="<c:out value="role-${user.id}"/>"
                            <c:if
                                test="${role != 'ADMIN'}">disabled</c:if>
                    >
                        <c:forEach items="${roles}" var="r">
                            <option value="${r}"
                                    <c:if
                                        test="${r == user.role}">selected
                                <c:set var="selectedRole" scope="session"
                                       value="${r}"/>
                            </c:if>
                            >
                                <c:out value="${r}"/>
                            </option>
                        </c:forEach>
                    </select>
                    <c:if test="${role != 'ADMIN'}">
                        <input type="hidden" name="role"
                               value="<c:out value = "${selectedRole}"/>"/>
                    </c:if>
                    <input type="hidden" name="id"
                           id="<c:out value="id-${user.id}"/>"
                           value="<c:out value="${user.id}"/>">
                    <input type="submit" value="Edit"
                           <c:if
                               test="${(user.login != login && role != 'ADMIN')}">disabled</c:if>
                    >
                </form>
            </fieldset>
            <c:if test="${role == 'ADMIN'}">
                <form action="${pageContext.servletContext.contextPath}/remove"
                      method="post">
                    <input type="hidden" name="id"
                           id="<c:out value="id-${user.id}"/>"
                           value="<c:out value="${user.id}"/>">
                    <input type="submit" value="Remove">
                </form>
            </c:if>
        </li>
    </c:forEach>
</ul>

</body>
</html>
