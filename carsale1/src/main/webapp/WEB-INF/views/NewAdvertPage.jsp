<%@ page import="ru.job4j.models.Brand" %>
<%@ page import="ru.job4j.storage.CarStor" %>
<%@ page import="ru.job4j.models.User" %>
<%@ page import="ru.job4j.models.Model" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add car</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        function select(elem) {
            $('#selModel').empty();
            var id = elem.value;
            if (id != -1) {
                $.ajax({
                    method: "post",
                    url: "./getmodel",
                    dataType: "json",
                    data: {"id": id},
                    success: function(data) {
                        for (var i = 0; i < data.length; i++) {
                            $('#selModel').append( '<option value="' + data[i].id + '">' + data[i].name + '</option>' );
                        }
                    }
                });
            }
        }
    </script>
</head>
<body>
<div>
    <h4><%=((User) session.getAttribute("user")).getLogin()%></h4>
    <h5><a href="${pageContext.servletContext.contextPath}/main/login/exit">Выйти</a></h5>
</div>
<div>
    <form action="${pageContext.servletContext.contextPath}/main" method="get">
        <input type="submit" value="К списку всех объявлений">
    </form>
</div>
<div>
    <form action="${pageContext.servletContext.contextPath}/user/addadvert" method="post" enctype="multipart/form-data">
        <table>
            <tr><td>Марка: <select id="selBrand" name="selBrand" onchange="select(this)">
                <% List<Brand> brandList = CarStor.INSTANCE.getbStor().getAll();
                    Brand firstBrand = brandList.get(0);
                    brandList.remove(0); %>
                <option selected value="<%=firstBrand.getId()%>"><%=firstBrand.getName()%></option>
                <% for (Brand brand : brandList) { %>
                <option value="<%=brand.getId()%>"><%=brand.getName()%></option>
                <% } %>
            </select></td></tr>
            <tr><td>Модель:<select id="selModel" name="selModel">
                <% for (Model model: CarStor.INSTANCE.getmStor().findByBrand(firstBrand)) { %>
                <option value="<%=model.getId()%>"><%=model.getName()%></option>
                <% } %>
            </select></td></tr>
            <tr><td>Краткое описание:<input type="text" name="name" required value="" style="width: 80%;"></td></tr>
            <tr><td>Описание:</td></tr>
            <tr><td><textarea cols="86" rows ="20" name="desc" required></textarea></td></tr>
        </table>
        <h4>Загрузить фотографию (только JPG):</h4>
        <input type="file" name="file"/>
        <div style="margin-top: 40px">
            <input type="submit" value="Добавить">
        </div>
    </form>
</div>
</body>
</html>
