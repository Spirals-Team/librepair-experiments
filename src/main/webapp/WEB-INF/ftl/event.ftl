<html>

<head>
    <title>Event</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>

        $(document).ready(function () {

            $('#addOneRow').on('click', function () {

                var html = $(".air_date_row")[0].outerHTML;
                var appendTr = jQuery(html);
                appendTr.insertAfter(".air_date_row:last");

                $('.air_date_row').each(function (i, row) {
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
        <h2><a href="../">Main page</a></h2>
        <hr>
        <h3><a href="javascript: window.history.go(-1)">Back</a></h3>
        <h3>Event</h3>

        <form action="addEvent" method="post">

            <div class="form-group">
                <label for="id">Id:</label>
                <input type="text" class="form-control" id="id" name="id" readonly
                       value="${(event.id)!""}">
            </div>

            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Enter name"
                       required="required"
                       value="${(event.name)!""}">
            </div>

            <div class="form-group">
                <label for="basePrice">Base price:</label>
                <input type="text" class="form-control" id="basePrice" name="basePrice" placeholder="Enter Base Price"
                       required="required"
                       value="${(event.basePrice)!""}">
            </div>

            <div class="form-group">
                <label for="ratingGroup">Rating:</label>

                <div class="form-group" id="ratingGroup">
                    <label class="radio-inline">
                        <input ${((event.rating == 'LOW')?then('checked', ''))!''}
                                type="radio" name="rating" value="LOW" required>LOW
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="rating" value="MID"
                               required ${((event.rating == 'MID')?then('checked', ''))!''}>MID
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="rating" value="HIGH"
                               required ${((event.rating == 'HIGH')?then('checked', ''))!''}>HIGH
                    </label>
                </div>
            </div>

            <div class="form-group">
                <label>Air dates and Auditoriums:</label>

            <#assign length = ((event.airDateAuditoriums?size)!0)>
            <#if length gt 0>
                <#assign index=0>
                <#list event.airDateAuditoriums as airAuditorium>
                    <div class="air_date_row form-inline">
                        <input type="datetime-local" class="form-control" name="airDateAuditoriums[${index}].airDate"
                               placeholder="Enter air date"
                               required="required"
                               value="${airAuditorium.airDate}">
                        <select class="form-control" name="airDateAuditoriums[${index}].auditorium">
                            <#list auditoriums as auditorium>
                                <option ${(airAuditorium.auditorium==auditorium)?then("selected", "")}>${auditorium.name}</option>
                            </#list>
                        </select>
                    </div>
                    <#assign index++>
                </#list>
            <#else>
                <div class="air_date_row form-inline">
                    <input type="datetime-local" class="form-control" name="airDateAuditoriums[0].airDate"
                           placeholder="Enter air date"
                           required="required">
                    <select class="form-control" name="airDateAuditoriums[0].auditorium">
                        <#list auditoriums as auditorium>
                            <option>${auditorium.name}</option>
                        </#list>
                    </select>
                </div>
            </#if>

                <button type="button" class="btn btn-success" id="addOneRow">+</button>
            </div>


            <div class="btn-group">
                <button type="button" class="btn btn-warning" onclick="location.href='../events'">Cancel</button>
                <button type="submit" class="btn btn-primary">Save</button>
            </div>
            <!--with CSRF-->
            <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
        </form>
    </div>
</div>
</body>

</html>