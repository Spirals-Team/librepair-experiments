<html>

<head>
    <title>Ticket list</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script>

        $(document).ready(function () {

            $('#addOneRow').on('click', function () {

                var html = $(".ticket_input_row")[0].outerHTML;
                var appendTr = jQuery(html);
                appendTr.insertAfter(".ticket_input_row:last");

                $('.ticket_input_row').each(function (i, row) {
                    $(row).find(':input').each(function (j, input) {
                        input.name = input.name.replace(/\[\d+\]/g, '[' + i + ']')
                    });
                });
            });
        });
    </script>
</head>

<body>
<div class="container">
    <div class="jumbotron" style="padding: 1px 20px 20px 20px; margin: 0;">
        <h2><a href="./">Main page</a></h2>
        <hr>
        <h3><a href="./events">Back</a></h3>
        <h3>Ticket list</h3>

        <form action="./tickets/showPrice" method="post">
            <input type="hidden" name="eventId" value="${eventId}">
            <input type="hidden" name="airDate" value="${airDate}">

            <div class="row">
                <div class="col-lg-5">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Air Date</th>
                            <th>Seat</th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr class="ticket_input_row">
                            <td>
                            ${airDate.format("dd.MM.yyyy HH:mm")}
                            </td>
                            <td>
                                <input type="text" class="form-control" name="seats[0]" placeholder="Enter seat"
                                       required>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="col-lg-7"></div>
            </div>

            <div class="btn-group-vertical">
                <button type="button" class="btn btn-success" id="addOneRow">+</button>
                <button type="submit" class="btn btn-primary">Check price</button>
            </div>
            <!--with CSRF-->
            <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->

        </form>

        <h3>Purchased tickets</h3>

        <table class="table">
            <thead>
            <tr>
                <th>Id</th>
                <th>Event</th>
                <th>Air Date</th>
                <th>Seat</th>
            </tr>
            </thead>
            <tbody>

            <#list purchasedTickets as ticket>
            <tr>
                <td>${ticket.id}</td>
                <td>${ticket.eventName}</td>
                <td>${ticket.dateTime.format("dd.MM.yyyy HH:mm")}</td>
                <td>${ticket.seat}</td>
            </tr>

            </#list>
            </tbody>
        </table>

    <#assign length = ((purchasedTickets?size)!0)>
    <#if length gt 0>
        <a class="btn btn-link btn-xs" href="./tickets/pdf?eventId=${eventId}&dateTime=${airDate}" role="button">Download as PDF</a>
    </#if>

    </div>
</div>
</body>

</html>