package com.orasi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.orasi.utils.date.DateTimeConversion;
import com.orasi.web.OrasiDriver;
import com.orasi.web.WebBaseTest;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class Sandbox extends WebBaseTest{
    
    @FindBy(id = "wrongid")
    private Listbox lstWrongID;
    
    @BeforeTest(alwaysRun = true)
    public void setup() {
        setApplicationUnderTest("Test Site");
        setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/listbox.html");
        testStart("TestListbox");
    }
	@Test
    public void getAllSelectedOptions() {
		
		ElementFactory.initElements(getDriver(), this);
		lstWrongID.select("wrong value");
      
    }
	
    @Override
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult testResults) {
    }

    @AfterTest(alwaysRun = true)
    public void close(ITestContext testResults) {
        endTest("TestListbox", testResults);
    }
}