<%@include file="utils/header.jsp"%>

<div class="row">
    <div class="col-md-6 offset-md-3">
        <form name="LoginForm" action="/store/loginForm" method="post">
            <div class="form-group">
                <label for="exampleInputEmail1"><fmt:message key="store.nickname" bundle="${rb}"/> </label>
                <input type="text" name="login" class="form-control" id="exampleInputEmail1" aria-describedby="nicknameHelp" placeholder="<fmt:message key="store.helpWithNickname" bundle="${rb}"/>">
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1"><fmt:message key="store.password" bundle="${rb}"/> </label>
                <input type="password" name="password" class="form-control" id="exampleInputPassword1" placeholder="<fmt:message key="store.password" bundle="${rb}"/> ">
            </div>
            <div class="form-check">
                <label class="form-check-label">
                    <input type="checkbox" name="checkMeOut" value="OK" class="form-check-input">
                    <fmt:message key="store.checkMeOut" bundle="${rb}"/>

                </label>
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="store.submit" bundle="${rb}"/> </button>
        </form>
    </div>
</div>
<%@include file="utils/footer.jsp"%>