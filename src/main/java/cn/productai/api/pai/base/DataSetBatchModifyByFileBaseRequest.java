package cn.productai.api.pai.base;

import cn.productai.api.core.attribute.IgnoreExtraParasAttribute;
import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.base.BaseResponse;
import cn.productai.api.core.helper.FileHelper;

import java.io.File;
import java.io.IOException;

/**
 * modify your image set using a csv file
 * @param <T>
 */
@IgnoreExtraParasAttribute
public abstract class DataSetBatchModifyByFileBaseRequest<T extends BaseResponse> extends BaseRequest<T> {

    private String imageSetId = "";
    private String opType = "urls_to_add";

    @Override
    public String getApiUrl() {
        return String.format("https://%s/image_sets/_0000014/%s", this.getHost(), this.imageSetId);
    }

    @Override
    public String getQueryString() {
        return "";
    }

    private String _boundary = FileHelper.getBoundary();

    @Override
    public byte[] getQueryBytes() {
        try {
            return FileHelper.getMultipartBytes(this.csvFile, this._boundary, null, this.opType);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getContentTypeHeader() {
        return FileHelper.getContentType(this._boundary);
    }

    private File csvFile;

    public String getImageSetId() {
        return imageSetId;
    }

    public void setImageSetId(String imageSetId) {
        this.imageSetId = imageSetId;
    }

    public File getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(File csvFile) {
        this.csvFile = csvFile;
    }

    /**
     *
     * @param imageSetId your image_set_id
     * @param opType if you set this para to null or empty, it will use the default value "urls_to_add"
     */
    public DataSetBatchModifyByFileBaseRequest(String imageSetId, String opType) {
        super();

        if (imageSetId.isEmpty())
            throw new IllegalArgumentException("imageSetId can not be null!");

        this.imageSetId = imageSetId;
        if (opType != null && !opType.isEmpty()) {
            this.opType = opType;
        }
    }

    /**
     *
     * @param imageSetId your image_set_id
     * @param csvFile the csv file
     * @param opType if you set this para to null or empty, it will use the default value "urls_to_add"
     */
    public DataSetBatchModifyByFileBaseRequest(String imageSetId, File csvFile, String opType) {
        this(imageSetId, opType);
        this.csvFile = csvFile;
    }

    public DataSetBatchModifyByFileBaseRequest(){
        super();
    }
}
