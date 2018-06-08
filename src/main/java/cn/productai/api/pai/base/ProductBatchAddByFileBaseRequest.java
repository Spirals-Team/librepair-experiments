package cn.productai.api.pai.base;

import cn.productai.api.core.attribute.IgnoreExtraParasAttribute;
import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.base.BaseResponse;
import cn.productai.api.core.enums.HttpMethod;
import cn.productai.api.core.enums.ServiceTypeId;
import cn.productai.api.core.helper.FileHelper;

import java.io.File;
import java.io.IOException;

/**
 * modify your image set using a csv file
 *
 * @param <T>
 */
@IgnoreExtraParasAttribute
public abstract class ProductBatchAddByFileBaseRequest<T extends BaseResponse> extends BaseRequest<T> {

    private String productSetId = "";
    private String opType = "products_to_add";
    private String _boundary = FileHelper.getBoundary();
    private File csvFile;

    /**
     * @param productSetId your productSetId
     * @param opType       if you set this para to null or empty, it will use the default value "urls_to_add"
     */
    public ProductBatchAddByFileBaseRequest(String productSetId, String opType) {
        super();

        if (productSetId.isEmpty())
            throw new IllegalArgumentException("imageSetId can not be null!");

        this.productSetId = productSetId;
        if (opType != null && !opType.isEmpty()) {
            this.opType = opType;
        }
    }

    /**
     * @param productSetId your productSetId
     * @param csvFile      the csv file
     * @param opType       if you set this para to null or empty, it will use the default value "urls_to_add"
     */
    public ProductBatchAddByFileBaseRequest(String productSetId, File csvFile, String opType) {
        this(productSetId, opType);
        this.csvFile = csvFile;
    }

    public ProductBatchAddByFileBaseRequest() {
        super();
        this.setRequestMethod(HttpMethod.POST);
    }

    @Override
    public String getApiUrl() {
        return String.format("%s://%s/product_sets/%s/%s/products",
                this.getScheme(), this.getHost(), ServiceTypeId.PRODUCT_SET, this.getProductSetId());
    }

    @Override
    public String getQueryString() {
        return "";
    }

    @Override
    public byte[] getQueryBytes() {
        try {
            return FileHelper.getMultipartBytes(this.csvFile, this._boundary, null, this.opType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getContentTypeHeader() {
        return FileHelper.getContentType(this._boundary);
    }

    public String getProductSetId() {
        return productSetId;
    }

    public void setProductSetId(String productSetId) {
        this.productSetId = productSetId;
    }

    public File getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(File csvFile) {
        this.csvFile = csvFile;
    }
}
