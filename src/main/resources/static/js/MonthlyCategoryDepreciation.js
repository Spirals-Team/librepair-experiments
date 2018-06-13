/*!
 Used to consume restful service on the client side drawing tables to show the
 fixed assets that have been uploaded in the server

 ©2018 Edwin Njeru.
*/
$(document).ready(function () {

    /* Ajax to list down data into dataTable from a
     * restful service
     */
    var table = $('#monthlyCategoryDepreciationTable').DataTable({

        "sAjaxSource":"/reports/depreciations/category/data",
        "sAjaxDataProp":"",
        "order":[[0,"asc"]],
        "aoColumns":[
            {"mData":"id"},
            {"mData":"categoryName"},
            {"mData":"year"},
            {"mData":"jan"},
            {"mData":"feb"},
            {"mData":"mar"},
            {"mData":"apr"},
            {"mData":"may"},
            {"mData":"jun"},
            {"mData":"jul"},
            {"mData":"aug"},
            {"mData":"sep"},
            {"mData":"oct"},
            {"mData":"nov"},
            {"mData":"dec"}
        ],

        /* really, really hope this works man! */
        dom: 'Bfrtip',
        buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print', 'colvis'
        ]
    })

});