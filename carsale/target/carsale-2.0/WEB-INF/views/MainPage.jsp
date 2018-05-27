<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.job4j.carsale.CarStorage" %>
<%@ page import="ru.job4j.models.Advert" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.job4j.models.Brand" %>
<html>
<head>
    <title>Cars</title>
    <script>
        function Selected(a) {
            var label = a.value;
            if (label == 'brand') {
                document.getElementById("selBrand").style.display='block';
            } else {
                document.getElementById("selBrand").style.display='none';
            }
        }
    </script>
</head>
<body>
<div>
    <% if (session.getAttribute("login") == null || ((String) session.getAttribute("login")).isEmpty()) { %>
    <h4><a href="${pageContext.servletContext.contextPath}/login">Войти</a></h4>
        <% } else { %>
    <h4>Привет, <%=session.getAttribute("login")%></h4>
    <% if (CarStorage.INSTANCE.getAdvertUser((Integer) session.getAttribute("user_id")).size() > 0) { %>
    <h5><a href="${pageContext.servletContext.contextPath}/useradvert">Мои объявления</a></h5>
    <% } %>
    <h5><a href="${pageContext.servletContext.contextPath}/exit">Выйти</a></h5>
    <% } %>
</div>
<div>
    <br>
    <form action="${pageContext.servletContext.contextPath}/add" method="get">
        <input type="submit" value="Добавить объявление">
    </form>
</div>
<div>
    <form action="${pageContext.servletContext.contextPath}/mainpage" method="get">
    <table>
        <tr>
            <td><input name="filter" type="radio" value="all" checked onChange="Selected(this)"> Все объявления</td>
            <td><input name="filter" type="radio" value="day" onChange="Selected(this)"> Объявления за последний день</td>
            <td><input name="filter" type="radio" value="pic" onChange="Selected(this)"> Объявления только с фотографией</td>
            <td><input name="filter" type="radio" value="brand" onChange="Selected(this)"> Объявления марки:</td>
            <td>
                <select id="selBrand" name="selBrand" onchange="select(this)" style="display: none">
                    <% for (Object obj : (List) request.getAttribute("Brands")) {%>
                    <% Brand brand = (Brand) obj; %>
                    <option value="<%=brand.getId()%>"><%=brand.getName()%></option>
                    <% } %>
                </select>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="Применить фильтр"></td>
        </tr>
    </table>
    </form>
    <br>
</div>
<div>
    <table border="1" id="table_items">
        <% List<Advert> list = (List<Advert>) request.getAttribute("Adverts"); %>
        <% if (list != null && list.size() > 0) { %>
            <tr>
                <td>Brand</td>
                <td>Model</td>
                <td>Short desc</td>
                <td>Time</td>
                <td>Author</td>
                <td>Link</td>
            </tr>
            <% for (Advert advert : list) { %>
            <tr>
                <td><%=CarStorage.INSTANCE.getBrand(advert.getIdBrand()).getName()%></td>
                <td><%=CarStorage.INSTANCE.getModel(advert.getIdModel()).getName()%></td>
                <td><%=advert.getName()%></td>
                <td><%=advert.getTime()%></td>
                <td><%=advert.getUser().getLogin()%></td>
                <td><a href="${pageContext.servletContext.contextPath}/view?id=<%=advert.getId()%>">link</a></td>
            </tr>
        <% }} %>
    </table>
</div>
</body>
</html>
