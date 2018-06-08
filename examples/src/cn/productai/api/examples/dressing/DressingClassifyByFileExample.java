package cn.productai.api.examples.dressing;

import cn.productai.api.core.IWebClient;
import cn.productai.api.core.enums.LanguageType;
import cn.productai.api.core.exceptions.ClientException;
import cn.productai.api.examples.IExample;
import cn.productai.api.pai.entity.dressing.DressingClassifyByImageFileRequest;
import cn.productai.api.pai.entity.dressing.DressingClassifyResponse;
import cn.productai.api.pai.response.DressingClassifyResult;

import java.io.File;

public class DressingClassifyByFileExample implements IExample {

    @Override
    public void run(IWebClient client) {

        System.out.println("==>  Demo - 时尚分析服务  <==");
        System.out.println("时尚智能分析服务可对用户所提交图片中与时尚元素相关的内容进行分析，分析包含：\r\n");
        System.out.println("    1. 找出图中的服饰商品，这些商品的类型及颜色成分;");
        System.out.println("    2. 判断出图中的服饰、人物所包含的服装款式、图案花型等时尚元素，并以标签的形式返回。");

        DressingClassifyByImageFileRequest request = new   DressingClassifyByImageFileRequest("0-0-1-1");
        request.setImageFile(new File(this.getClass().getResource("/").getPath() + "cn/productai/api/examples/files/f10.jpg"));
        request.setLanguage(LanguageType.Chinese);

        // 可选参数列表，不需要请注释掉
        request.getOptions().put("ret_img_tags", "1");

        try {
            DressingClassifyResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            // 原始的JSON返回
            System.out.println(response.getResponseJsonString());

            // 访问反序列化后的实体
            if (response.getResults() != null) {
                for (DressingClassifyResult result : response.getResults()) {
                    System.out.println(result.getItem());
                }
            }

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
