package cn.productai.api.examples.classify;

import cn.productai.api.core.IWebClient;
import cn.productai.api.core.enums.LanguageType;
import cn.productai.api.core.exceptions.ClientException;
import cn.productai.api.examples.IExample;
import cn.productai.api.pai.entity.classify.ClassifyByImageUrlRequest;
import cn.productai.api.pai.entity.classify.ClassifyResponse;
import cn.productai.api.pai.response.ClassifyResult;

/**
 * Created by Zhong Wang on 2017/7/5.
 * 场景分析与标注
 * https://developers.productai.cn/zh/reference/classify#场景分析与标注-v1-0
 */
public class ClassifyByUrlExample implements IExample {

    @Override
    public void run(IWebClient client) {

        System.out.println("==>  Demo - 场景分析与标注  <==");
        System.out.println("See https://developers.productai.cn/zh/reference/classify#场景分析与标注-v1-0 for details.\r\n");

        ClassifyByImageUrlRequest request = new ClassifyByImageUrlRequest("classify", "_0000039");
        request.setUrl("http://productai.cn/img/f10.jpg");
        request.setLanguage(LanguageType.Chinese);
        request.setLoc("0.1-0.1-1-1");

        // you can pass the extra paras to the request
        request.getOptions().put("para1", "1");
        request.getOptions().put("para2", "中文");
        request.getOptions().put("para3", "value3");

        try {
            ClassifyResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            for (ClassifyResult r : response.getResults()) {
                // access the response directly
                System.out.println(String.format("%s - %s", r.getCategory(), r.getScore()));
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
