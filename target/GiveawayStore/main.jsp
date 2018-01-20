<%@include file="viewPublic/utils/header.jsp"%>
<%@ page import="ua.com.company.store.model.entity.additional.ProductImage" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.com.company.store.service.ProductService" %>
<%@ page import="ua.com.company.store.service.ProductImageService" %>
<% List list = ProductImageService.getInstance().getAllProducts();
     if (request.getAttribute("list") != null){
    list = (List) request.getAttribute("list");
}%>


<h1>MainPage</h1>
<div class="input-group mb-3">

    <div class="input-group-prepend">

        <fmt:message key="store.product.sorting" bundle="${rb}"/>
        <button class="btn btn-outline-secondary" type="button" onclick="location.href= '${pageContext.request.contextPath}/store/sortingProducts?v=1'">
            <fmt:message key="store.product.descendingPrice" bundle="${rb}"/>
        </button>

        <button class="btn btn-outline-secondary" type="button" onclick="location.href= '${pageContext.request.contextPath}/store/sortingProducts?v=0'">
            <fmt:message key="store.product.ascendingPrice" bundle="${rb}"/>
        </button>
    </div>


    <input type="text" id="your_search_text"  name="searchText" class="form-control" placeholder="<fmt:message key="store.product.search" bundle="${rb}"/>" aria-label="" aria-describedby="basic-addon1">

    <div class="input-group-append">
        <button class="btn btn-outline-secondary" type="button" onclick="location.href= '${pageContext.request.contextPath}/store/searchProduct?searchText=' + document.getElementById('your_search_text').value;"><fmt:message key="store.product.search" bundle="${rb}"/></button>
          </div>


</div>


<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col"><fmt:message key="store.product.id" bundle="${rb}"/> </th>
        <th scope="col"><fmt:message key="store.product.title" bundle="${rb}"/> </th>
        <th scope="col"><fmt:message key="store.product.description" bundle="${rb}"/> </th>
        <th scope="col"><fmt:message key="store.product.price" bundle="${rb}"/> </th>
        <th scope="col"><fmt:message key="store.product.image" bundle="${rb}"/> </th>
        <c:if test="${user.isRole() eq false}">
            <th scope="col"> <fmt:message key="store.product.buy" bundle="${rb}"/></th>
        </c:if>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="prod" items="<%=list%>">
        <c:url var="image" value="${prod.pathImage}"/>
        <tr>
            <th scope="row">${prod.id}</th>
            <td>${prod.title}</td>
            <td>${prod.description}</td>
            <td>${prod.price}</td>
            <td>
                <c:if test="${image eq '/resources/images/'}">
                <img src="/resources/images/no-image-83a2b680abc7af87cfff7777d0756fadb9f9aecd5ebda5d34f8139668e0fc842.png" width="100px" height="50px">
            </c:if>
                <c:if test="${image ne '/resources/images/'}">
                    <img src="${image}" width="100px" height="50px">
                </c:if>
            </td>


            <c:if test="${user.isRole() eq false}">
                <td scope="col"> <a href="${pageContext.request.contextPath}/store/createOrder?id=${prod.id}">
                    <fmt:message key="store.product.buy" bundle="${rb}"/>
                </a>
                </td>

                </c:if>


        </tr>

    </c:forEach>

    </tbody>
</table>


<%@include file="viewPublic/utils/footer.jsp"%>