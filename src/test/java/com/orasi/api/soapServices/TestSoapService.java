package com.orasi.api.soapServices;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orasi.api.APIBaseTest;
import com.orasi.api.soapServices.exceptions.HeaderNotFoundException;
import com.orasi.api.soapServices.exceptions.MissingFunctionParameterValueException;
import com.orasi.api.soapServices.exceptions.SoapException;
import com.orasi.api.soapServices.helpers.GetInfoByZip;
import com.orasi.api.soapServices.helpers.USZipService;
import com.orasi.utils.Sleeper;
import com.orasi.utils.exception.XPathInvalidExpression;
import com.orasi.utils.exception.XPathNotFoundException;
import com.orasi.utils.exception.XPathNullNodeValueException;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestSoapService extends APIBaseTest {
    @Features("API")
    @Stories("SoapServices")
    @Title("createService")
    @Test
    public void createService() {
        USZipService usZip = new USZipService();
        Assert.assertNotNull(usZip);
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("createOperation")
    @Test(dependsOnMethods = "createService")
    public void createOperation() {
        GetInfoByZip getInfo = new GetInfoByZip();
        Assert.assertNotNull(getInfo);
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("createOperation_WithCSVData")
    @Test(dependsOnMethods = "createOperation")
    public void createOperation_WithCSVData() {
        GetInfoByZip getInfo = new GetInfoByZip("Main", "CSV");
        Assert.assertTrue(getInfo.getRequestZip().equals("27410"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("createOperation_WithXLSData")
    @Test(dependsOnMethods = "createOperation")
    public void createOperation_WithXLSData() {
        GetInfoByZip getInfo = new GetInfoByZip("Main", "XLS");
        Assert.assertTrue(getInfo.getRequestZip().equals("27410"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("createOperation_WithXLSXData")
    @Test(dependsOnMethods = "createOperation")
    public void createOperation_WithXLSXData() {
        GetInfoByZip getInfo = new GetInfoByZip("Main", "XLSX");
        Assert.assertTrue(getInfo.getRequestZip().equals("27410"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("addRequestHeader")
    @Test(dependsOnMethods = "createOperation")
    public void addRequestHeader() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.addRequestHeader("blah", "blah");
        Assert.assertNotNull(getInfo);
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("getNumberOfRequestNodesByXPath")
    @Test(dependsOnMethods = "createOperation")
    public void getNumberOfRequestNodesByXPath() {
        GetInfoByZip getInfo = new GetInfoByZip();
        Assert.assertTrue(getInfo.getNumberOfRequestNodesByXPath("/Envelope/Body/GetInfoByZIP/USZip") == 1);
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setSoapVersion")
    @Test(dependsOnMethods = "createOperation")
    public void setSoapVersion() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setSoapVersion();
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("getServiceURL")
    @Test(dependsOnMethods = "createOperation")
    public void getServiceURL() {
        GetInfoByZip getInfo = new GetInfoByZip();
        Assert.assertTrue(getInfo.getServiceURL().equals("http://www.webservicex.net/uszip.asmx?wsdl"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("getServiceName")
    @Test(dependsOnMethods = "createOperation")
    public void getServiceName() {
        GetInfoByZip getInfo = new GetInfoByZip();
        Assert.assertTrue(getInfo.getServiceName().equals("USZipSoap"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("getOperationName")
    @Test(dependsOnMethods = "createOperation")
    public void getOperationName() {
        GetInfoByZip getInfo = new GetInfoByZip();
        Assert.assertTrue(getInfo.getOperationName().equals("GetInfoByZIP"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("getRequest")
    @Test(dependsOnMethods = "createOperation")
    public void getRequest() {
        GetInfoByZip getInfo = new GetInfoByZip();
        Assert.assertTrue(!getInfo.getRequest().isEmpty());
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_AddAttribute")
    @Test(dependsOnMethods = "createOperation")
    public void setRequestNodeValueByXPath_HandleValueFunctions_AddAttribute() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:addattribute;blah");
        Assert.assertTrue(getInfo.getRequest().contains("<web:USZip blah=\"\""));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_AddAttribute_MissingParameter")
    @Test(dependsOnMethods = "createOperation", expectedExceptions = MissingFunctionParameterValueException.class)
    public void setRequestNodeValueByXPath_HandleValueFunctions_AddAttribute_MissingParameter() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:addattribute");
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_AddNode")
    @Test(dependsOnMethods = "createOperation")
    public void setRequestNodeValueByXPath_HandleValueFunctions_AddNode() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP", "fx:addnode;blah");
        Assert.assertTrue(getInfo.getRequest().contains("<blah/>"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_AddNode_MissingParameter")
    @Test(dependsOnMethods = "createOperation", expectedExceptions = MissingFunctionParameterValueException.class)
    public void setRequestNodeValueByXPath_HandleValueFunctions_AddNode_MissingParameter() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:addnode");
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_AddNodes")
    @Test(dependsOnMethods = "createOperation")
    public void setRequestNodeValueByXPath_HandleValueFunctions_AddNodes() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP", "fx:addnodes;blah/blah2/blah3");
        Assert.assertTrue(getInfo.getRequest().replace(System.getProperty("line.separator"), "").replaceAll(" ", "").contains("<blah><blah2><blah3/></blah2></blah>"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_AddNodes_MissingParameter")
    @Test(dependsOnMethods = "createOperation", expectedExceptions = MissingFunctionParameterValueException.class)
    public void setRequestNodeValueByXPath_HandleValueFunctions_AddNodes_MissingParameter() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:addnodes");
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_AddNamespace")
    @Test(dependsOnMethods = "createOperation")
    public void setRequestNodeValueByXPath_HandleValueFunctions_AddNamespace() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope", "fx:addnamespace;xmlns:web2,http://www.webserviceXnew.NET");
        Assert.assertTrue(getInfo.getRequest().contains("xmlns:web2=\"http://www.webserviceXnew.NET\""));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_AddNamespace_MissingParameter")
    @Test(dependsOnMethods = "createOperation", expectedExceptions = MissingFunctionParameterValueException.class)
    public void setRequestNodeValueByXPath_HandleValueFunctions_AddNamespace_MissingParameter() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:addnamespace");
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_AddNamespace_MissingURLParameter")
    @Test(dependsOnMethods = "createOperation", expectedExceptions = MissingFunctionParameterValueException.class)
    public void setRequestNodeValueByXPath_HandleValueFunctions_AddNamespace_MissingURLParameter() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:addnamespace;xmlns:web2");
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_GetDateTime")
    @Test(dependsOnMethods = "createOperation")
    public void setRequestNodeValueByXPath_HandleValueFunctions_GetDateTime() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:getdatetime;0");
        Assert.assertTrue(getInfo.getRequestZip().matches("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_GetDateTime_MissingParameter")
    @Test(dependsOnMethods = "createOperation", expectedExceptions = MissingFunctionParameterValueException.class)
    public void setRequestNodeValueByXPath_HandleValueFunctions_GetDateTime_MissingParameter() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:getdatetime");
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_GetDate")
    @Test(dependsOnMethods = "createOperation")
    public void setRequestNodeValueByXPath_HandleValueFunctions_GetDate() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:getdate;0");
        Assert.assertTrue(getInfo.getRequestZip().matches("[0-9]{4}-[0-9]{2}-[0-9]{2}"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_GetDate_MissingParameter")
    @Test(dependsOnMethods = "createOperation", expectedExceptions = MissingFunctionParameterValueException.class)
    public void setRequestNodeValueByXPath_HandleValueFunctions_GetDate_MissingParameter() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:getdate");
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_RemoveNode")
    @Test(dependsOnMethods = "createOperation")
    public void setRequestNodeValueByXPath_HandleValueFunctions_RemoveAttribute() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("//Envelope/Body/GetInfoByZIP/USZip", "fx:addAttribute;blah");
        getInfo.setRequestNodeValueByXPath("//Envelope/Body/GetInfoByZIP/USZip/@blah", "fx:removeattribute");
        Assert.assertTrue(getInfo.getRequest().contains("<web:USZip>27410</web:USZip>"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_RemoveNode")
    @Test(dependsOnMethods = "createOperation")
    public void setRequestNodeValueByXPath_HandleValueFunctions_RemoveNode() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("//Envelope/Body/GetInfoByZIP/USZip", "fx:removenode");
        Assert.assertTrue(getInfo.getRequest().contains("<web:GetInfoByZIP/>"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_RandomAlphaNumeric")
    @Test(dependsOnMethods = "createOperation")
    public void setRequestNodeValueByXPath_HandleValueFunctions_RandomAlphaNumeric() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:randomalphanumeric;2");
        Assert.assertTrue(getInfo.getRequestZip().matches("[0-9 a-z A-Z]{2}"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_RandomAlphaNumeric_MissingParameter")
    @Test(dependsOnMethods = "createOperation", expectedExceptions = MissingFunctionParameterValueException.class)
    public void setRequestNodeValueByXPath_HandleValueFunctions_RandomAlphaNumeric_MissingParameter() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:randomalphanumeric");
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_RandomNumber")
    @Test(dependsOnMethods = "createOperation")
    public void setRequestNodeValueByXPath_HandleValueFunctions_RandomNumber() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:randomnumber;2");
        Assert.assertTrue(getInfo.getRequestZip().matches("[0-9]{2}"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_RandomNumber_MissingParameter")
    @Test(dependsOnMethods = "createOperation", expectedExceptions = MissingFunctionParameterValueException.class)
    public void setRequestNodeValueByXPath_HandleValueFunctions_RandomNumber_MissingParameter() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:RandomNumber");
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_RandomString")
    @Test(dependsOnMethods = "createOperation")
    public void setRequestNodeValueByXPath_HandleValueFunctions_RandomString() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:RandomString;5");
        Assert.assertTrue(getInfo.getRequestZip().matches("[a-z A-Z]{5}"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_RandomNumber_MissingParameter")
    @Test(dependsOnMethods = "createOperation", expectedExceptions = SoapException.class)
    public void setRequestNodeValueByXPath_HandleValueFunctions_InvalidCommand() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:blah");
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_HandleValueFunctions_RandomString_MissingParameter")
    @Test(dependsOnMethods = "createOperation", expectedExceptions = MissingFunctionParameterValueException.class)
    public void setRequestNodeValueByXPath_HandleValueFunctions_RandomString_MissingParameter() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "fx:RandomString");
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_XPathNotFoundException")
    @Test(dependsOnMethods = "createOperation", expectedExceptions = XPathNotFoundException.class)
    public void setRequestNodeValueByXPath_XPathNotFoundException() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/blah", "blah");
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_InvalidXPathExpression")
    @Test(dependsOnMethods = "createOperation", expectedExceptions = XPathInvalidExpression.class)
    public void setRequestNodeValueByXPath_InvalidXPathExpression() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/blah\"", "blah");
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("setRequestNodeValueByXPath_NullValueExpression")
    @Test(dependsOnMethods = "createOperation", expectedExceptions = XPathNullNodeValueException.class)
    public void setRequestNodeValueByXPath_NullValueExpression() {
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body/GetInfoByZIP/USZip", "");
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("sendRequest")
    @Test(dependsOnMethods = "createOperation")
    public void sendRequest() {
        sleep();
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.sendRequest();
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("sendRequestExpectFault")
    @Test(dependsOnMethods = "sendRequest")
    public void sendRequestExpectFault() {
        sleep();
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.setRequestNodeValueByXPath("/Envelope/Body", SoapServiceCommands.removeNode());
        getInfo.sendRequest();
        Assert.assertTrue(getInfo.getResponseStatusCode().equals("soap:Receiver"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("sendRequest")
    @Test(dependsOnMethods = "createOperation")
    public void sendRequestWithHeaders() {
        sleep();
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.addRequestHeader("encoding", "UTF-8");
        getInfo.sendRequest();
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("getResponse")
    @Test(dependsOnMethods = "sendRequest")
    public void getResponse() {
        sleep();
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.sendRequest();
        Assert.assertTrue(!getInfo.getResponse().isEmpty());
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("getNumberOfResponseNodesByXPath")
    @Test(dependsOnMethods = "sendRequest")
    public void getNumberOfResponseNodesByXPath() {
        sleep();
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.sendRequest();
        Assert.assertTrue(getInfo.getNumberOfResponseNodesByXPath("/Envelope/Body/GetInfoByZIPResponse/GetInfoByZIPResult/NewDataSet/Table") == 1);
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("getResponseNodeValueByXPath")
    @Test(dependsOnMethods = "sendRequest")
    public void getResponseNodeValueByXPath() {
        sleep();
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.sendRequest();
        Assert.assertTrue(getInfo.getResponseNodeValueByXPath("/Envelope/Body/GetInfoByZIPResponse/GetInfoByZIPResult/NewDataSet/Table/ZIP").equals("27410"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("getResponseHeaders")
    @Test(dependsOnMethods = "sendRequest")
    public void getResponseHeaders() {
        sleep();
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.sendRequest();
        Assert.assertTrue(getInfo.getResponseHeader("Content-Type").equals("application/soap+xml; charset=utf-8"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("getResponseHeaders_NoneFound")
    @Test(dependsOnMethods = "sendRequest", expectedExceptions = HeaderNotFoundException.class)
    public void getResponseHeaders_NoneFound() {
        sleep();
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.sendRequest();
        getInfo.getResponseHeader("blah");
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("validateResponse_WithCSVData")
    @Test(dependsOnMethods = "sendRequest")
    public void validateResponse_WithCSVData() {
        sleep();
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.sendRequest();
        Assert.assertTrue(getInfo.validateResponse("/excelsheets/GetInfoByZipResponse_csv.csv", "Main"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("validateResponse_WithXLSData")
    @Test(dependsOnMethods = "sendRequest")
    public void validateResponse_WithXLSData() {
        sleep();
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.sendRequest();
        Assert.assertTrue(getInfo.validateResponse("/excelsheets/GetInfoByZipResponse_xls.xls", "Main"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("validateResponse_WithXLSXData")
    @Test(dependsOnMethods = "sendRequest")
    public void validateResponse_WithXLSXData() {
        sleep();
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.sendRequest();
        Assert.assertTrue(getInfo.validateResponse("/excelsheets/GetInfoByZipResponse_xlsx.xlsx", "Main"));
    }

    @Features("API")
    @Stories("SoapServices")
    @Title("validateResponse_WithError")
    @Test(dependsOnMethods = "sendRequest")
    public void validateResponse_WithErrors() {
        sleep();
        GetInfoByZip getInfo = new GetInfoByZip();
        getInfo.sendRequest();
        Assert.assertFalse(getInfo.validateResponse("/excelsheets/GetInfoByZipResponse_ExpectErrors.csv", "Main"));
    }

    private void sleep() {
        Sleeper.sleep(1000);
    }
}
