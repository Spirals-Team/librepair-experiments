package com.orasi.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.orasi.web.WebBaseTest;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestJavaUtilities extends WebBaseTest {

    @Override
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult testResults) {
    }

    @Features("Utilities")
    @Stories("JavaUtilities")
    @Title("testJavaUtilitiesWithNull")
    @Test(groups = { "regression", "utils", "javaUtilities" })
    public void testJavaUtilitiesWithNull() {
        Assert.assertFalse(JavaUtilities.isValid(null));
    }

    @Features("Utilities")
    @Stories("JavaUtilities")
    @Title("testJavaUtilitiesWithValidObject")
    @Test(groups = { "regression", "utils", "javaUtilities" })
    public void testJavaUtilitiesWithValidObject() {
        Assert.assertTrue(JavaUtilities.isValid(1));
    }

    @Features("Utilities")
    @Stories("JavaUtilities")
    @Title("testJavaUtilitiesWithValidString")
    @Test(groups = { "regression", "utils", "javaUtilities" })
    public void testJavaUtilitiesWithValidString() {
        Assert.assertTrue(JavaUtilities.isValid("blah"));
    }

    @Features("Utilities")
    @Stories("JavaUtilities")
    @Title("testJavaUtilitiesWithEmptyString")
    @Test(groups = { "regression", "utils", "javaUtilities" })
    public void testJavaUtilitiesWithEmptyString() {
        Assert.assertFalse(JavaUtilities.isValid(""));
    }

    @Features("Utilities")
    @Stories("JavaUtilities")
    @Title("testJavaUtilitiesWithValidCollection")
    @Test(groups = { "regression", "utils", "javaUtilities" })
    public void testJavaUtilitiesWithValidCollection() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        Assert.assertTrue(JavaUtilities.isValid(list));
    }

    @Features("Utilities")
    @Stories("JavaUtilities")
    @Title("testJavaUtilitiesWithEmptyCollection")
    @Test(groups = { "regression", "utils", "javaUtilities" })
    public void testJavaUtilitiesWithEmptyCollection() {
        List<Integer> list = new ArrayList<>();
        Assert.assertFalse(JavaUtilities.isValid(list));
    }

    @Features("Utilities")
    @Stories("JavaUtilities")
    @Title("testJavaUtilitiesWithValidMap")
    @Test(groups = { "regression", "utils", "javaUtilities" })
    public void testJavaUtilitiesWithValidMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("blah", 1);
        Assert.assertTrue(JavaUtilities.isValid(map));
    }

    @Features("Utilities")
    @Stories("JavaUtilities")
    @Title("testJavaUtilitiesWithValidMap")
    @Test(groups = { "regression", "utils", "javaUtilities" })
    public void testJavaUtilitiesWithEmptyMap() {
        Map<String, Integer> map = new HashMap<>();
        Assert.assertFalse(JavaUtilities.isValid(map));
    }

}
