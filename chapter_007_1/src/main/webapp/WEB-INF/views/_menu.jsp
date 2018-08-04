<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
    <ul class="nav justify-content-end">
        <li class="nav-item">
            <p class="nav-link">Hello, <c:out value="${loginedUser.login}"/></p>
        </li>
        <li class="nav-item">
            <form action="${pageContext.servletContext.contextPath}/update" method="get">
                <input type="hidden" name="id" value="<c:out value="${loginedUser.id}"/>">
                <button type="submit" class="btn btn-primary">Update</button>
            </form>
        </li>
        <li class="nav-item">
            <form action="${pageContext.servletContext.contextPath}/logout" method="get">
                <button type="submit" class="btn btn-primary">Logout</button>
            </form>
        </li>
    </ul>
</div>