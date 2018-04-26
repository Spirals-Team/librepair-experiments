<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/menuDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<input id='restIdValue' type='hidden' value='${param.restId}'/>
<div class="jumbotron">
    <div class="container">
        <div class="page-header">
            <h3>
                <c:if test="${menusList.size() != 0}">
                    ${menusList.get(0).restaurant.name}
                </c:if>
            </h3>
        </div>
        <a class="btn btn-primary" onclick="createMenu(${param.restId})">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            <spring:message code="menu.addMenu"/>
        </a>
        <table class="table table-striped display" id="menuDatatable">
            <thead>
            <tr>
                <th><spring:message code="menu.Date"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>
        <br/>
        <a class="btn btn-primary" onclick="redirectToRestaurants()">
            <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
            <spring:message code="common.backToRestaurants"/>
        </a>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
