package cn.productai.api.pai.entity.dressing;

import cn.productai.api.pai.base.CallApiByImageFileBaseRequest;

import java.io.File;

/**
 * Created by Thinkpad on 2017/7/4.
 * 时尚智能分析
 * https://developers.productai.cn/zh/reference/fashion_analysis
 */
public class DressingClassifyByImageFileRequest extends CallApiByImageFileBaseRequest<DressingClassifyResponse> {

    @Override
    public Class<DressingClassifyResponse> getResponseClass() {
        return DressingClassifyResponse.class;
    }

    public DressingClassifyByImageFileRequest(String loc) {
        super("dressing", "_0000111", loc);
    }

    public DressingClassifyByImageFileRequest(File imageFile, String loc) {
        this(loc);
        this.setImageFile(imageFile);
    }
}
