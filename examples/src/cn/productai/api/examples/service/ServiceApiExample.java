package cn.productai.api.examples.service;

import cn.productai.api.core.IWebClient;
import cn.productai.api.core.enums.SearchScenario;
import cn.productai.api.core.exceptions.ClientException;
import cn.productai.api.examples.IExample;
import cn.productai.api.pai.entity.service.*;

public class ServiceApiExample implements IExample {

    @Override
    public void run(IWebClient client) {

        // 获取所有服务信息
        GetAll(client);

        // 新增服务
        Add(client);

        // 获取服务信息
        Get(client);

        // 更新服务信息
        Update(client);

        //删除服务
        Delete(client);
    }

    private String dataSetId;
    private String _serviceId = "";

    public String getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(String dataSetId) {
        this.dataSetId = dataSetId;
    }

    private void Add(IWebClient client) {
        System.out.println("==>  Demo - 新增搜索服务  <==");

        CreateSearchServiceRequest request = new CreateSearchServiceRequest(this.getDataSetId(), "MyExampleService", SearchScenario.Furniture_V6);

        try {
            CreateSearchServiceResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("Service Id - %s", response.getServiceId()));
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));

            _serviceId = response.getServiceId();

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
        System.out.println("==>  Demo - 获取搜索服务信息  <==");

        GetServiceInfoRequest request = new GetServiceInfoRequest(_serviceId);

        try {
            GetServiceInfoResponse response = client.getResponse(request);

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
        System.out.println("==>  Demo - 获取所有搜索服务信息  <==");

        GetAllServicesRequest request = new GetAllServicesRequest();

        try {
            GetAllServicesResponse response = client.getResponse(request);

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
        System.out.println("==>  Demo - 更新搜索服务信息  <==");

        UpdateServiceRequest request = new UpdateServiceRequest(_serviceId, "SmartImageSearch-Updated");

        try {
            UpdateServiceResponse response = client.getResponse(request);

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
        System.out.println("==>  Demo - 删除搜索服务  <==");

        DeleteServiceRequest request = new DeleteServiceRequest(_serviceId);

        try {
            DeleteServiceResponse response = client.getResponse(request);

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