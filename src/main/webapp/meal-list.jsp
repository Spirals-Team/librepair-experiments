<%-- @auhor danis.tazeev@gmail.com --%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Meal</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/default.css"/>"/>
    <style type="text/css">
        form { margin-bottom: 1em }
        form > table {
            width: min-content;
            padding-bottom: .4em;
            border-collapse: separate;
            border-top: 1px solid darkgrey;
            border-bottom: 1px solid darkgrey;
        }
        form > table th,
        form > table td {
            border: none;
            padding: 2px 5px;
        }
        form > table td:first-child { text-align: right }

        table {
            width: 100%;
            border-collapse: collapse;
        }
        caption > a {
            font-weight: normal;
            float: right;
        }
        th {
            border: 3px double darkgrey;
            border-left: 1px solid;
            border-right: none;
        }
        th:first-child { border-left: none }
        tr.green { color: green; }
        tr.red { color: red; }
        tbody > tr:hover { background-color: #E0F0FF }
        td {
            text-align: center;
            border-left: 1px solid darkgrey;
            padding: 0 5px;
        }
        td:first-child { border-left: none }
        td.right-aligned { text-align: right }
    </style>
</head>
<body>
    <form action="<c:url value="/meal?sorting=${param.sorting}"/>" method="post">
        <table>
            <caption>Filter</caption>
            <thead><tr><th/><th>Date</th><th>Time</th></tr></thead>
            <tbody>
                <tr>
                    <td>from:</td>
                    <td><input type="date" name="d1" value="${requestScope.filteringParams.d1}"/></td>
                    <td><input type="time" name="t1" value="${requestScope.filteringParams.t1}"/></td>
                </tr>
                <tr>
                    <td>to:</td>
                    <td><input type="date" name="d2" value="${requestScope.filteringParams.d2}"/></td>
                    <td><input type="time" name="t2" value="${requestScope.filteringParams.t2}"/></td>
                </tr>
            </tbody>
            <tfoot><tr><td colspan="3"><input type="submit"/></td></tr></tfoot>
        </table>
    </form>

    <table>
        <caption>Meal<a href="<c:url value="/meal/save?sorting=${param.sorting}"/>">Add Meal</a></caption>
        <thead><tr>
            <th><a href="?sorting=${requestScope.sorting}">When</a></th>
            <th>Desc</th>
            <th>Calories</th>
            <th colspan="2"/>
        </tr></thead>
        <tbody>
            <c:forEach var="meal" items="${requestScope.meals}">
                <tr class="${meal.exceed ? "red" : "green"}">
                    <%--<td>${ListMealServlet.toString(meal.when)}</td>--%>
                    <td>${fn:toString(meal.meal.when)}</td>
                    <td>${meal.meal.desc}</td>
                    <td class="right-aligned">${meal.meal.calories}</td>
                    <td><a href="<c:url value="/meal/save?sorting=${param.sorting}&id=${meal.meal.id}"/>">Edit</a></td>
                    <td><a href="<c:url value="/meal/delete?sorting=${param.sorting}&id=${meal.meal.id}"/>">Delete</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
