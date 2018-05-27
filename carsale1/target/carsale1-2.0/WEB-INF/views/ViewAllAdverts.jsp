<%@ page import="ru.job4j.models.Advert" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.job4j.storage.CarStor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All adverts</title>
</head>
<body>
<div>
    <h5 align="center"><a href="${pageContext.servletContext.contextPath}/main">На главную</a></h5>
</div>
<div>
    <table border="1">
        <% List<Advert> list = CarStor.INSTANCE.getaStor().getAll(); %>
        <% if (list != null && list.size() > 0) { %>
        <tr>
            <td>ID</td>
            <td>Brand</td>
            <td>Model</td>
            <td>Short desc</td>
            <td>Time</td>
            <td>Author</td>
            <td>Status</td>
            <td>Pic</td>
            <td>Link</td>
            <td>DELETE</td>
        </tr>
        <% for (Advert advert : list) { %>
        <tr>
            <td><%=advert.getId()%></td>
            <td><%=advert.getBrand().getName()%></td>
            <td><%=advert.getModel().getName()%></td>
            <td><%=advert.getName()%></td>
            <td><%=advert.getTime()%></td>
            <td><%=advert.getUser().getLogin()%></td>
            <td><%=advert.isStatus()%></td>
            <td>
                <% if (advert.getPic().length() > 0) { %>
                <img src="data:image/jpg;base64,<%=advert.getPic()%>"  width="100">
                <% } %>
            </td>
            <td>
                <form action="${pageContext.servletContext.contextPath}/main/view" method="post">
                    <input type="hidden" name="id" value="<%=advert.getId()%>">
                    <input type="submit" value="link">
                </form>
            </td>
            <td>
                <form action="${pageContext.servletContext.contextPath}/admin/adverts" method="post">
                    <input type="hidden" name="id" value="<%=advert.getId()%>">
                    <input type="submit" value="DELETE">
                </form>
            </td>
        </tr>
        <% }} %>
    </table>
</div>
</body>
</html>
