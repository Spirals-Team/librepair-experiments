package cn.productai.api.examples.detect;

import cn.productai.api.core.IWebClient;
import cn.productai.api.core.enums.LanguageType;
import cn.productai.api.core.exceptions.ClientException;
import cn.productai.api.examples.IExample;
import cn.productai.api.pai.entity.detect.DetectByImageUrlRequest;
import cn.productai.api.pai.entity.detect.DetectResponse;
import cn.productai.api.pai.response.DetectResult;

/**
 * Created by Zhong Wang on 2017/8/15.
 * 家具检测与定位
 * https://developers.productai.cn/zh/reference/detect#%E5%AE%B6%E5%85%B7%E6%A3%80%E6%B5%8B%E4%B8%8E%E5%AE%9A%E4%BD%8D-v3-0
 */
public class DetectByUrlExample implements IExample {

    @Override
    public void run(IWebClient client) {

        System.out.println("==>  Demo - 家具检测与定位  <==");
        System.out.println("See https://developers.productai.cn/zh/reference/detect#%E5%AE%B6%E5%85%B7%E6%A3%80%E6%B5%8B%E4%B8%8E%E5%AE%9A%E4%BD%8D-v3-0 for details.\r\n");

        DetectByImageUrlRequest request = new DetectByImageUrlRequest("detect",
                "_0000171",
                "http://cdn.malong.com/users/zhowang/Bed/0004a56f-bbaf-46ac-ad66-8bbe9153e7c8.jpg",
                "0-0-1-1");
        request.setLanguage(LanguageType.Chinese);

        // you can pass the extra paras to the request
        request.getOptions().put("para1", "1");
        request.getOptions().put("para2", "中文");
        request.getOptions().put("para3", "value3");

        try {
            DetectResponse response = client.getResponse(request);

            System.out.println("==============================Result==============================");

            for (DetectResult r : response.getDetectedBoxes()) {
                // access the response directly
                System.out.println(String.format("%s - %s", r.getType(), r.getScore()));
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
