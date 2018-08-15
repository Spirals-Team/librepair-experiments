<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>create user</title>
    </head>
    <body>
        <form method="post">
            name: <input type="text" name="name">
            login: <input type="text" name="login">
            email: <input type="text" name="email">
            password: <input type="password" name="password">
            role: <select name="role">
            <option value="user">user</option>;
            <option value="administrator">admin</option>
            </select>
            <input type="submit" value="create">
        </form>
    </body>
</html>
