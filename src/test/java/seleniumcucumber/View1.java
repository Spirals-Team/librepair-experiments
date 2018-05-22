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
//public class View1 {
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
//        driver.navigate().refresh();
//    }
//
//    @AfterClass
//    public static void tearDown() {
//        driver.quit();
//    }
//
//    @Test
//    //Verify that page is loaded and all expected data are visible
//    //2. Verify that data is loaded, and the DOM is constructed in userstory 1
//    // Check if the dropdown menu contains correct things
//    public void test1() throws Exception {
//        driver.get("http://localhost:8080/dbtest/#!/view1");
//        String expectedResult_null = "";
//        String expectedResult_stub = "stub";
//        String expectedResult_mongodb = "mongodb";
//        String expectedResult_neo4j = "neo4j";
//
//        WebElement e = driver.findElement(By.id("select_db_dropdown"));
//        Select dropdown = new Select(e);
//        List<WebElement> dropdownOptions = dropdown.getOptions();
//        
//        Assert.assertThat(4, is(dropdownOptions.size()));
//        Assert.assertThat(expectedResult_null, is(dropdownOptions.get(0).getText()));
//        Assert.assertThat(expectedResult_stub, is(dropdownOptions.get(1).getText()));
//        Assert.assertThat(expectedResult_mongodb, is(dropdownOptions.get(2).getText()));
//        Assert.assertThat(expectedResult_neo4j, is(dropdownOptions.get(3).getText()));
//    }
//
//    @Test
//    //Verify that page is loaded and all expected data are visible
//    //2. Verify that data is loaded, and the DOM is constructed in userstory 1
//    //Checks if the data are loaded correct
//    //Database = stub
//    public void test2() throws Exception {
//        driver.get("http://localhost:8080/dbtest/#!/view1");
//        String expectedBookTitleInFirstRow = "Autobiography of a Child";
//        String expectedBookAuthorInFirstRow = "Hannah Lynch";
//        
//        WebElement select_field = driver.findElement(By.id("select_db_dropdown"));
//        WebElement cityfield = driver.findElement(By.id("city"));
//        WebElement submit = driver.findElement(By.id("submit"));
//
//        Select dropdown = new Select(select_field);
//        dropdown.selectByValue("stub");
//        String input = "asd";
//        cityfield.sendKeys(input);
//        submit.click();
//        
//        TimeUnit.SECONDS.sleep(2);
//        WebElement bookstable = driver.findElement(By.id("books"));
//        WebElement body = bookstable.findElement(By.tagName("tbody"));
//        List<WebElement> rowList = body.findElements(By.tagName("tr"));
//        List<WebElement> td = rowList.get(0).findElements(By.tagName("td"));
//        String firstRowBookTitle = td.get(0).getText();
//        String firstRowBookAuthor = td.get(1).getText();
//        Assert.assertThat(7, is(rowList.size()));
//        Assert.assertThat(expectedBookTitleInFirstRow, is(firstRowBookTitle));
//        Assert.assertThat(expectedBookAuthorInFirstRow, is(firstRowBookAuthor));
//    }
//    @Test
//    //Verify that page is loaded and all expected data are visible
//    //3. Verify that the user will get error message when the city-field is empty
//    //Database = stub
//    public void test3() throws Exception {
//        driver.get("http://localhost:8080/dbtest/#!/view1");
//        String expectedErrorMessage = "ERROR:";
//        
//        WebElement select_field = driver.findElement(By.id("select_db_dropdown"));
//        //WebElement cityfield = driver.findElement(By.id("city"));
//        WebElement submit = driver.findElement(By.id("submit"));
//
//        Select dropdown = new Select(select_field);
//        dropdown.selectByValue("stub");
//        submit.click();
//        
//        TimeUnit.SECONDS.sleep(2);
//        WebElement errormsg = driver.findElement(By.id("alert_message_view1"));
//        String errormsgString = errormsg.getText();
//        System.out.println(errormsgString);
//        Assert.assertThat(expectedErrorMessage, is(errormsgString));
//    }
//    
//     @Test
//    //Verify that page is loaded and all expected data are visible
//    //4. Verify that the view returns correct values
//    // Database = Mongodb
//    public void test4() throws Exception {
//         driver.get("http://localhost:8080/dbtest/#!/view1");
//        String expectedBookTitleInFirstRow = "The Three Musketeers";
//        String expectedBookAuthorInFirstRow = "Alexandre Dumas";
//        
//        WebElement select_field = driver.findElement(By.id("select_db_dropdown"));
//        WebElement cityfield = driver.findElement(By.id("city"));
//        WebElement submit = driver.findElement(By.id("submit"));
//
//        Select dropdown = new Select(select_field);
//        dropdown.selectByValue("mongodb");
//        String input = "Madrid";
//        cityfield.sendKeys(input);
//        submit.click();
//        
//        TimeUnit.SECONDS.sleep(5);
//        WebElement bookstable = driver.findElement(By.id("books"));
//        WebElement body = bookstable.findElement(By.tagName("tbody"));
//        List<WebElement> rowList = body.findElements(By.tagName("tr"));
//        List<WebElement> td = rowList.get(0).findElements(By.tagName("td"));
//        String firstRowBookTitle = td.get(0).getText();
//        String firstRowBookAuthor = td.get(1).getText();
//        Assert.assertThat(2, is(rowList.size()));
//        Assert.assertThat(expectedBookTitleInFirstRow, is(firstRowBookTitle));
//        Assert.assertThat(expectedBookAuthorInFirstRow, is(firstRowBookAuthor));
//    }
//    
//    @Test
//    //Verify that page is loaded and all expected data are visible
//    //4. Verify that the user will get error message "No book found" when the city-field is empty
//    // Database = Mongodb
//    public void test5() throws Exception {
//        driver.get("http://localhost:8080/dbtest/#!/view1");
//        String expectedErrorMessage = "ERROR:";
//        
//        WebElement select_field = driver.findElement(By.id("select_db_dropdown"));
//        //WebElement cityfield = driver.findElement(By.id("city"));
//        WebElement submit = driver.findElement(By.id("submit"));
//        Select dropdown = new Select(select_field);
//        dropdown.selectByValue("mongodb");
//        submit.click();
//        
//        TimeUnit.SECONDS.sleep(2);
//        WebElement errormsg = driver.findElement(By.id("alert_message_view1"));
//        String errormsgString = errormsg.getText();
//        System.out.println(errormsgString);
//        Assert.assertThat(expectedErrorMessage, is(errormsgString));
//    }
//    
//      @Test
//    //Verify that page is loaded and all expected data are visible
//    //5. Verify that the view returns correct values
//    // Database = Neo4j
//    public void test6() throws Exception {
//        driver.get("http://localhost:8080/dbtest/#!/view1");
//        String expectedBookTitleInFirstRow = "Wee Macgreegor Enlists";
//        String expectedBookAuthorInFirstRow = "Bell, J. J. (John Joy)";
//        
//        WebElement select_field = driver.findElement(By.id("select_db_dropdown"));
//        WebElement cityfield = driver.findElement(By.id("city"));
//        WebElement submit = driver.findElement(By.id("submit"));
//
//        Select dropdown = new Select(select_field);
//        dropdown.selectByValue("neo4j");
//        String input = "Most";
//        cityfield.sendKeys(input);
//        submit.click();
//        
//        TimeUnit.SECONDS.sleep(5);
//        WebElement bookstable = driver.findElement(By.id("books"));
//        WebElement body = bookstable.findElement(By.tagName("tbody"));
//        List<WebElement> rowList = body.findElements(By.tagName("tr"));
//        List<WebElement> td = rowList.get(0).findElements(By.tagName("td"));
//        String firstRowBookTitle = td.get(0).getText();
//        String firstRowBookAuthor = td.get(1).getText();
//        Assert.assertThat(5, is(rowList.size()));
//        Assert.assertThat(expectedBookTitleInFirstRow, is(firstRowBookTitle));
//        Assert.assertThat(expectedBookAuthorInFirstRow, is(firstRowBookAuthor));
//    }
//    
//    
//    
//    
//
//}
