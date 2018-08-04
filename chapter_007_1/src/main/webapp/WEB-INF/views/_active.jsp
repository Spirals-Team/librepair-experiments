<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
    <ul class="nav justify-content-end">
        <li class="nav-item">
            <form action="${pageContext.servletContext.contextPath}/users" method="GET">
                <button type="submit" class="btn btn-primary">Find all users</button>
            </form>
        </li>
        <li class="nav-item">
            <form action="${pageContext.servletContext.contextPath}/findByAddress" method="GET">
                <button type="submit" class="btn btn-primary">Find by address</button>
            </form>
        </li>
        <li class="nav-item">
            <form action="${pageContext.servletContext.contextPath}/findByRole" method="GET">
                <button type="submit" class="btn btn-primary">Find by role</button>
            </form>
        </li>
        <li class="nav-item">
            <form action="${pageContext.servletContext.contextPath}/findByMusic" method="GET">
                <button type="submit" class="btn btn-primary">Find by music type</button>
            </form>
        </li>
    </ul>
</div>