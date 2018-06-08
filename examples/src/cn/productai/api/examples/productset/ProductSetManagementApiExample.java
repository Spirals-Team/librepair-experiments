package cn.productai.api.examples.productset;

import cn.productai.api.core.IWebClient;
import cn.productai.api.core.enums.LanguageType;
import cn.productai.api.core.exceptions.ClientException;
import cn.productai.api.examples.IExample;
import cn.productai.api.pai.entity.productset.*;

import java.io.File;

public class ProductSetManagementApiExample implements IExample {

    private String productSetId = "";
    private String productId = "";
    private Object productSets;

    @Override
    public void run(IWebClient client) {
        // 创建商品集
        productSetId = createProductSet(client);

        // 获取所有商品集
        getProductSets(client);

        // 更新商品集
        updateProductSet(client, productSetId);

        // 通过商品集 ID 删除商品集
        deleteProductSet(client, productSetId);

        sleep(500L);

        // 通过商品集 ID 获取商品集
        productSetId = createProductSet(client);
        getProductSet(client, productSetId);

        // 添加商品到商品集中
        addProduct(client, productSetId);

        // 批量添加商品到商品集中
        batchAddProducts(client, productSetId);

        // 获取商品集中的所有商品
        getProducts(client, productSetId, new String[]{"_001", "_002"});

        // 批量删除商品
        batchDeleteProducts(client, productSetId);

        // 通过商品 IDs 删除商品
        deleteProductsByProductIDs(client, productSetId, new String[]{"_001", "_002"});

        // 创建服务
        createService(client, productSetId);

        sleep(500L);

        // 删除所有商品集
        deleteAllProductSets(client);
    }

