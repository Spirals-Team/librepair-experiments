package com.orasi.utils.dataProviders;

import java.net.URL;

import com.orasi.utils.exception.DataProviderInputFileNotFound;
import com.orasi.utils.io.ExcelDocumentReader;

public class ExcelDataProvider {
    private String filePath;
    private String sheetName;
    private int row;

    public ExcelDataProvider(String filePath, String sheetName) {
        this(filePath, sheetName, -1);
    }

    public ExcelDataProvider(String filePath, String sheetName, int rowToRead) {
        URL file = getClass().getResource(filePath);
        if (file == null) {
            throw new DataProviderInputFileNotFound("Failed to find a file in path [ " + filePath + " ]");
        }

        this.filePath = file.getPath();
        this.sheetName = sheetName;
        this.row = rowToRead;
    }

    public Object[][] getTestData() {
        return ExcelDocumentReader.readData(this.filePath, this.sheetName, this.row);
    }
}
