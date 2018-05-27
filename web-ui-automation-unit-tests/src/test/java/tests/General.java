package tests;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;

import common.TestBase;
import common.UserInterface;

public class General extends TestBase
{
    private final static String GENERAL_URL = "https://www.w3schools.com/html/default.asp";
    
    private final static By HTML5_HEADER = By.xpath("//*[@id='main']/h1");
    private final static By H2_HEADERS = By.xpath("//*[@id='main']//h2");
    
    @Test
    public void getText()
    {
        UserInterface.Action.visitUrl(GENERAL_URL);
        
        String header = UserInterface.DataReceive.getText(HTML5_HEADER);
        
        UserInterface.Compare.equals("HTML5 Tutorial", header);
    }
    
    @Test
    public void getAllText()
    {
        UserInterface.Action.visitUrl(GENERAL_URL);
        
        List<String> actualHeaders = UserInterface.DataReceive.getTexts(H2_HEADERS);
        
        List<String> expectedHeaders = Arrays.asList("Examples in Every Chapter", "HTML Examples", "HTML Exercises and Quiz Test", "HTML References", "HTML Exam - Get Your Diploma!", "W3Schools' Online Certification");
        
        UserInterface.Compare.equals(expectedHeaders, actualHeaders);
    }
}
