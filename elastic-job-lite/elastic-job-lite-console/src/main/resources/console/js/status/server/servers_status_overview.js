$(function() {
    renderServersOverview();
    bindServerStatusDetailButton();
});

function renderServersOverview() {
    $("#servers-overview-tbl").bootstrapTable({
        url: "/api/servers",
        cache: false,
        columns: 
        [{
            field: "serverIp",
            title: "服务器IP",
            sortable: "true"
        }, {
            field: "instanceId",
            title: "服务器实例ID",
            sortable: "true"
        }, {
            field: "status",
            title: "状态",
            sortable: "true",
            formatter: "statusFormatter"
        }, {
            field: "operation",
            title: "操作",
            formatter: "generateOperationButtons"
        }]
    });
}

function statusFormatter(value) {
    switch(value) {
        case "OK":
            return "<span class='label label-success'>全部可用</span>";
            break;
        case "PARTIAL_ALIVE":
            return "<span class='label label-warning'>部分可用</span>";
            break;
        case "ALL_CRASHED":
            return "<span class='label label-danger'>全部宕机</span>";
            break;
    }
}

function generateOperationButtons(val, row) {
    return "<button operation='server-status-detail' class='btn-xs btn-info' server-ip='" + row.serverIp + "' server-instance-id='" + row.instanceId + "'>详情</button>";
}

function bindServerStatusDetailButton() {
    $(document).on("click", "button[operation='server-status-detail'][data-toggle!='modal']", function(event) {
        var serverIp = $(event.currentTarget).attr("server-ip");
        var serverInstanceId = $(event.currentTarget).attr("server-instance-id");
        window.location = "index.html?server-ip=" + serverIp + "&server-instance-id=" + serverInstanceId;
    });
}
