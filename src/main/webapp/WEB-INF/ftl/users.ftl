<html>

<head>
    <title>User list</title>
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
        <h3>User list</h3>
        <a href="./user/add">Add new</a>
        <table class="table">
            <thead>
            <tr>
                <th>Id</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Birthday</th>
                <th>Roles</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
            <tr>
                <td><a href="./user/id?id=${user.id}">${user.id}</a></td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td><a href="./user/email?email=${user.email}">${user.email}</a></td>
                <td>${user.birthday}</td>
                <td>${user.rolesToString}</td>
                <td>
                    <div class="btn-group">
                        <input type="button"
                               class="btn btn-primary"
                               onclick="location.href='./user/id?id=${user.id}'"
                               value="Edit">
                        <input type="button"
                               class="btn btn-danger"
                               onclick="location.href='./user/removeUser?id=${user.id}'"
                               value="Delete">
                    </div>
                </td>
            </tr>

            </#list>
            </tbody>
        </table>
        <hr>
    <#--<form class="form-horizontal" action="user/addUsersByFile?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data" method="post">-->
        <form class="form-horizontal" action="user/addUsersByFile"
              enctype="multipart/form-data" method="post">
            <div class="form-group">
                <label class="control-label col-sm-2" for="usersFile">File to upload:</label>
                <div class="col-sm-10">
                    <input class="form-control" type="file" name="file" id="usersFile">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-info">Upload</button>
                </div>
            </div>
        </form>

    </div>
</div>
</body>

</html>