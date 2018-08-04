<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <title>Users</title>
</head>
<body>
    <div class="container">
        <jsp:include page="_menu.jsp"/>
        <jsp:include page="_active.jsp"/>
        <div>
            <h3 class="text-center">A user's table</h3>
            <div class="row">
                <div class="col-sm-12">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Login</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${users}" var="user">
                        <c:if test="${loginedUser != user}">
                            <tr>
                                <td><c:out value="${user.name}"/></td>
                                <td><c:out value="${user.login}"/></td>
                                <td>
                                    <div class="btn-group">
                                        <form action="${pageContext.servletContext.contextPath}/update" method="get">
                                            <input type="hidden" name="id" value="<c:out value="${user.id}"/>">
                                            <button type="submit" class="btn btn-primary">Update</button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:if>
                        </c:forEach>
                        <tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
