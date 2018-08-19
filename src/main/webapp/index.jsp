<%--
  Created by IntelliJ IDEA.
  User: vladimir
  Date: 12.11.17
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Movie manager</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>


<body>
<div class="container">
    <div class="jumbotron" style="padding: 1px 20px 20px 20px; margin: 0;">
        <h2><a href="./">Main page</a></h2>
        <hr>
        <a href="users">Users</a> (<a href="showlogin">show me</a>, <a href="accounts">accounts</a>)<br>
        <a href="events">Events and Tickets</a><br>
        <a href="auditoriums">Auditoriums</a><br>
        <hr>
        <a href="login">Login Page</a><br>

        <!-- without CSRF-->
        <a href="logout">Fast Logout </a><br>

        <!--with CSRF-->
        <%--<form action="logout" method="POST">--%>
            <%--<input type="submit" value="Logout"/>--%>
            <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
        <%--</form>--%>


        <a href="logoutPage">Logout Page</a><br>
        <%--<a href="tickets?eventId=0&dateTime=2018-01-01T10:00">Tickets</a><br>--%>
    </div>
</div>
</body>

</html>
