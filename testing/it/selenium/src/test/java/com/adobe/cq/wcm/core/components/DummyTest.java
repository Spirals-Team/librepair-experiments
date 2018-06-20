package com.adobe.cq.wcm.core.components;

import org.junit.BeforeClass;
import org.junit.Test;

import com.adobe.cq.model.LoginPage;
import com.codeborne.selenide.Selenide;
import junit.framework.TestCase;
import com.adobe.cq.model.EditorPage;

public class DummyTest extends TestCase {

    private static String cqUsername = System.getProperty("cq.username", "enter-your-aem-username");
    private static String cqPassword = System.getProperty("cq.password", "enter-your-aem-password");


    @BeforeClass
    public void setUp() throws Exception {
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.login(cqUsername, cqPassword);
    }

    @Test
    public void testSimple() throws Exception {
        EditorPage editorPage = Selenide.open("/editor.html/content/we-retail/language-masters/en/about-us.html", EditorPage.class);

    }

}
