package de.swt.inf.model;

import de.swt.inf.controler.UserRegistrationController;
import de.swt.inf.database.DaoFactory;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class UserRegistrationTest {
    @BeforeClass
    public static void beforeClass() {
        DaoFactory.test = true;
        BeforSelenium.beforSeleniumTests();
    }

    @AfterClass
    public static void afterClass() {
        DaoFactory.test = false;
    }


    @Test
    @Parameters({"MeinNameIstC0@l", "J4V4R@ck5", "ReguläreaußdrückeSindS0wasVonC@@l", "KarlenderR0ck$", "1luvImp!"})
    public void testPasswordPolicyTrue(String password) {
        assertTrue(password.matches(User.passwordRegex));
        password = randomizeString(password);
        assertTrue(password.matches(User.passwordRegex));
        password = randomizeString(password);
        assertTrue(password.matches(User.passwordRegex));
        password = randomizeString(password);
        assertTrue(password.matches(User.passwordRegex));
    }

    @Test
    @Parameters({"MeinNameistCo0l", "KarlenderR@cks", "password", "regEx", "test123", "12345688"})
    public void testPasswordPolicyFalse(String password) {
        assertFalse(password.matches(User.passwordRegex));
        password = randomizeString(password);
        assertFalse(password.matches(User.passwordRegex));
        password = randomizeString(password);
        assertFalse(password.matches(User.passwordRegex));
        password = randomizeString(password);
        assertFalse(password.matches(User.passwordRegex));
    }

    @Test
    @Parameters({"KarlEnder", "ManniDasMamut", "DarthVader", "Hitler", "phirewire", "nini19", "TetrisIQ"})
    public void testUsernameRegexTrue(String username) {
        assertTrue(username.matches(User.userNameRegex));
        username = randomizeString(username);
        assertTrue(username.matches(User.userNameRegex));
        username = randomizeString(username);
        assertTrue(username.matches(User.userNameRegex));
        username = randomizeString(username);
        assertTrue(username.matches(User.userNameRegex));
    }

    @Test
    @Parameters({"Karl", "Manni", "Vader", "phire", "nini", "alex"})
    public void testUsernameRegexFalse(String username) {
        assertFalse(username.matches(User.userNameRegex));
        username = randomizeString(username);
        assertFalse(username.matches(User.userNameRegex));
        username = randomizeString(username);
        assertFalse(username.matches(User.userNameRegex));
        username = randomizeString(username);
        assertFalse(username.matches(User.userNameRegex));
    }

    @Test
    @Parameters({"Hitler", "Stalin", "Arschloch", "Penis"})
    public void testUsernameBlacklist(String username) throws IOException {
        //username sollte in der Blacklist stehen
        assertTrue(User.checkBlacklistedUsernames(username));
        //Der username sollte nicht mehr auf der blacklist stehen wenn der String anders angeordnet ist
        username = randomizeString(username);
        assertFalse(User.checkBlacklistedUsernames(username));
    }

    @Test
    //      Username , Email , Password
    @Parameters({
            "KarlEnder, karlender@web.de, J4V4R@ck5",
            "KlammerKarl, klammerkarl@yahoo.com, KarlenderR0ck$",
            "ManniDasMamut, mannidasmamut@gmail.com, Ice4ge!",
            "DarthVader, todesstern@imperium.rus, 1luvImp!"})
    public void seleniumTestRegistrationTrue(String username, String email, String password) {
        WebDriver driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:8080/registration");
        //fill in First Name
        WebElement element = driver.findElement(By.name("Vname"));
        element.sendKeys("JUnit Selenium Test Vorname");
        //fill in Last Name
        element = driver.findElement(By.name("Nname"));
        element.sendKeys("JUnit Selenium Test Nachname");
        //fill in Username
        element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        //fill in Email
        element = driver.findElement(By.name("email"));
        element.sendKeys(email);
        //fill in Password
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        //fill in Password Confirm
        element = driver.findElement(By.name("passwordconfirm"));
        element.sendKeys(password);
        //click on submit
        element = driver.findElement(By.name("submit"));
        element.click();
        //expected we are back on Dashboard
        assertTrue(driver.getTitle().contains("Login"));
    }


    @Test
    @Parameters({
            "KarlEnder1, karlender1@web.de, J4V4R@ck5",
            "KlammerKarl1, karlender1@web.de, KarlenderR0ck$",
            "ManniDasMamut1, karlender1@web.de, Ice4ge!",
            "DarthVader1, karlender1@web.de, 1luvImp!"})
    public void seleniumTestRegistrationEmailFalse(String username, String email, String password) {
        WebDriver driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:8080/registration");
        //fill in First Name
        WebElement element = driver.findElement(By.name("Vname"));
        element.sendKeys("JUnit Selenium Test Vorname");
        //fill in Last Name
        element = driver.findElement(By.name("Nname"));
        element.sendKeys("JUnit Selenium Test Nachname");
        //fill in Username
        element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        //fill in Email
        element = driver.findElement(By.name("email"));
        element.sendKeys(email);
        //fill in Password
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        //fill in Password Confirm
        element = driver.findElement(By.name("passwordconfirm"));
        element.sendKeys(password);
        //click on submit
        element = driver.findElement(By.name("submit"));
        element.click();
        if ((username.equals("KarlEnder1"))) {
            // put first user in database
            assertTrue(driver.getTitle().contains("Login"));
        } else {
            // other user with this email cant register
            element = driver.findElement(By.tagName("body"));
            assertTrue(element.getText().contains(UserRegistrationController.MSG_EMAIL_IN_USE));
        }
    }


    @Test
    //      Username , Email , Password
    @Parameters({
            "KarlEnder2, karlender2@web.de, J4V4R@ck5",
            "KarlEnder2, klammerkarl2@yahoo.com, KarlenderR0ck$",
            "KarlEnder2, mannidasmamut2@gmail.com, Ice4ge!",
            "KarlEnder2, todesstern2@imperium.rus, 1luvImp!"})
    public void seleniumTestRegistrationUsernameFalse(String username, String email, String password) {
        WebDriver driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:8080/registration");
        //fill in First Name
        WebElement element = driver.findElement(By.name("Vname"));
        element.sendKeys("JUnit Selenium Test Vorname");
        //fill in Last Name
        element = driver.findElement(By.name("Nname"));
        element.sendKeys("JUnit Selenium Test Nachname");
        //fill in Username
        element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        //fill in Email
        element = driver.findElement(By.name("email"));
        element.sendKeys(email);
        //fill in Password
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        //fill in Password Confirm
        element = driver.findElement(By.name("passwordconfirm"));
        element.sendKeys(password);
        //click on submit
        element = driver.findElement(By.name("submit"));
        element.click();
        if ((password.equals("J4V4R@ck5"))) {
            // register first User
            assertTrue(driver.getTitle().contains("Login"));
        } else {
            // other User with this username canot register
            element = driver.findElement(By.tagName("body"));
            assertTrue(element.getText().contains(UserRegistrationController.MSG_USERNAME_IN_USE));
        }
    }

    @Test
    //      Username , Email , Password
    @Parameters({
            "KarlEnder3, karlender3@web.de, MeinNameistCo0l",
            "KlammerKarl3, klammerkarl3@yahoo.com, KarlenderR@cks",
            "ManniDasMamut3, mannidasmamut3@gmail.com, password!",
            "DarthVader3, todesstern3@imperium.rus, test123!"})
    public void seleniumTestRegistrationPasswordFalse(String username, String email, String password) {
        WebDriver driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:8080/registration");
        //fill in First Name
        WebElement element = driver.findElement(By.name("Vname"));
        element.sendKeys("JUnit Selenium Test Vorname");
        //fill in Last Name
        element = driver.findElement(By.name("Nname"));
        element.sendKeys("JUnit Selenium Test Nachname");
        //fill in Username
        element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        //fill in Email
        element = driver.findElement(By.name("email"));
        element.sendKeys(email);
        //fill in Password
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        //fill in Password Confirm
        element = driver.findElement(By.name("passwordconfirm"));
        element.sendKeys(password);
        //click on submit
        element = driver.findElement(By.name("submit"));
        element.click();
        //we got an error message
        element = driver.findElement(By.tagName("body"));
        assertTrue(element.getText().contains(UserRegistrationController.MSG_PASSWORD_POLICY_NOT_SUFFUSED));
    }

    @Test
    //      Username , Email , Password
    @Parameters({
            "karl, karlender4@web.de, J4V4R@ck5",
            "hans, klammerkarl4@yahoo.com, KarlenderR0ck$",
            "manni, mannidasmamut4@gmail.com, Ice4ge!",
            "vader, todesstern4@imperium.rus, 1luvImp!"})
    public void seleniumTestRegistrationUsernameToShortFalse(String username, String email, String password) {
        WebDriver driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:8080/registration");
        //fill in First Name
        WebElement element = driver.findElement(By.name("Vname"));
        element.sendKeys("JUnit Selenium Test Vorname");
        //fill in Last Name
        element = driver.findElement(By.name("Nname"));
        element.sendKeys("JUnit Selenium Test Nachname");
        //fill in Username
        element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        //fill in Email
        element = driver.findElement(By.name("email"));
        element.sendKeys(email);
        //fill in Password
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        //fill in Password Confirm
        element = driver.findElement(By.name("passwordconfirm"));
        element.sendKeys(password);
        //click on submit
        element = driver.findElement(By.name("submit"));
        element.click();

        //we got an error message
        element = driver.findElement(By.tagName("body"));
        assertTrue(element.getText().contains(UserRegistrationController.MSG_USERNAME_POLICY_NOT_SUFFUSED));
    }

    @Test
    @Parameters({
            "KarlEnder5, karlender5@web.de, J4V4R@ck5",
            "KlammerKarl5, klammerkarl5@yahoo.com, KarlenderR0ck$",
            "ManniDasMamut5, mannidasmamut5@gmail.com, Ice4ge!",
            "DarthVader5, todesstern5@imperium.rus, 1luvImp!"
    })
    public void testPasswordsNotEqals(String username, String email, String password) {
        WebDriver driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:8080/registration");
        //fill in First Name
        WebElement element = driver.findElement(By.name("Vname"));
        element.sendKeys("JUnit Selenium Test Vorname");
        //fill in Last Name
        element = driver.findElement(By.name("Nname"));
        element.sendKeys("JUnit Selenium Test Nachname");
        //fill in Username
        element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        //fill in Email
        element = driver.findElement(By.name("email"));
        element.sendKeys(email);
        //fill in Password
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        //fill in Password Confirm
        element = driver.findElement(By.name("passwordconfirm"));
        element.sendKeys(password + "nop");
        //click on submit
        element = driver.findElement(By.name("submit"));
        element.click();

        //we got an error message
        element = driver.findElement(By.tagName("body"));
        assertTrue(element.getText().contains(UserRegistrationController.MSG_PASSWORD_DONT_MATCH));


    }

    /**
     * Die Idee, <br>
     * ein passwort bleibt richtig egal in welcher reinfolge die Buchstaben stehen <br>
     * Das Passwort bleibt falsch egal in welcher reinfolge die Buchstaben stehen <br>
     *
     * @param string Passwort
     * @return das veränderte Passwort
     */
    private String randomizeString(String string) {
        List<Character> characters = new ArrayList<Character>();
        for (char c : string.toCharArray()) {
            characters.add(c);
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        while (characters.size() != 0) {
            int rand = (int) (Math.random() * characters.size());
            stringBuilder.append(characters.remove(rand));
        }
        return stringBuilder.toString();
    }
}
