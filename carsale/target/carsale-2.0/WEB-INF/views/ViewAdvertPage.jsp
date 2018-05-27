<%@ page import="ru.job4j.carsale.CarStorage" %>
<%@ page import="ru.job4j.models.Advert" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View</title>
</head>
<body>
<div>
    <form action="${pageContext.servletContext.contextPath}/mainpage" method="get">
        <input type="submit" value="К списку всех объявлений">
    </form>
</div>
<div>
    <table border="1">
        <tr>
            <% Advert advert = CarStorage.INSTANCE.getAdvert(Integer.parseInt(request.getParameter("id")));%>
            <td><%=CarStorage.INSTANCE.getBrand(advert.getIdBrand()).getName()%></td>
            <td><%=CarStorage.INSTANCE.getModel(advert.getIdModel()).getName()%></td>
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
