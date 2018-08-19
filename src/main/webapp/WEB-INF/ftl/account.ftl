<html>

<head>
    <title>Account</title>
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
        <h3>Account</h3>

        <form action="addAccount" method="post">

            <div class="form-group">
                <label for="id">Id:</label>
                <input type="text" class="form-control" id="id" name="id" readonly
                       value="${(account.id)!""}">
            </div>
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" class="form-control" id="name" name="name"
                       required="required"
                       value="${(account.name)!""}">
            </div>

            <div class="form-group">
                <label for="money">Money:</label>
                <input type="text" class="form-control" id="money" name="money"
                       required="required"
                       pattern="(\d{1,5})([\.])(\d{2})"
                       value="${(account.money?string(",##0.00"))!"100.00"}">
            </div>

            <div class="btn-group">
                <button type="button" class="btn btn-warning" onclick="location.href='../accounts'">Cancel</button>
                <button type="submit" class="btn btn-primary">Save</button>
            </div>

            <!--with CSRF-->
            <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->

        </form>

    </div>
</div>
</body>

</html>