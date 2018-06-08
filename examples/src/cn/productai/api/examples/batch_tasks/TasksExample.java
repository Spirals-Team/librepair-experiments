package cn.productai.api.examples.batch_tasks;

import cn.productai.api.core.IWebClient;
import cn.productai.api.core.exceptions.ClientException;
import cn.productai.api.examples.IExample;
import cn.productai.api.pai.entity.batch.*;

import java.io.File;

public class TasksExample implements IExample {

    @Override
    public void run(IWebClient client) {

        System.out.println("Demo - 批处理操作");

        System.out.println("Demo - 搜索批量任务");
        SearchTasks(client);

        System.out.println("Demo - 获取支持批量操作的服务列表");
        GetSupportServiceIds(client);

        System.out.println("Demo - 通过CSV文件（一行一个URL）创建批量任务");
        String task_id = CreateTaskByCsvFile(client);

        System.out.println("Demo - 启动批量任务");
        if (task_id != null && !task_id.isEmpty())
            StartTask(client, task_id);

        System.out.println("Demo - 查询批量任务进度");
        if (task_id != null && !task_id.isEmpty())
            WaitForTaskToCompleted(client, task_id);

        System.out.println("Demo - 取消批量任务");
        // CancelTask(client,task_id);
    }

    /**
     * 获取所有支持批处理的服务
     *
     * @param client IWebClient
     */
    private void GetSupportServiceIds(IWebClient client) {
        GetSupportServicesRequest request = new GetSupportServicesRequest();

        try {
            GetSupportServicesResponse response = client.getResponse(request);

            System.out.println(response.getResponseJsonString());

        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                    e.getErrorCode(),
                    e.getErrorMessage()));
            e.printStackTrace();

        } catch (ClientException e) {
            System.out.println(String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                    e.getErrorCode(),
                    e.getErrorMessage(),
                    e.getRequestId()));
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * 通过CSV文件创建批处理任务
     *
     * @param client IWebClient
     * @return TaskId
     */
    private String CreateTaskByCsvFile(IWebClient client) {
        CreateTaskByFileRequest request = new CreateTaskByFileRequest("_0000039", new File(this.getClass().getResource("/").getPath() + "cn/productai/api/examples/files/urls-001.csv"));

        try {
            CreateTaskByFileResponse response = client.getResponse(request);
            System.out.println(response.getResponseJsonString());

            return response.getTaskInfo().getTaskId();

        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                    e.getErrorCode(),
                    e.getErrorMessage()));
            e.printStackTrace();

        } catch (ClientException e) {
            System.out.println(String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                    e.getErrorCode(),
                    e.getErrorMessage(),
                    e.getRequestId()));
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 启动任务
     *
     * @param client  IWebClient
     * @param task_id task_id
     */
    private void StartTask(IWebClient client, String task_id) {
        StartTaskRequest request = new StartTaskRequest(task_id);

        try {
            StartTaskResponse response = client.getResponse(request);
            System.out.println(response.getResponseJsonString());
        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                    e.getErrorCode(),
                    e.getErrorMessage()));
            e.printStackTrace();

        } catch (ClientException e) {
            System.out.println(String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                    e.getErrorCode(),
                    e.getErrorMessage(),
                    e.getRequestId()));
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * 获取任务状态
     *
     * @param client  IWebClient
     * @param task_id task_id
     */
    private void WaitForTaskToCompleted(IWebClient client, String task_id) {
        PollTaskStatusRequest request = new PollTaskStatusRequest(task_id);

        try {
            PollTaskStatusResponse response = client.getResponse(request);

            System.out.println(response.getTaskInfo().getState());
            while (!response.getTaskInfo().getState().equals("FAILURE") & !response.getTaskInfo().getState().equals("SUCCESS")) {
                System.out.println(String.format("Response: \r\n%s", response.getResponseJsonString()));
                Thread.sleep(5000);
                response = client.getResponse(request);
            }

        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                    e.getErrorCode(),
                    e.getErrorMessage()));
            e.printStackTrace();

        } catch (ClientException e) {
            System.out.println(String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                    e.getErrorCode(),
                    e.getErrorMessage(),
                    e.getRequestId()));
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * 取消任务
     * 只有任务在队列中（状态字段state为PENDING）时，任务可以被取消．一旦任务开始运行，则不可被取消．
     *
     * @param client  IWebClient
     * @param task_id task_id
     */
    private void CancelTask(IWebClient client, String task_id) {
        CancelTaskRequest request = new CancelTaskRequest(task_id);

        try {
            CancelTaskResponse response = client.getResponse(request);

            System.out.println(String.format("Response: \r\n%s", response.getResponseJsonString()));

        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                    e.getErrorCode(),
                    e.getErrorMessage()));
            e.printStackTrace();

        } catch (ClientException e) {
            System.out.println(String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                    e.getErrorCode(),
                    e.getErrorMessage(),
                    e.getRequestId()));
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * Search Tasks
     *
     * @param client IWebClient
     */
    private void SearchTasks(IWebClient client) {
        SearchTaskRequest request = new SearchTaskRequest("2018-01-01", "2018-01-10");

        try {
            SearchTaskResponse response = client.getResponse(request);

            System.out.println(String.format("Response: \r\n%s", response.getResponseJsonString()));
        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                    e.getErrorCode(),
                    e.getErrorMessage()));
            e.printStackTrace();

        } catch (ClientException e) {
            System.out.println(String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                    e.getErrorCode(),
                    e.getErrorMessage(),
                    e.getRequestId()));
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }
}
