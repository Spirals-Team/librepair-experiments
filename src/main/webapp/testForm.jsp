<%--
  Created by IntelliJ IDEA.
  User: Vladimir_Vysokomorny
  Date: 06-Dec-17
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test Form</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container">
    <form action="" method="get">
        <div class="form-group">
            <label for="birthday">Email:</label>
            <input type="datetime-local" class="form-control" id="birthday" name="birthday" placeholder="Enter birthday"
                   required="required">
        </div>
        <div class="btn-group">
            <button type="button" class="btn btn-warning" onclick="location.href='../users'">Cancel</button>
            <!--<button type="button" class="btn btn-warning" onclick="location.href='javascript: window.history.go(-1)'">Cancel</button>-->
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </form>
</div>

</body>
</html>
