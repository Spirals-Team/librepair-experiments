<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <div class="page-header">
            <h3>
                <c:if test="${mealsList.size() != 0}">
                    ${mealsList.get(0).menu.addDate}
                </c:if>
            </h3>
        </div>
        <a class="btn btn-primary" data-toggle="modal" onclick="addMeal(${menuId})">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            <spring:message code="meal.addMeal"/>
        </a>
        <table class="table table-striped display" id="mealDatatable">
            <thead>
            <tr>
                <th><spring:message code="common.name"/></th>
                <th><spring:message code="meal.price"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${mealsList}" var="meal">
                <tr>
                    <td><c:out value="${meal.name}"/></td>
                    <td><c:out value="${meal.price}"/></td>
                    <td>
                        <a class="glyphicon glyphicon-pencil" aria-hidden="true"
                           onclick=updateMealsRow(${meal.id},${menuId})></a>
                    </td>
                    <td>
                        <a class="glyphicon glyphicon-remove" aria-hidden="true"
                           onclick="deleteMeal(${meal.id}, ${menuId})">
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <br/>
        <a class="btn btn-primary" onclick="redirectToMenus(${restId})">
            <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
            <spring:message code="meal.backToMenus"/>
        </a>
    </div>
</div>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="modalTitle"><spring:message code="meal.addMeal"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="detailsForm">
                    <input type="hidden" id="id" name="id">
                    <input type="hidden" id="menuId" name="menuId">

                    <div class="form-group">
                        <label for="name" class="control-label col-xs-3"><spring:message
                                code="common.name"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="name" name="name"
                                   placeholder="<spring:message code="common.name"/>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="name" class="control-label col-xs-3"><spring:message
                                code="meal.price"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="price" name="price"
                                   placeholder="<spring:message code="meal.price"/>">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button class="btn btn-primary" type="button" onclick="saveMeal(${menuId})">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
