<%@ page import="ru.job4j.crud.pojo.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <script src="<c:url value="/js/scripts.js" />"></script>
    <title>Update</title>
</head>
<body>
    <div class="container">
        <h3 class="text-center">Updating user</h3>
        <form action="${pageContext.servletContext.contextPath}/update" method="POST" onsubmit="validFields(this); return false;">
            <input type="hidden" name="id" value="<c:out value="${user.id}"/>">
            <c:if test="${error != ''}">
                <h4 class="text-center text-danger"><c:out value="${error}"/></h4>
            </c:if>
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" class="form-control" name="name" id="name" value="<c:out value="${user.name}"/>">
            </div>
            <div class="form-group">
                <label for="login">Login:</label>
                <input type="text" class="form-control" name="login" id="login" value="<c:out value="${user.login}"/>">
            </div>
            <div class="form-group">
                <label for="email">Email address:</label>
                <input type="email" class="form-control" name="email" id="email" value="<c:out value="${user.email}"/>">
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" class="form-control" name="password" id="password" value="<c:out value="${user.password}"/>">
            </div>
            <div class="form-group">
                <div class="form-check-inline">
                    <label for="states" class="form-check-inline">State:</label>
                    <select class="form-control form-check-inlin" id="states" name="states"
                            onchange="var t = this.options[this.selectedIndex]; fillCities(t.value);">
                        <c:forEach items="${states}" var="state">
                            <option value="${state.id}" <c:if test="${user.stateId == state.id}">selected</c:if>>${state.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-check-inline">
                    <label for="cities" class="form-check-inline">City:</label>
                    <select class="form-control form-check-inlin" id="cities" name="cities">
                        <c:forEach items="${cities}" var="city">
                            <option value="${city.id}" <c:if test="${user.cityId == city.id}">selected</c:if>>${city.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="roles">Role:</label>
                <select class="form-control" id="roles" name="roles"
                        <c:if test="${(activeUser.role == initParam['roleAdmin'] and user.id == activeUser.id)
                                        or activeUser.role == initParam['roleUser']}">disabled</c:if>>
                    <option value="${initParam['roleAdmin']}"<c:if test="${user.role == initParam['roleAdmin']}">selected</c:if>>
                        Administrator
                    </option>
                    <option value="${initParam['roleUser']}" <c:if test="${user.role == initParam['roleUser']}">selected</c:if>>
                        User
                    </option>
                </select>
            </div>
            <div class="form-row text-center">
                <div class="col-12" style="margin-bottom: 20px">
                    <a href="${pageContext.servletContext.contextPath}/users" class="alert-link text-center">
                        <small>Go back</small>
                    </a>
                </div>
            </div>
            <div class="form-row text-center">
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Update</button>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
