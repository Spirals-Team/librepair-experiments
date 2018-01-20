<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8");%>
<%@ page session="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="today" prefix="ct" %>

<c:set var="lang" scope="session" value="${empty sessionScope.locale ? 'en_US' : sessionScope.locale}"/>

<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="/locale/messages" var="rb"/>
<html lang="${lang}">
<head>
    <title>Store</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-inverse">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/store">
                <fmt:message key="store.main" bundle="${rb}"/>
            </a>
        </div>

        <ul class="nav navbar-nav">
            <table class="border-light">
                <tr>
            <td>
                    <li>
                <a href="${pageContext.request.contextPath}/products">
                    <fmt:message key="store.products" bundle="${rb}"/>
                </a>
            </li>
            </td>
                    <td>
            <li>
                <c:if test="${user.isRole() eq true}">
                <a href="${pageContext.request.contextPath}/store/adminPage">
                    <fmt:message key="store.adminPage" bundle="${rb}"/>
                </a>
            </c:if>
                <c:if test="${user.isRole() eq false}">
                <a href="${pageContext.request.contextPath}/store/personalPage">
                    <fmt:message key="store.personalPage" bundle="${rb}"/>
                </a>
            </c:if>
            </li>
                    </td>
                    <td>

            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-globe" aria-hidden="true"></i>
                    ${sessionScope.locale.getLanguage()}
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <c:forEach items="${applicationScope.locales}" var="locale">
                        <c:set var="lang" value="${locale.getLanguage()}"/>
                        <li>
                            <a href="${pageContext.request.contextPath}/store/locale?lang=${lang}">${lang.toUpperCase()}</a></li>
                        </li>
                    </c:forEach>
                </ul>
            </li>
                    </td>
            <c:choose>
                <c:when test="${empty user}">
                    <td>
                    <li>
                        <a href="${pageContext.request.contextPath}/store/login">
                            <span class="glyphicon glyphicon-log-out"></span>
                    <fmt:message key="store.login" bundle="${rb}"/>
                        </a>
                    </li>
                    </td>
                    <td>
                    <li>
                        <a href="${pageContext.request.contextPath}/store/signUp">
                            <span class="glyphicon glyphicon-log-out"></span>
                            <fmt:message key="store.signUp" bundle="${rb}"/>
                        </a>
                    </li>
                    </td>
                </c:when>
                <c:otherwise>
                    <td>
                   <li>
                       <a href="${pageContext.request.contextPath}/store/logout">
                           <span class="glyphicon glyphicon-log-out"></span>
                           <fmt:message key="store.logout" bundle="${rb}"/>
                           <c:out value="${user.getNickname()}"/>
                       </a>
                   </li>
                    </td>
                </c:otherwise>
            </c:choose>
                </tr>
            </table>
        </ul>


    </nav>
</div>
