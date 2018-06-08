package cn.productai.api.pai.entity.color;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.enums.ColorReturnType;
import cn.productai.api.core.enums.Granularity;
import cn.productai.api.core.enums.SubType;
import cn.productai.api.core.helper.EnumHelper;
import cn.productai.api.pai.base.CallApiByImageUrlBaseRequest;

import java.util.HashMap;

public class ColorClassifyByImageUrlRequest extends CallApiByImageUrlBaseRequest<ColorAnalysisResponse> {

    @Override
    public Class<ColorAnalysisResponse> getResponseClass() {
        return ColorAnalysisResponse.class;
    }

    public ColorClassifyByImageUrlRequest(String loc) {
        super("color", "_0000072", loc);
    }

    public ColorClassifyByImageUrlRequest(SubType subType, Granularity granularity, ColorReturnType returnType, String imageUrl, String loc) {
        this(loc);
        this.setSubType(subType);
        this.setGranularity(granularity);
        this.setReturnType(returnType);
        this.setUrl(imageUrl);
    }

    public ColorClassifyByImageUrlRequest(String serviceType, String serviceId, String loc) {
        super(serviceType, serviceId, loc);
    }

    public ColorClassifyByImageUrlRequest(String serviceType, String serviceId, String imageUrl, String loc) {
        super(serviceType, serviceId, imageUrl, loc);
    }

    private SubType subType;
    private Granularity granularity;
    private ColorReturnType returnType;

    private final HashMap<Integer, String> _subTypes = EnumHelper.toHashMap(SubType.class);
    private final HashMap<Integer, String> _granularities = EnumHelper.toHashMap(Granularity.class);
    private final HashMap<Integer, String> _returnTypes = EnumHelper.toHashMap(ColorReturnType.class);

    public SubType getSubType() {
        return subType;
    }

    public void setSubType(SubType subType) {
        this.subType = subType;
        this.subTypeString = _subTypes.get(this.getSubType().ordinal());
    }

    public Granularity getGranularity() {
        return granularity;
    }

    public void setGranularity(Granularity granularity) {
        this.granularity = granularity;
        this.granularityString = _granularities.get(this.getGranularity().ordinal());
    }

    public ColorReturnType getReturnType() {
        return returnType;
    }

    public void setReturnType(ColorReturnType returnType) {
        this.returnType = returnType;
        this.returnTypeString = _returnTypes.get(this.getReturnType().ordinal());
    }

    @ParaSignAttribute(Name = "sub_type")
    public String subTypeString;

    @ParaSignAttribute(Name = "granularity")
    public String granularityString;

    @ParaSignAttribute(Name = "return_type")
    public String returnTypeString;
}
