<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <title>Creat</title>
</head>
<body>
    <div class="container">
        <h3 class="text-center">Creating a new user</h3>
        <form action="${pageContext.servletContext.contextPath}/create" method="POST">
            <c:if test="${errorMessage != ''}">
                <h4 class="text-center text-danger"><c:out value="${errorMessage}"/></h4>
            </c:if>
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" class="form-control" name="name" id="name">
            </div>
            <div class="form-group">
                <label for="login">Login:</label>
                <input type="text" class="form-control" name="login" id="login">
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" class="form-control" name="password" id="password">
            </div>
            <div class="form-group">
                <label for="country">Country:</label>
                <input type="text" class="form-control" name="country" id="country">
            </div>
            <div class="form-group">
                <label for="city">City:</label>
                <input type="text" class="form-control" name="city" id="city">
            </div>
            <div class="form-group">
                <label for="role">Role:</label>
                <select class="form-control" id="role" name="role">
                    <option value="ADMIN">ADMIN</option>
                    <option value="MODERATOR">MODERATOR</option>
                    <option value="USER" selected>USER</option>
                </select>
            </div>
            <div class="form-group">
                <label>Music type:</label><br>
                <input type="checkbox" name="music" value="RAP">RAP
                <input type="checkbox" name="music" value="ROCK">ROCK
                <input type="checkbox" name="music" value="FOLK">FOLK
                <input type="checkbox" name="music" value="CLASSIC">CLASSIC
            </div>
            <div class="form-row text-center">
                <div class="col-12" style="margin-bottom: 20px">
                    <a href="${pageContext.servletContext.contextPath}/login" class="alert-link text-center">
                        <small>Go back</small>
                    </a>
                </div>
            </div>
            <div class="form-row text-center">
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Create</button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
