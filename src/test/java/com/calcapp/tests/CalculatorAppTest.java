package com.calcapp.tests;

import com.calcapp.pages.app.CalculatorPage;
import com.frameworkium.ui.pages.PageFactory;
import com.frameworkium.ui.tests.BaseUITest;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;

import java.util.Random;

import static com.google.common.truth.Truth.assertThat;

public class CalculatorAppTest extends BaseUITest {

    /**
     * Example test for https://appium.s3.amazonaws.com/TestApp7.1.app.zip
     */
    @Test(description = "Test sum computation", enabled = false)
    @TmsLink("CALC-1")
    public void testIOSApp() {

        Random rand = new Random();
        Integer a = rand.nextInt(100);
        Integer b = rand.nextInt(100);
        Integer sum = a + b;

        String result = PageFactory
                .newInstance(CalculatorPage.class)
                .computeSum(a, b).then()
                .getResult();

        assertThat(result).isEqualTo(sum.toString());
    }
}
