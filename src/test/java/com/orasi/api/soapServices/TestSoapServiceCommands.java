package com.orasi.api.soapServices;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orasi.api.APIBaseTest;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestSoapServiceCommands extends APIBaseTest {

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("addAttribute")
    @Test(groups = { "regression", "smoke" })
    public void addAttribute() {
        Assert.assertTrue(SoapServiceCommands.addAttribute("blah").equals("fx:addattribute;blah"));
    }

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("addNode")
    @Test(groups = { "regression", "smoke" })
    public void addNode() {
        Assert.assertTrue(SoapServiceCommands.addNode("blah").equals("fx:addnode;blah"));
    }

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("addNodes")
    @Test(groups = { "regression", "smoke" })
    public void addNodes() {
        Assert.assertTrue(SoapServiceCommands.addNodes("blah/blah2/blah3").equals("fx:addnodes;blah/blah2/blah3"));
    }

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("addNamespace")
    @Test(groups = { "regression", "smoke" })
    public void addNamespaceNode() {
        Assert.assertTrue(SoapServiceCommands.addNamespace("blah").equals("fx:addnamespace;blah"));
    }

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("getDateDefault")
    @Test(groups = { "regression", "smoke" })
    public void getDateDefault() {
        Assert.assertTrue(SoapServiceCommands.getDate().equals("fx:getdate;0"));
    }

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("getDateSpecific")
    @Test(groups = { "regression", "smoke" })
    public void getDateSpecific() {
        Assert.assertTrue(SoapServiceCommands.getDate("3").equals("fx:getdate;3"));
    }

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("getDateTimeDefault")
    @Test(groups = { "regression", "smoke" })
    public void getDateTimeDefault() {
        Assert.assertTrue(SoapServiceCommands.getDateTime().equals("fx:getdatetime;0"));
    }

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("getDateTimeSpecific")
    @Test(groups = { "regression", "smoke" })
    public void getDateTimeSpecific() {
        Assert.assertTrue(SoapServiceCommands.getDateTime("5").equals("fx:getdatetime;5"));
    }

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("getRandomAlphaNumericDefault")
    @Test(groups = { "regression", "smoke" })
    public void getRandomAlphaNumericDefault() {
        Assert.assertTrue(SoapServiceCommands.getRandomAlphaNumeric().matches("fx:randomalphanumeric;[3-9]"));
    }

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("getRandomAlphaNumericSpecific")
    @Test(groups = { "regression", "smoke" })
    public void getRandomAlphaNumericSpecific() {
        Assert.assertTrue(SoapServiceCommands.getRandomAlphaNumeric("8").equals("fx:randomalphanumeric;8"));
    }

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("getRandomNumberDefault")
    @Test(groups = { "regression", "smoke" })
    public void getRandomNumberDefault() {
        Assert.assertTrue(SoapServiceCommands.getRandomNumber().matches("fx:randomnumber;[3-9]"));
    }

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("getRandomNumberSpecific")
    @Test(groups = { "regression", "smoke" })
    public void getRandomNumberSpecific() {
        Assert.assertTrue(SoapServiceCommands.getRandomNumber("8").equals("fx:randomnumber;8"));
    }

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("getRandomStringDefault")
    @Test(groups = { "regression", "smoke" })
    public void getRandomStringDefault() {
        Assert.assertTrue(SoapServiceCommands.getRandomString().matches("fx:randomstring;[3-9]"));
    }

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("getRandomStringSpecific")
    @Test(groups = { "regression", "smoke" })
    public void getRandomStringSpecific() {
        Assert.assertTrue(SoapServiceCommands.getRandomString("8").equals("fx:randomstring;8"));
    }

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("removeAttribute")
    @Test(groups = { "regression", "smoke" })
    public void removeAttribute() {
        Assert.assertTrue(SoapServiceCommands.removeAttribute().equals("fx:removeattribute"));
    }

    @Features("API")
    @Stories("SoapServiceCommands")
    @Title("removeNode")
    @Test(groups = { "regression", "smoke" })
    public void removeNode() {
        Assert.assertTrue(SoapServiceCommands.removeNode().equals("fx:removenode"));
    }

}
