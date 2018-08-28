<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="row">
            <div class="col-xs-2 navbar-header">
                <%--<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                        aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>--%>
                <a class="navbar-brand" href="<c:url value="/"/>">Phonebook</a>
            </div>
            <div class="col-xs-8">
                <input type="text" class="form-control search-employee" id="searchEmployee" placeholder="Search for...">
                <%--input-group
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button">Go!</button>
                </span>--%>
            </div><!-- /input-group -->
            <div id="navbar" class="col-xs-2 navbar-collapse collapse">
                <form class="navbar-form navbar-right" action="<c:url  value="/account/logout"/>"
                      method="post">
                    <span class="navbar-username">${sessionScope.login}</span>
                    <button type="submit" class="btn btn-default">Logout</button>
                </form>
            </div><!--/.navbar-collapse -->
        </div>
    </div>
</nav>
