<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isErrorPage = "true" %>

<%@ include file="tags/header.jsp" %>

<div class="alert alert-danger alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <%=exception.getMessage()%>.
</div>

<%@ include file="tags/footer.jsp" %>