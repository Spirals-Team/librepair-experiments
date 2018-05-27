<%@ page import="ru.job4j.models.Brand" %>
<%@ page import="ru.job4j.models.Advert" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.job4j.models.User" %>
<%@ page import="ru.job4j.storage.CarStor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <% User user = (User) session.getAttribute("user"); %>
    <% if (user == null || user.getLogin().isEmpty()) { %>
    <h4><a href="${pageContext.servletContext.contextPath}/main/login">Войти</a></h4>
    <% } else { %>
    <h4>Привет, <%=user.getLogin()%></h4>
    <% if (CarStor.INSTANCE.getaStor().findByUser(user).size() > 0) { %>
    <h5><a href="${pageContext.servletContext.contextPath}/user/adverts">Мои объявления</a></h5>
    <% } %>
    <h5><a href="${pageContext.servletContext.contextPath}/main/login/exit">Выйти</a></h5>
    <% } %>
</div>
<div>
    <% if (user != null && user.getRole().equals("ROLE_ADMIN")) { %>
    <h4><a href="${pageContext.servletContext.contextPath}/admin/adverts">Все объявления</a></h4>
    <h4><a href="${pageContext.servletContext.contextPath}/admin/users">Все пользователи</a></h4>
    <% } %>
</div>
<div>
    <br>
    <form action="${pageContext.servletContext.contextPath}/user/addadvert" method="get">
        <input type="submit" value="Добавить объявление">
    </form>
</div>
<div>
    <form action="${pageContext.servletContext.contextPath}/main" method="get">
        <table>
            <tr>
                <td><input name="filter" type="radio" value="all" checked onChange="Selected(this)"> Все объявления</td>
                <td><input name="filter" type="radio" value="day" onChange="Selected(this)"> Объявления за последний день</td>
                <td><input name="filter" type="radio" value="pic" onChange="Selected(this)"> Объявления только с фотографией</td>
                <td><input name="filter" type="radio" value="brand" onChange="Selected(this)"> Объявления марки:</td>
                <td>
                    <select id="selBrand" name="selBrand" onchange="select(this)" style="display: none">
                        <% for (Brand brand: CarStor.INSTANCE.getbStor().getAll()) {%>
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
            <td>Pic</td>
            <td>Brand</td>
            <td>Model</td>
            <td>Short desc</td>
            <td>Time</td>
            <td>Author</td>
            <td>Link</td>
        </tr>
        <% for (Advert advert : list) { %>
        <tr>
            <td>
                <% if (advert.getPic().length() > 0) { %>
                <img src="data:image/jpg;base64,<%=advert.getPic()%>"  width="100">
                <% } %>
            </td>
            <td><%=advert.getBrand().getName()%></td>
            <td><%=advert.getModel().getName()%></td>
            <td><%=advert.getName()%></td>
            <td><%=advert.getTime()%></td>
            <td><%=advert.getUser().getLogin()%></td>
            <td>
                <form action="${pageContext.servletContext.contextPath}/main/view" method="post">
                    <input type="hidden" name="id" value="<%=advert.getId()%>">
                    <input type="submit" value="link">
                </form>
            </td>
        </tr>
        <% }} %>
    </table>
</div>
</body>
</html>
