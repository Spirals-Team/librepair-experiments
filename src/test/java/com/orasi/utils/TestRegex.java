package com.orasi.utils;

import org.junit.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.orasi.web.WebBaseTest;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestRegex extends WebBaseTest {
    private static final String CASE_SENSITIVE_EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String CASE_INSENSITIVE_EMAIL_PATTERN = "^[_a-z0-9-\\+]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$";

    @BeforeTest
    public void setup() {
        setReportToMustard(false);
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult testResults) {
    }

    @Features("Utilities")
    @Stories("Regex")
    @Title("constructor")
    @Test(groups = { "regression", "utils", "Regex" })
    public void constructorWithElement() {
        Assert.assertNotNull((new Regex()));
    }

    @Features("Utilities")
    @Stories("Regex")
    @Title("match")
    @Test(groups = { "regression", "utils", "dev", "Regex" })
    public void match() {
        Assert.assertTrue("Pattern did not match", Regex.match(CASE_SENSITIVE_EMAIL_PATTERN, "TesterEmail123@email.com"));
    }

    @Features("Utilities")
    @Stories("Regex")
    @Title("matchFail")
    @Test(groups = { "regression", "utils", "dev", "Regex" })
    public void matchFail() {
        Assert.assertFalse("Pattern did not match", Regex.match(CASE_SENSITIVE_EMAIL_PATTERN, "TesterEmail123email.com"));
    }

    @Features("Utilities")
    @Stories("Regex")
    @Title("match")
    @Test(groups = { "regression", "utils", "dev", "Regex" })
    public void matchCaseInsensitive() {
        Assert.assertTrue("Pattern did not match", Regex.matchCaseInsensitive(CASE_INSENSITIVE_EMAIL_PATTERN, "TESTING234_23-234@email.com"));
    }

    @Features("Utilities")
    @Stories("Regex")
    @Title("matchFail")
    @Test(groups = { "regression", "utils", "dev", "Regex" })
    public void matchCaseInsensitiveFail() {
        Assert.assertFalse("Pattern did not match", Regex.matchCaseInsensitive(CASE_INSENSITIVE_EMAIL_PATTERN, "TesterEmail123@email.c"));
    }
}
