package cn.productai.api.pai.entity.filter;

import cn.productai.api.pai.base.CallApiByImageFileBaseRequest;

import java.io.File;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
public class IntelligentFilterByImageFileRequest extends CallApiByImageFileBaseRequest<IntelligentFilterResponse> {

    @Override
    public Class<IntelligentFilterResponse> getResponseClass() {
        return IntelligentFilterResponse.class;
    }

    public IntelligentFilterByImageFileRequest(){
        super("filter","_0000015");
    }

    public IntelligentFilterByImageFileRequest(File imageFile, String loc){
        super("filter","_0000015", loc);
    }
}
