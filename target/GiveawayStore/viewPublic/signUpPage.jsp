<%@include file="utils/header.jsp"%>

<div class="row">
    <div class="col-md-6 offset-md-3">
        <form name="LoginForm" accept-charset="UTF-8" action="/store/signUpForm" method="post">
            <div class="form-group">
            <label for="exampleInputEmail1"><fmt:message key="store.nickname" bundle="${rb}"/> </label>
            <input type="text" name="login" class="form-control" id="exampleInputEmail1" aria-describedby="nicknameHelp" placeholder="<fmt:message key="store.helpWithNickname" bundle="${rb}"/>">
           </div>
            <div class="form-group">
                <label for="exampleInputEmail2"><fmt:message key="store.email" bundle="${rb}"/> </label>
                <input type="email" name="email" class="form-control" id="exampleInputEmail2" aria-describedby="emailHelp" placeholder="<fmt:message key="store.helpWithEmail" bundle="${rb}"/> ">
                <small id="emailHelp" class="form-text text-muted"><fmt:message key="store.helpWithEmailSecond" bundle="${rb}"/> </small>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1"><fmt:message key="store.password" bundle="${rb}"/> </label>
                <input type="password" name="password" class="form-control" id="exampleInputPassword1" placeholder="<fmt:message key="store.password" bundle="${rb}"/> ">
            </div>
            <div class="form-group">
                <label for="exampleInputPassword2"><fmt:message key="store.passwordOnceMore" bundle="${rb}"/> </label>
                <input type="password" name="password2" class="form-control" id="exampleInputPassword2" placeholder="<fmt:message key="store.password" bundle="${rb}"/> ">
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