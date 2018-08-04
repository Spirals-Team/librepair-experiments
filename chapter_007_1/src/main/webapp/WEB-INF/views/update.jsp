<%@ page import="ru.job4j.model.pojo.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <title>Update</title>
</head>
<body>
    <div class="container">
        <h3 class="text-center">Updating user</h3>
        <form action="${pageContext.servletContext.contextPath}/update" method="POST">
            <input type="hidden" name="id" value="<c:out value="${user.id}"/>">
            <c:if test="${errorMessage != ''}">
                <h4 class="text-center text-danger"><c:out value="${errorMessage}"/></h4>
            </c:if>
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" class="form-control" name="name" id="name" value="<c:out value="${user.name}"/>">
            </div>
            <div class="form-group">
                <label for="login">Login:</label>
                <input type="text" class="form-control" name="login" id="login" value="<c:out value="${user.login}"/>">
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" class="form-control" name="password" id="password" value="<c:out value="${user.password}"/>">
            </div>
            <div class="form-group">
                <label for="country">Country:</label>
                <input type="text" class="form-control" name="country" id="country" value="<c:out value="${user.address.country}"/>">
            </div>
            <div class="form-group">
                <label for="city">City:</label>
                <input type="text" class="form-control" name="city" id="city" value="<c:out value="${user.address.city}"/>">
            </div>
            <c:if test="${loginedUser.role == 'ADMIN'}">
                <div class="form-group">
                    <label for="role">Role:</label>
                    <select class="form-control" id="role" name="role">
                        <c:forEach items="${roles}" var="role">
                            <option value="${role}" <c:if test="${user.role == role}">selected</c:if>>${role}</option>
                        </c:forEach>
                    </select>
                </div>
            </c:if>
            <c:if test="${loginedUser.role != 'ADMIN'}">
                <input type="hidden" name="role" value="<c:out value="${loginedUser.role}"/>">
            </c:if>
            <div class="form-group">
                <label>Music type:</label><br>
                <c:forEach items="${musics}" var="music">
                    <input type="checkbox" name="music" value="${music}"
                        <c:forEach items="${user.musicsType}" var="musicType">
                           <c:if test="${musicType == music}">checked</c:if>
                        </c:forEach>
                    >${music}
                </c:forEach>
            </div>
            <div class="form-row text-center">
                <div class="col-12" style="margin-bottom: 20px">
                    <a href="${pageContext.servletContext.contextPath}/users" class="alert-link text-center">
                        <small>Go back</small>
                    </a>
                </div>
            </div>
            <div class="form-row text-center">
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Update</button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
