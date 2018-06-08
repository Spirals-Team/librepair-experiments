package cn.productai.api.pai.base;

import cn.productai.api.core.attribute.IgnoreExtraParasAttribute;
import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.base.BaseResponse;
import cn.productai.api.core.enums.ServiceTypeId;
import cn.productai.api.core.helper.FileHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * modify your image set using a csv file
 *
 * @param <T>
 */
@IgnoreExtraParasAttribute
public abstract class ProductBatchDeleteByFileBaseRequest<T extends BaseResponse> extends BaseRequest<T> {

    private String productSetId = "";
    private String opType = "products_to_delete";
    private String _boundary = FileHelper.getBoundary();
    private File csvFile;
    private String ids;

    /**
     * @param productSetId your productSetId
     * @param opType       if you set this para to null or empty, it will use the default value "urls_to_add"
     */
    public ProductBatchDeleteByFileBaseRequest(String productSetId, String opType) {
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
    public ProductBatchDeleteByFileBaseRequest(String productSetId, File csvFile, String opType) {
        this(productSetId, opType);
        this.setCsvFile(csvFile);
    }

    public ProductBatchDeleteByFileBaseRequest() {
        super();
    }

    @Override
    public String getApiUrl() {
        return String.format("%s://%s/product_sets/%s/%s/products?ids=%s",
                this.getScheme(), this.getHost(), ServiceTypeId.PRODUCT_SET, this.getProductSetId(), this.getIds());
    }

    @Override
    public String getQueryString() {
        return "";
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

        // Parse file to string like '1,2,3'
        try {
            this.setIds(String.join(",", parseCsvToLine(csvFile)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    private List<String> parseCsvToLine(File csv) throws Exception {
        List<String> lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(csv);
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
            return lines;
        } catch (Exception e) {
            throw new Exception("Parse csv error");
        }
    }
}
