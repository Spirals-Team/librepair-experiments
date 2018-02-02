package com.orasi.utils;

import org.junit.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.orasi.AutomationException;
import com.orasi.utils.io.ExcelDocumentReader;
import com.orasi.web.WebBaseTest;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestExcelDocumentReader extends WebBaseTest {

    @Override
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult testResults) {
    }

    @Features("Utilities")
    @Stories("ExcelDocumentReader")
    @Title("readDataFilePathConstructorAllRows")
    @Test(groups = "regression")
    public void readDataFilePathConstructorAllRows() {
        Object[][] data = null;
        String filepath = "/excelsheets/TestData.xlsx";
        data = ExcelDocumentReader.readData(filepath, "Data");

        Assert.assertNotNull(data);
        Assert.assertTrue(data.length == 3);
        Assert.assertTrue(data[0].length == 5);
        Assert.assertTrue(data[0][0].toString().equals("DataSet1"));

    }

    @Features("Utilities")
    @Stories("ExcelDocumentReader")
    @Title("readDataFilePathConstructorSingleRow")
    @Test(groups = "regression")
    public void readDataFilePathConstructorSingleRow() {
        Object[][] data = null;
        String filepath = "/excelsheets/TestData.xlsx";
        data = ExcelDocumentReader.readData(filepath, "Data", 1);

        Assert.assertNotNull(data);
        Assert.assertTrue(data.length == 1);
        Assert.assertTrue(data[0].length == 5);
        Assert.assertTrue(data[0][0].toString().equals("DataSet1"));

    }

    @Features("Utilities")
    @Stories("ExcelDocumentReader")
    @Title("readDataFilePathConstructorSingleRow")
    @Test(groups = "regression")
    public void readDataFilePathConstructorSheetNameSingleRow() {
        Object[][] data = null;
        String filepath = "/excelsheets/TestData.xlsx";
        data = ExcelDocumentReader.readData(filepath, "Data", 1);

        Assert.assertNotNull(data);
        Assert.assertTrue(data.length == 1);
        Assert.assertTrue(data[0].length == 5);
        Assert.assertTrue(data[0][0].toString().equals("DataSet1"));

    }

    @Features("Utilities")
    @Stories("ExcelDocumentReader")
    @Title("readDataDefaultConstructorAllRows")
    @Test(groups = "regression")
    public void readDataDefaultConstructorAllRows() {
        Object[][] data = null;
        String filepath = "/excelsheets/TestData.xlsx";
        data = ExcelDocumentReader.readData(filepath, "Data");

        Assert.assertNotNull(data);
        Assert.assertTrue(data.length == 3);
        Assert.assertTrue(data[0].length == 5);
        Assert.assertTrue(data[0][0].toString().equals("DataSet1"));

    }

    @Features("Utilities")
    @Stories("ExcelDocumentReader")
    @Title("readDataDefaultConstructorAllRows")
    @Test(groups = "regression")
    public void readDataDefaultConstructorAllRowsIncludeColumns() {
        Object[][] data = null;
        String filepath = "/excelsheets/TestData.xlsx";
        data = ExcelDocumentReader.readData(filepath, "Data", -1, 0);

        Assert.assertNotNull(data);
        Assert.assertTrue(data.length == 4);
        Assert.assertTrue(data[0].length == 5);
        Assert.assertTrue(data[0][0].toString().equals("Column1"));
    }

    @Features("Utilities")
    @Stories("ExcelDocumentReader")
    @Title("readDataDefaultConstructorAllRows")
    @Test(groups = "regression")
    public void readDataDefaultConstructorAllRowsStartRow() {
        Object[][] data = null;
        String filepath = "/excelsheets/TestData.xlsx";
        data = ExcelDocumentReader.readData(filepath, "Data", -1, 2);

        Assert.assertNotNull(data);
        Assert.assertTrue(data.length == 2);
        Assert.assertTrue(data[0].length == 5);
        Assert.assertTrue(data[0][0].toString().equals("DataSet2"));

    }

    @Features("Utilities")
    @Stories("ExcelDocumentReader")
    @Title("readDataDefaultConstructorSingleRow")
    @Test(groups = "regression")
    public void readDataDefaultConstructorSingleRow() {
        Object[][] data = null;
        String filepath = "/excelsheets/TestData.xlsx";
        data = ExcelDocumentReader.readData(filepath, "Data", 1);

        Assert.assertNotNull(data);
        Assert.assertTrue(data.length == 1);
        Assert.assertTrue(data[0].length == 5);
        Assert.assertTrue(data[0][0].toString().equals("DataSet1"));

    }

    @Features("Utilities")
    @Stories("ExcelDocumentReader")
    @Title("readDataXlsFile")
    @Test(groups = "regression")
    public void readDataXlsFile() {
        Object[][] data = null;
        String filepath = "/excelsheets/TestData_xls.xls";
        data = ExcelDocumentReader.readData(filepath, "Data", 1);

        Assert.assertNotNull(data);
        Assert.assertTrue(data.length == 1);
        Assert.assertTrue(data[0].length == 5);
        Assert.assertTrue(data[0][0].toString().equals("DataSet1"));

    }

    @Features("Utilities")
    @Stories("ExcelDocumentReader")
    @Title("readDataMissingXlsFile")
    @Test(groups = "regression", expectedExceptions = { AutomationException.class })
    public void readDataMissingXlsFile() {
        ExcelDocumentReader.readData("/excelsheets/TestData_blah.xls", "Data", 1);
    }

    @Features("Utilities")
    @Stories("ExcelDocumentReader")
    @Title("readDataInvalidXlsFile")
    @Test(groups = "regression", expectedExceptions = { AutomationException.class })
    public void readDataInvalidXlsFile() {
        String filepath = "/excelsheets/TestData_csv.csv";
        ExcelDocumentReader.readData(filepath, "Data", 1);
    }

    @Features("Utilities")
    @Stories("ExcelDocumentReader")
    @Title("readDataSheetAsIndex")
    @Test(groups = "regression")
    public void readDataSheetAsIndex() {
        Object[][] data = null;
        String filepath = "/excelsheets/TestData.xlsx";
        data = ExcelDocumentReader.readData(filepath, "0");

        Assert.assertNotNull(data);
        Assert.assertTrue(data.length == 3);
        Assert.assertTrue(data[0].length == 5);
        Assert.assertTrue(data[0][0].toString().equals("DataSet1"));

    }
}
