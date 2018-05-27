<%@ page import="ru.job4j.models.User" %>
<%@ page import="ru.job4j.storage.CarStor" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
<div>
    <h5 align="center"><a href="${pageContext.servletContext.contextPath}/main">На главную</a></h5>
</div>
<div>
    <table border="1">
        <% List<User> list = CarStor.INSTANCE.getuStor().getAll(); %>
        <% if (list != null && list.size() > 0) { %>
        <tr>
            <td>ID</td>
            <td>Login</td>
            <td>Role</td>
            <td>Change Role</td>
            <td>DELETE</td>
        </tr>
        <% for (User user : list) { %>
        <tr>
            <td><%=user.getId()%></td>
            <td><%=user.getLogin()%></td>
            <td><%=user.getRole()%></td>
            <td>
                <form action="${pageContext.servletContext.contextPath}/admin/users" method="post">
                    <input type="hidden" name="id" value="<%=user.getId()%>">
                    <input type="submit" value="change">
                </form>
            </td>
            <td>
                <form action="${pageContext.servletContext.contextPath}/admin/usersdel" method="post">
                    <input type="hidden" name="id" value="<%=user.getId()%>">
                    <input type="submit" value="DELETE">
                </form>
            </td>
        </tr>
        <% }} %>
    </table>
</div>
</body>
</html>
