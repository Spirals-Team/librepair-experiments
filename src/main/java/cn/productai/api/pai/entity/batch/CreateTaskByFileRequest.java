package cn.productai.api.pai.entity.batch;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.helper.FileHelper;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;

public class CreateTaskByFileRequest extends BaseRequest<CreateTaskByFileResponse> {

    @Override
    public Class<CreateTaskByFileResponse> getResponseClass() {
        return CreateTaskByFileResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/batch/_1000001/task/prepare", this.getHost());
    }

    @ParaSignAttribute(Name = "service_id")
    public String serviceId;

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
            return FileHelper.getMultipartBytes(this.csvFile, this._boundary, options, "urls");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private File csvFile;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public File getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(File csvFile) {
        this.csvFile = csvFile;
    }

    public CreateTaskByFileRequest(){
        super();
    }

    public CreateTaskByFileRequest(String serviceId, File csvFile){
        this();
        this.setServiceId(serviceId);
        this.setCsvFile(csvFile);
    }
}
