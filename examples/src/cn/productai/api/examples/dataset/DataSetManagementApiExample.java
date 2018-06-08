package cn.productai.api.examples.dataset;

import cn.productai.api.core.IWebClient;
import cn.productai.api.core.exceptions.ClientException;
import cn.productai.api.examples.IExample;
import cn.productai.api.pai.entity.dataset.*;

public class DataSetManagementApiExample implements IExample {

    @Override
    public void run(IWebClient client) {

        // 获取所有的数据集信息
        GetAll(client);

        // 新增数据集
        Add(client);

        // 获取数据集信息
        Get(client);

        // 更新数据集
        Update(client);

        //删除数据集
        Delete(client);
    }

    private String _dataSetId = "";

    private void Add(IWebClient client) {
        System.out.println("==>  Demo - 新增数据集  <==");

        CreateDataSetRequest request = new CreateDataSetRequest("MyExampleDs", "MyDs-Description");

        try {
            CreateDataSetResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("DataSet Id - %s", response.getDataSetId()));
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));

            _dataSetId = response.getDataSetId();

            System.out.println("==============================Result==============================");
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

    private void Get(IWebClient client) {
        System.out.println("==>  Demo - 获取数据集信息  <==");

        GetDataSetInfoRequest request = new GetDataSetInfoRequest(_dataSetId);

        try {
            GetDataSetInfoResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));

            System.out.println("==============================Result==============================");
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

    private void Update(IWebClient client) {
        System.out.println("==>  Demo - 更新数据集信息  <==");

        UpdateDataSetRequest request = new UpdateDataSetRequest(_dataSetId, "MyExampleDs-Updated", "MyDs-Description-Updated");

        try {
            UpdateDataSetResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));

            System.out.println("==============================Result==============================");
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

    private void Delete(IWebClient client) {
        System.out.println("==>  Demo - 删除数据集信息  <==");

        DeleteDataSetRequest request = new DeleteDataSetRequest(_dataSetId);

        try {
            DeleteDataSetResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));

            System.out.println("==============================Result==============================");
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

    private void GetAll(IWebClient client) {
        System.out.println("==>  Demo - 获取所有数据集信息  <==");

        GetAllDataSetsRequest request = new GetAllDataSetsRequest();

        try {
            GetAllDataSetsResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));

            System.out.println("==============================Result==============================");
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