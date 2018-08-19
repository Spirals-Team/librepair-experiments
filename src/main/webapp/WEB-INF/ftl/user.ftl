<html>

<head>
    <title>User</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
<div class="container">
    <div class="jumbotron" style="padding: 1px 20px 20px 20px; margin: 0;">
        <h2><a href="../">Main page</a></h2>
        <hr>
        <h3><a href="javascript: window.history.go(-1)">Back</a></h3>
        <h3>User</h3>

        <form action="addUser" method="post">
            <input type="hidden" name="id" value="${(user.id)!""}">
            <#--<div class="form-group">-->
                <#--<label for="id">Id:</label>-->
                <#--<input type="text" class="form-control" id="id" name="id" readonly-->
                       <#--value="${(user.id)!""}">-->
            <#--</div>-->
            <div class="form-group">
                <label for="firstName">First Name:</label>
                <input type="text" class="form-control" id="firstName" name="firstName" placeholder="Enter first name"
                       required="required"
                       value="${(user.firstName)!""}">
            </div>
            <div class="form-group">
                <label for="lastName">Last Name:</label>
                <input type="text" class="form-control" id="lastName" name="lastName" placeholder="Enter last name"
                       required="required"
                       value="${(user.lastName)!""}">
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="text" class="form-control" id="email" name="email" placeholder="Enter email"
                       required="required"
                       value="${(user.email)!""}">
            </div>
            <div class="form-group">
                <label for="birthday">Birthday:</label>
                <input type="date" class="form-control" id="birthday" name="birthday" placeholder="Enter birthday"
                       required="required"
                       value="${(user.birthday)!""}">
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" class="form-control" id="password" name="password"
                ${(user.id??)?then("", "placeholder=\"Enter password\" required=\"required\"")}>
            </div>
            <div class="form-group" required="required">
                <label for="roles">Roles:</label>
                <div class="form-group" id="roles">

                <#if user.rolesToString?? && (user.rolesToString)?contains("REGISTERED_USER")>
                    <label class="checkbox-inline">
                        <input name="roles" type="checkbox" value="REGISTERED_USER" checked>REGISTERED_USER</label>
                <#else>
                    <label class="checkbox-inline">
                        <input name="roles" type="checkbox" value="REGISTERED_USER">REGISTERED_USER</label>
                </#if>

                <#if user.rolesToString?? && (user.rolesToString)?contains("BOOKING_MANAGER")>
                    <label class="checkbox-inline">
                        <input name="roles" type="checkbox" value="BOOKING_MANAGER" checked>BOOKING_MANAGER</label>
                <#else>
                    <label class="checkbox-inline">
                        <input name="roles" type="checkbox" value="BOOKING_MANAGER">BOOKING_MANAGER</label>
                </#if>
                </div>
            </div>
            <div class="btn-group">
                <button type="button" class="btn btn-warning" onclick="location.href='../users'">Cancel</button>
            <#--<button type="button" class="btn btn-warning" onclick="location.href='javascript: window.history.go(-1)'">Cancel</button>-->
                <button type="submit" class="btn btn-primary">Save</button>
            </div>
            <!--with CSRF-->
            <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->

        </form>

    </div>
</div>
</body>

</html>