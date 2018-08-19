<html>

<head>
    <title>Account list</title>
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
        <h2><a href="./">Main page</a></h2>
        <hr>
        <h3>Account list</h3>
        <a href="./account/add">Add new</a>
        <table class="table">
            <thead>
            <tr>
                <th>Id</th>
                <th>User Id</th>
                <th>Name</th>
                <th>Money</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <#list accounts as account>
            <tr>
                <td>${account.id}</td>
                <td><a href="./account/id?id=${account.userId}">${account.userId}</a></td>
                <td>${account.name}</td>
                <td>${account.money?string(",##0.00")}</td>
                <td>
                    <div class="btn-group">
                        <input type="button"
                               class="btn btn-primary"
                               onclick="location.href='./account/id?id=${account.id}'"
                               value="Edit">
                        <input type="button"
                               class="btn btn-danger"
                               onclick="location.href='./account/removeAccount?id=${account.id}'"
                               value="Delete">
                    </div>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
        <hr>
    </div>
</div>
</body>

</html>