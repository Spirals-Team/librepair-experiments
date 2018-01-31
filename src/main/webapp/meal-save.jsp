<%-- @auhor danis.tazeev@gmail.com --%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Meal</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/default.css"/>"/>
    <style type="text/css">
        form {
            width: min-content;
            margin: auto;
        }
        h1 { font-family: Cambria, sans-serif }
        td { padding: 0 5px }
        td > * { width: 100% }
        label { font-family: Cambria, sans-serif }
        tfoot { text-align: right }
    </style>
</head>
<body>
    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
    <form action="<c:url value="/meal/save"/>" method="post">
        <input type="hidden" name="sorting" value="${requestScope.sorting}"/>
        <input type="hidden" name="id" value="${meal.id}"/>
        <h1>${meal.new ? "Add" : "Edit"} Meal</h1>
        <table>
            <tbody>
                <tr>
                    <td><label for="when">When:</label></td>
                    <td><input id="when" type="datetime-local" name="when" value="${meal.when}"/></td>
                </tr>
                <tr>
                    <td><label for="desc">Description:</label></td>
                    <td><input id="desc" type="text" name="desc" value="${meal.desc}"/></td>
                </tr>
                <tr>
                    <td><label for="calories">Calories:</label></td>
                    <td><input id="calories" type="number" name="calories" value="${meal.calories}"/></td>
                </tr>
            </tbody>
            <tfoot><tr><td colspan="2"><input type="submit"/></td></tr></tfoot>
        </table>
    </form>
</body>
</html>
