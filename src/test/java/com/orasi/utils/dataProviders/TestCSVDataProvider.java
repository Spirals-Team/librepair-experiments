package com.orasi.utils.dataProviders;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orasi.utils.exception.DataProviderInputFileNotFound;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestCSVDataProvider {
    @Features("Utilities")
    @Stories("CSVDataProvider")
    @Title("getData")
    @Test(groups = { "regression", "utils", "dataProviders" })
    public void getData() {
        Object[][] data = CSVDataProvider.getData("/excelsheets/TestData_csv.csv");
        Assert.assertNotNull(data);
    }

    @Features("Utilities")
    @Stories("CSVDataProvider")
    @Title("getDataWithDelimiter")
    @Test(groups = { "regression", "utils", "dataProviders" })
    public void getDataWithDelimiter() {
        Object[][] data = CSVDataProvider.getData("/excelsheets/TestData_csv.csv", ",");
        Assert.assertNotNull(data);
    }

    @Features("Utilities")
    @Stories("CSVDataProvider")
    @Title("getDataWithDelimiter")
    @Test(groups = { "regression", "utils", "dataProviders" }, expectedExceptions = DataProviderInputFileNotFound.class)
    public void getDataNoFileFound() {
        Object[][] data = CSVDataProvider.getData("blah");
        Assert.assertNotNull(data);
    }
}
