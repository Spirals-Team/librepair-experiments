///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package seleniumcucumber;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.Matchers.containsString;
//import static org.hamcrest.Matchers.not;
//import static org.hamcrest.Matchers.notNullValue;
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runners.MethodSorters;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.ExpectedCondition;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
///**
// *
// * @author Andreas
// */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class Home {
//
//    private static final int WAIT_MAX = 4;
//    static WebDriver driver;
//
//    @BeforeClass
//    public static void setup() {
//        System.setProperty("webdriver.chrome.driver", "src/test/java/selenium/chromedriver.exe");
//        driver = new ChromeDriver();
//    }
//
//    @Before
//    public void waitLoad() throws InterruptedException {
//        TimeUnit.SECONDS.sleep(2);
//    }
//
//    @AfterClass
//    public static void tearDown() {
//        driver.quit();
//    }
//
//    @Test
//    //Verify that page is loaded and all expected data are visible
//    // 1. Verify that data is loaded, and the DOM is constructed in homepage
//    public void test1() throws Exception {
//        driver.get("http://localhost:8080/dbtest/#!/home");
//        WebElement e = driver.findElement(By.id("headline"));
//        String expectedResult = "Here we write a short brief about the project";
//        String actualOutout = e.getText();
//        Assert.assertThat(expectedResult, is(actualOutout));
//    }
//    @Test
//    //Verify that page is loaded and all expected data are visible
//    // 2. Verify that nav-bar works
//    public void test2() throws Exception {
//        driver.get("http://localhost:8080/dbtest/#!/home");
//        List<WebElement> allElements = driver.findElements(By.tagName("a"));
//        String expectedOutput_1 = "Home";
//        String expectedOutput_2 = "User Story #1";
//        String expectedOutput_3 = "User Story #2";
//        String expectedOutput_4 = "User Story #3";
//        String expectedOutput_5 = "User Story #4";
//        
//        String expectedLink_1 = "home";
//        String expectedLink_2 = "view1";
//        String expectedLink_3 = "view2";
//        String expectedLink_4 = "view3";
//        String expectedLink_5 = "view4";
//        
//        String home =  allElements.get(1).getText();
//        String us1 =  allElements.get(2).getText();
//        String us2 =  allElements.get(3).getText();
//        String us3 =  allElements.get(4).getText();
//        String us4 =  allElements.get(5).getText();
//        String[] asd = allElements.get(1).getAttribute("href").split("/");
//        String link_1 = allElements.get(1).getAttribute("href").split("/")[5];
//        String link_2 = allElements.get(2).getAttribute("href").split("/")[5];
//        String link_3 = allElements.get(3).getAttribute("href").split("/")[5];
//        String link_4 = allElements.get(4).getAttribute("href").split("/")[5];
//        String link_5 = allElements.get(5).getAttribute("href").split("/")[5];
//        
//        Assert.assertThat(expectedOutput_1, is(home));
//        Assert.assertThat(expectedOutput_2, is(us1));
//        Assert.assertThat(expectedOutput_3, is(us2));
//        Assert.assertThat(expectedOutput_4, is(us3));
//        Assert.assertThat(expectedOutput_5, is(us4));
//        
//        
//        Assert.assertThat(expectedLink_1, is(link_1));
//        Assert.assertThat(expectedLink_2, is(link_2));
//        Assert.assertThat(expectedLink_3, is(link_3));
//        Assert.assertThat(expectedLink_4, is(link_4));
//        Assert.assertThat(expectedLink_5, is(link_5));
//    }
//
//
//    
//
//}
