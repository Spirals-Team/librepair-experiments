<%@ page import="ru.job4j.models.Advert" %>
<%@ page import="ru.job4j.storage.CarStor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View</title>
</head>
<body>
<div>
    <form action="${pageContext.servletContext.contextPath}/main" method="get">
        <input type="submit" value="К списку всех объявлений">
    </form>
</div>
<div>
    <table border="1">
        <tr>
            <% Advert advert = CarStor.INSTANCE.getaStor().findById(Integer.parseInt(request.getParameter("id")));%>
            <td><%=advert.getBrand().getName()%></td>
            <td><%=advert.getModel().getName()%></td>
            <td><%=advert.getTime()%></td>
        </tr>
        <tr>
            <td colspan="3"><%=advert.getName()%></td>
        </tr>
        <tr>
            <td colspan="3"><%=advert.getDescription()%></td>
        </tr>
        <tr>
            <% if (advert.getPic().length() > 0) { %>
            <td colspan="3"><img src="data:image/jpg;base64,<%=advert.getPic()%>"  width="300"></td>
            <% } %>
        </tr>
    </table>
</div>
</body>
</html>
