package cn.productai.api.pai.entity.detect;

import cn.productai.api.core.base.BaseResponse;
import cn.productai.api.pai.response.DetectResult;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
public class DetectResponse extends BaseResponse {

    @JsonProperty("boxes_detected")
    private DetectResult[] DetectedBoxes;

    @JsonProperty("detecttime")
    private double DetectTime;

    @JsonProperty("image_id")
    private String imageId;

    public DetectResult[] getDetectedBoxes() {
        return DetectedBoxes;
    }

    public void setDetectedBoxes(DetectResult[] detectedBoxes) {
        DetectedBoxes = detectedBoxes;
    }

    public double getDetectTime() {
        return DetectTime;
    }

    public void setDetectTime(double detectTime) {
        DetectTime = detectTime;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
