package cn.productai.api.pai.base;


import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.base.BaseResponse;
import cn.productai.api.core.helper.EnumHelper;
import cn.productai.api.core.helper.FileHelper;
import cn.productai.api.core.helper.WebQueryHelper;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;

public abstract class CallApiByImageFileBaseRequest<T extends BaseResponse> extends BaseRequest<T> {

    private String serviceType;
    private String serviceId;


    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/%s/%s", this.getHost(), this.getServiceType(), this.getServiceId());
    }

    @Override
    public String getQueryString() {
        return "";
    }

    private String _boundary = FileHelper.getBoundary();

    @Override
    public String getContentTypeHeader() {
        return FileHelper.getContentType(this._boundary);
    }

    @Override
    public byte[] getQueryBytes() {
        HashMap<String, String> options = new HashMap<>();
        Field[] ps = this.getClass().getFields();
        for (Field p : ps) {
            ParaSignAttribute ca = p.getAnnotation(ParaSignAttribute.class);
            if (ca != null) {
                try {
                    Object value = p.get(this);
                    if (value != null && !value.toString().isEmpty()) {
                        options.put(ca.Name(), value.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.getOptions() != null && this.getOptions().size() > 0) {
            for (String key : this.getOptions().keySet()) {
                try {
                    options.put(key, this.getOptions().get(key));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            return FileHelper.getMultipartBytes(this.imageFile, this._boundary, options, "search");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private File imageFile;

    @ParaSignAttribute(Name = "loc")
    public String loc = "0-0-1-1";

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public CallApiByImageFileBaseRequest() {
        super();
        this._boundary = FileHelper.getBoundary();
    }

    public CallApiByImageFileBaseRequest(String serviceType, String serviceId) {
        this();
        if (serviceType.isEmpty()) {
            throw new IllegalArgumentException("serviceType can not be null");
        }
        if (serviceId.isEmpty()) {
            throw new IllegalArgumentException("serviceId can not be null");
        }

        this.serviceType = serviceType;
        this.serviceId = serviceId;
    }

    public CallApiByImageFileBaseRequest(String serviceType, String serviceId, String loc) {
        this(serviceType, serviceId);
        if (loc != null && !loc.isEmpty())
            this.loc = loc;
    }

    public CallApiByImageFileBaseRequest(String serviceType, String serviceId, File imageFile) {
        this(serviceType, serviceId);
        this.imageFile = imageFile;
    }

    public CallApiByImageFileBaseRequest(String serviceType, String serviceId, File imageFile, String loc) {
        this(serviceType, serviceId, loc);
        this.imageFile = imageFile;
    }
}
