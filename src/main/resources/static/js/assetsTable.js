/*!
 Used to consume restful service on the client side drawing tables to show the
 fixed assets that have been uploaded in the server

 ©2018 Edwin Njeru.
*/
$(document).ready(function () {

    /* Ajax to list down data into dataTable from a
     * restful service
     */
    var table = $('#assetlist').DataTable({

        "sAjaxSource":"/listing/assets/data",
        "sAjaxDataProp":"",
        "order":[[0,"asc"]],
        "aoColumns":[
            {"mData":"id"},
            {"mData":"solId"},
            {"mData":"barcode"},
            {"mData":"assetDescription"},
            {"mData":"purchaseDate"},
            {"mData":"category"},
            {"mData":"purchaseCost"},
            {"mData":"netBookValue"}
        ],

        /* really, really hope this works man! */
        dom: 'Bfrtip',
        buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print', 'colvis'
        ]
    })

});



