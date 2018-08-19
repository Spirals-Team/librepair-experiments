<html>

<head>
    <title>Auditorium list</title>
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
        <h3>Auditorium list</h3>

        <table class="table">
            <thead>
            <tr>
                <th>Name</th>
                <th>Number of seats</th>
            </tr>
            </thead>
            <tbody>
            <#list auditoriums as auditorium>
            <tr>
                <td><a href="./auditorium?name=${auditorium.name}">${auditorium.name}</a></td>
                <td>${auditorium.numberOfSeats}</td>
            </tr>

            </#list>
            </tbody>
        </table>
    </div>
</div>
</body>

</html>