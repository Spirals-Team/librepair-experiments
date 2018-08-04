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
    <h3 class="text-center">Find by address</h3>
    <form action="${pageContext.servletContext.contextPath}/findByAddress" method="POST">
        <div class="form-group">
            <label for="country">Country:</label>
            <input type="text" class="form-control" name="country" id="country">
        </div>
        <div class="form-group">
            <label for="city">City:</label>
            <input type="text" class="form-control" name="city" id="city">
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
                <button type="submit" class="btn btn-primary">Find</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
