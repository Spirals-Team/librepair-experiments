$(function() {
    renderJobsOverview();
    bindModifyButton();
});

function renderJobsOverview() {
    $("#jobs-overview-tbl").bootstrapTable({
        url: "/api/jobs",
        cache: false,
        search: true,
        showRefresh: true,
        showColumns: true,
        columns: 
        [{
            field: "jobName",
            title: "作业名",
            sortable: true
        }, {
            field: "jobType",
            title: "作业类型",
            sortable: true
        }, {
            field: "cron",
            title: "cron表达式"
        }, {
            field: "description",
            title: "描述"
        }, {
            field: "operation",
            title: "操作",
            formatter: "generateOperationButtons"
        }]
    });
}

function generateOperationButtons(val, row) {
    return "<button operation='modify-job' class='btn-xs btn-info' job-name='" + row.jobName + "'>修改</button>";
}

function bindModifyButton() {
    $(document).on("click", "button[operation='modify-job'][data-toggle!='modal']", function(event) {
        var jobName = $(event.currentTarget).attr("job-name");
        $.ajax({
            url: "/api/jobs/settings/" + jobName,
            success: function(data) {
                if (null !== data) {
                    $(".box-body").remove();
                    $('#update-job-body').load('html/job/job_config.html', null, function() {
                        $('#data-update-job').modal({backdrop : 'static', keyboard : true});
                        renderJob(data);
                    });
                }
            }
        });
    });
}

function renderJob(data) {
    $("#job-name").attr("value", data.jobName);
    $("#job-type").attr("value", data.jobType);
    $("#job-class").attr("value", data.jobClass);
    $("#sharding-total-count").attr("value", data.shardingTotalCount);
    $("#cron").attr("value", data.cron);
    $("#sharding-item-parameters").text(data.shardingItemParameters);
    $("#job-parameter").attr("value", data.jobParameter);
    $("#monitor-execution").attr("checked", data.monitorExecution);
    $("#failover").attr("checked", data.failover);
    $("#misfire").attr("checked", data.misfire);
    $("#streaming-process").attr("checked", data.streamingProcess);
    $("#max-time-diff-seconds").attr("value", data.maxTimeDiffSeconds);
    $("#monitor-port").attr("value", data.monitorPort);
    $("#job-sharding-strategy-class").attr("value", data.jobShardingStrategyClass);
    $("#executor-service-handler").attr("value", data.jobProperties["executor_service_handler"]);
    $("#job-exception-handler").attr("value", data.jobProperties["job_exception_handler"]);
    $("#reconcile-cycle-time").attr("value", data.reconcileCycleTime);
    $("#description").text(data.description);
    if (!data.monitorExecution) {
        $("#execution-info-tab").addClass("disabled");
    }
    $("#script-command-line").attr("value", data.scriptCommandLine);
    if ("DATAFLOW" === $("#job-type").val()) {
        $("#streaming-process-group").show();
    }
    if ("SCRIPT" === $("#job-type").val()) {
        $("#script-commandLine-group").show();
    }
}
