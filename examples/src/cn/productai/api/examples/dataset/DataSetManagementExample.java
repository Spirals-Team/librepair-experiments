package cn.productai.api.examples.dataset;

import cn.productai.api.core.IWebClient;
import cn.productai.api.core.enums.LanguageType;
import cn.productai.api.core.exceptions.ClientException;
import cn.productai.api.examples.IExample;
import cn.productai.api.pai.entity.dataset.DataSetBaseResponse;
import cn.productai.api.pai.entity.dataset.DataSetBatchAddRequest;

import java.io.File;

/**
 * Created by Zhong Wang on 2017/7/5.
 * 数据集操作 - 向数据集增加多条数据
 * https://developers.productai.cn/zh/reference/image_search#向图片集批量上传图片
 */
public class DataSetManagementExample implements IExample {

    @Override
    public void run(IWebClient client) {

        System.out.println("==>  Demo - 向数据集增加多条数据  <==");
        System.out.println("See https://developers.productai.cn/zh/reference/image_search#向图片集批量上传图片 for details.\r\n");

        DataSetBatchAddRequest request = new DataSetBatchAddRequest("lqn2jj6z");
        request.setCsvFile(new File(this.getClass().getResource("/").getPath() + "cn/productai/api/examples/files/example.csv"));
        request.setLanguage(LanguageType.Chinese);

        try {
            DataSetBaseResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // access the response directly
            System.out.println(String.format("LastModifiedTime - %s", response.getLastModifiedTime()));

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
