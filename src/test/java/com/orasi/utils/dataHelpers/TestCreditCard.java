package com.orasi.utils.dataHelpers;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.orasi.AutomationException;
import com.orasi.utils.dataHelpers.creditCards.CreditCard;
import com.orasi.utils.dataHelpers.creditCards.CreditCards;
import com.orasi.utils.dataHelpers.personFactory.Address;
import com.orasi.utils.dataHelpers.personFactory.Person;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestCreditCard {
    private CreditCard card;
    private Person person = new Person();

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("creditCardsConstructor")
    @Test(groups = { "regression", "utils", "CreditCard" })
    public void creditCardConstructor() {
        CreditCard card = CreditCards.VISA();
        Assert.assertNotNull(card);
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("creditCardsConstructor")
    @Test(groups = { "regression", "utils", "CreditCard" })
    public void creditCardPersonConstructor() {
        card = new CreditCards(person).VISA();
        Assert.assertNotNull(card);
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("creditCardsConstructor")
    @Test(groups = { "regression", "utils", "CreditCard" })
    public void creditCardPersonWillBillingConstructor() {
        Person peep = new Person();
        Address address = new Address();
        address.setType("Billing");
        peep.addAddress(address);
        CreditCard card = new CreditCards(peep).VISA();
        Assert.assertNotNull(card);
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getBillingCity")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getBillingCity() {
        Assert.assertTrue(card.getBillingCity().equals(person.primaryAddress().getCity()));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getBillingCountry")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getBillingCountry() {
        Assert.assertTrue(card.getBillingCountry().equals(person.primaryAddress().getCountryAbbv()));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getBillingState")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getBillingState() {
        Assert.assertTrue(card.getBillingState().equals(person.primaryAddress().getStateAbbv()));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getBillingStreet")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getBillingStreet() {
        Assert.assertTrue(card.getBillingStreet().equals(person.primaryAddress().getAddress1()));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getBillingStreet2")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getBillingStreet2() {
        Assert.assertTrue(card.getBillingStreet2().equals(person.primaryAddress().getAddress2()));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getBillingZip")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getBillingZip() {
        Assert.assertTrue(card.getBillingZip().equals(person.primaryAddress().getZipCode()));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getCardNumber")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getCardNumber() {
        Assert.assertTrue(card.getCardNumber().equals("4012888888881881"));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getCardType")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getCardType() {
        Assert.assertTrue(card.getCardType().equals("VISA"));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getExpireMonth")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getExpireMonth() {
        Assert.assertTrue(card.getExpireMonth().equals("12"));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getExpireYear")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getExpireYear() {
        Assert.assertTrue(card.getExpireYear().equals("20"));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getNameOnCard")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getNameOnCard() {
        Assert.assertTrue(card.getNameOnCard().equals(person.getFullName()));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getSecurityCode")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getSecurityCode() {
        Assert.assertTrue(card.getSecurityCode().equals("980"));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getCreditCardByType_AMEX")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getCreditCardByType_AMEX() {
        String type = "AMEX";
        CreditCard card = CreditCards.getCreditCardByType(type);
        Assert.assertNotNull(card);
        Assert.assertTrue(card.getCardType().equalsIgnoreCase(type));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getCreditCardByType_AMERICANEXPRESS")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getCreditCardByType_AMERICANEXPRESS() {
        String type = "AMEX";
        CreditCard card = CreditCards.getCreditCardByType("American Express");
        Assert.assertNotNull(card);
        Assert.assertTrue(card.getCardType().equalsIgnoreCase(type));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getCreditCardByType_DISC")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getCreditCardByType_DINERS() {
        String type = "DINERSCLUB";
        CreditCard card = CreditCards.getCreditCardByType("DINERS");
        Assert.assertNotNull(card);
        Assert.assertTrue(card.getCardType().equalsIgnoreCase(type));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getCreditCardByType_DISCOVER")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getCreditCardByType_DINERSCLUB() {
        String type = "DINERSCLUB";
        CreditCard card = CreditCards.getCreditCardByType(type);
        Assert.assertNotNull(card);
        Assert.assertTrue(card.getCardType().equalsIgnoreCase(type));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getCreditCardByType_DISC")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getCreditCardByType_DISC() {
        String type = "DISCOVER";
        CreditCard card = CreditCards.getCreditCardByType("DISC");
        Assert.assertNotNull(card);
        Assert.assertTrue(card.getCardType().equalsIgnoreCase(type));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getCreditCardByType_DISCOVER")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getCreditCardByType_DISCOVER() {
        String type = "DISCOVER";
        CreditCard card = CreditCards.getCreditCardByType(type);
        Assert.assertNotNull(card);
        Assert.assertTrue(card.getCardType().equalsIgnoreCase(type));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getCreditCardByType_DISC")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getCreditCardByType_JCB() {
        String type = "JCB";
        CreditCard card = CreditCards.getCreditCardByType("JCB");
        Assert.assertNotNull(card);
        Assert.assertTrue(card.getCardType().equalsIgnoreCase(type));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getCreditCardByType_MC")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getCreditCardByType_MC() {
        String type = "MASTERCARD";
        CreditCard card = CreditCards.getCreditCardByType("MC");
        Assert.assertNotNull(card);
        Assert.assertTrue(card.getCardType().equalsIgnoreCase(type));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getCreditCardByType_MASTERCARD")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getCreditCardByType_MASTERCARD() {
        String type = "MASTERCARD";
        CreditCard card = CreditCards.getCreditCardByType(type);
        Assert.assertNotNull(card);
        Assert.assertTrue(card.getCardType().equalsIgnoreCase(type));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getCreditCardByType_VISA")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getCreditCardByType_VISA() {
        String type = "VISA";
        CreditCard card = CreditCards.getCreditCardByType(type);
        Assert.assertNotNull(card);
        Assert.assertTrue(card.getCardType().equalsIgnoreCase(type));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getCreditCardByType_VISA_EXPIRED")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void getCreditCardByType_VISA_EXPIRED() {
        String type = "VISA_EXPIRED";
        CreditCard card = CreditCards.getCreditCardByType(type);
        Assert.assertNotNull(card);
        Assert.assertTrue(card.getCardType().equalsIgnoreCase(type));
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("getCreditCardByType_InvalidCard")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" }, expectedExceptions = AutomationException.class)
    public void getCreditCardByType_InvalidCard() {
        CreditCard card = CreditCards.getCreditCardByType("BLAH");
    }

    @Features("Utilities")
    @Stories("CreditCard")
    @Title("testToString")
    @Test(groups = { "regression", "utils", "CreditCard" }, dependsOnMethods = { "creditCardConstructor" })
    public void testToString() {
        Assert.assertTrue(card.toString().contains("[CreditCard cardType=VISA"));
    }
}