    private void batchDeleteProducts(IWebClient client, String productSetId) {
        System.out.println("==>  Demo - 通过商品 IDs 删除商品  <==");

        String filePath = "cn/productai/api/examples/files/batch_delete_products.csv";
        DeleteProductsRequest request = new DeleteProductsRequest(productSetId);
        request.setCsvFile(new File(ClassLoader.getSystemResource(filePath).getFile()));
        request.setLanguage(LanguageType.Chinese);

        try {
            DeleteProductsResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));
            System.out.println("==============================Result==============================");

        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(
                    String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                            e.getErrorCode(),
                            e.getErrorMessage()));
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println(
                    String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                            e.getErrorCode(),
                            e.getErrorMessage(),
                            e.getRequestId()));
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }

    private void batchAddProducts(IWebClient client, String productSetId) {
        System.out.println("==>  Demo - 批量添加商品  <==");

        String filePath = "cn/productai/api/examples/files/batch_add_products.csv";
        AddProductsBatchRequest request = new AddProductsBatchRequest(productSetId);
        request.setCsvFile(new File(ClassLoader.getSystemResource(filePath).getFile()));
        request.setLanguage(LanguageType.Chinese);

        try {
            AddProductsBatchResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));
            System.out.println("==============================Result==============================");

        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(
                    String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                            e.getErrorCode(),
                            e.getErrorMessage()));
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println(
                    String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                            e.getErrorCode(),
                            e.getErrorMessage(),
                            e.getRequestId()));
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }

    private void createService(IWebClient client, String productSetId) {
        System.out.println("==>  Demo - 创建服务  <==");

        CreateServiceRequest request = new CreateServiceRequest(
                productSetId,
                "product-service",
                "fashion_v3");

        try {
            CreateServiceResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));
            System.out.println("==============================Result==============================");

        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(
                    String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                            e.getErrorCode(),
                            e.getErrorMessage()));
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println(
                    String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                            e.getErrorCode(),
                            e.getErrorMessage(),
                            e.getRequestId()));
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }

    private void deleteProductsByProductIDs(IWebClient client, String productSetId, String[] ids) {
        System.out.println("==>  Demo - 删除商品  <==");

        DeleteProductsByProductSetRequest request = new DeleteProductsByProductSetRequest(productSetId, ids);

        try {
            DeleteProductsByProductSetResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));
            System.out.println("==============================Result==============================");

        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(
                    String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                            e.getErrorCode(),
                            e.getErrorMessage()));
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println(
                    String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                            e.getErrorCode(),
                            e.getErrorMessage(),
                            e.getRequestId()));
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }

    private void addProduct(IWebClient client,
                            String productSetId) {
        System.out.println("==>  Demo - 添加两个商品到商品集  <==");

        String productId = "_001";
        String price = "35.6";
        String keywords = "皮鞋";
        String imageUrl = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1987650312,1979230819&fm=27&gp=0.jpg";
        String meta = "白色";
        String tags = "短裙";

        AddProductRequest request1 = new AddProductRequest(
                productSetId, productId, price, keywords, imageUrl, meta, tags);

        try {
            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("Response Json : %s", client.getResponse(request1).getResponseJsonString()));
            System.out.println("==============================Result==============================");

        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(
                    String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                            e.getErrorCode(),
                            e.getErrorMessage()));
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println(
                    String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                            e.getErrorCode(),
                            e.getErrorMessage(),
                            e.getRequestId()));
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }

    private void getProducts(IWebClient client, String productSetId, String[] productIds) {
        System.out.println("==>  Demo - 获取商品集中所有商品  <==");
        System.out.println(String.format("ProductSetId: %s", productSetId));

        GetProductsByProductSetRequest request = new GetProductsByProductSetRequest(productSetId, productIds);

        try {
            GetProductsByProductSetResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));
            System.out.println("==============================Result==============================");

        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(
                    String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                            e.getErrorCode(),
                            e.getErrorMessage()));
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println(
                    String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                            e.getErrorCode(),
                            e.getErrorMessage(),
                            e.getRequestId()));
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }

    private void getProductSet(IWebClient client, String productSetId) {
        System.out.println("==>  Demo - 查找商品集  <==");

        GetProductSetRequest request = new GetProductSetRequest(productSetId);

        try {
            GetProductSetResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(
                    String.format(
                            "ProductSet Name - %s, Description - %s",
                            response.getName(),
                            response.getDescription()));
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));
            System.out.println("==============================Result==============================");

        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(
                    String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                            e.getErrorCode(),
                            e.getErrorMessage()));
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println(
                    String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                            e.getErrorCode(),
                            e.getErrorMessage(),
                            e.getRequestId()));
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }

    private void updateProductSet(IWebClient client, String productSetId) {
        System.out.println("==>  Demo - 删除所有商品集  <==");

        UpdateProductSetRequest request = new UpdateProductSetRequest(
                productSetId,
                "New ProductSet Name",
                "Another product set description.");

        try {
            UpdateProductSetResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));
            System.out.println("==============================Result==============================");

        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(
                    String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                            e.getErrorCode(),
                            e.getErrorMessage()));
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println(
                    String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                            e.getErrorCode(),
                            e.getErrorMessage(),
                            e.getRequestId()));
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }

    private void deleteAllProductSets(IWebClient client) {
        System.out.println("==>  Demo - 删除所有商品集  <==");

        String token = "token";
        String b64Token = "dG9rZW4=";
        DeleteAllProductSetRequest request = new DeleteAllProductSetRequest(token, b64Token);

        try {
            DeleteAllProductSetResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));
            System.out.println("==============================Result==============================");

        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(
                    String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                            e.getErrorCode(),
                            e.getErrorMessage()));
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println(
                    String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                            e.getErrorCode(),
                            e.getErrorMessage(),
                            e.getRequestId()));
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }

    private void deleteProductSet(IWebClient client, String productSetId) {
        System.out.println("==>  Demo - 通过 ID 删除商品集  <==");

        DeleteProductSetRequest request = new DeleteProductSetRequest(productSetId);

        try {
            DeleteProductSetResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));
            System.out.println("==============================Result==============================");

        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(
                    String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                            e.getErrorCode(),
                            e.getErrorMessage()));
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println(
                    String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                            e.getErrorCode(),
                            e.getErrorMessage(),
                            e.getRequestId()));
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }

    private String createProductSet(IWebClient client) {
        System.out.println("==>  Demo - 新增商品集  <==");

        CreateProductSetRequest request = new CreateProductSetRequest(
                "My Product Set",
                "This is my product set of fashion images.");

        try {
            CreateProductSetResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(
                    String.format(
                            "ProductSet Name - %s, Description - %s",
                            response.getName(),
                            response.getDescription()));
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));
            System.out.println("==============================Result==============================");

            return response.getId();
        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(
                    String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                            e.getErrorCode(),
                            e.getErrorMessage()));
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println(
                    String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                            e.getErrorCode(),
                            e.getErrorMessage(),
                            e.getRequestId()));
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }

        return null;
    }

    public void getProductSets(IWebClient client) {
        System.out.println("==>  Demo - 获取所有商品集  <==");

        GetAllProductSetsRequest request = new GetAllProductSetsRequest();

        try {
            GetAllProductSetsResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            for (ProductSet ps : response.getResults()) {
                System.out.println(String.format("Product Set, id: %s, name: name", ps.getId(), ps.getName()));
            }
            System.out.println(String.format("Response Json : %s", response.getResponseJsonString()));
            System.out.println("==============================Result==============================");

        } catch (cn.productai.api.core.exceptions.ServerException e) {
            System.out.println(
                    String.format("ServerException occurred. ErrorCode: %s \r\n ErrorMessage: %s",
                            e.getErrorCode(),
                            e.getErrorMessage()));
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println(
                    String.format("ClientException occurred. ErrorCode: %s \r\n ErrorMessage: %s \r\n RequestId: %s",
                            e.getErrorCode(),
                            e.getErrorMessage(),
                            e.getRequestId()));
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(String.format("%s occurred. ErrorMessage: %s", e.getClass().getName(), e.getMessage()));
            e.printStackTrace();
        }
    }

    private void sleep(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}