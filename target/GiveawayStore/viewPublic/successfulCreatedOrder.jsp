<%@include file="utils/header.jsp"%>
<h1>Dear ${sessionScope.user.getNickname()}!</h1>
<p class="text-info">
Order in execution, all information in you email : ${sessionScope.user.getEmail()}
You choice: ${sessionScope.product.getTitle()}
</p>
<%@include file="utils/footer.jsp"%>