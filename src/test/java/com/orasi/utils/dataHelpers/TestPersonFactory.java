package com.orasi.utils.dataHelpers;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.orasi.utils.dataHelpers.creditCards.CardTypes;
import com.orasi.utils.dataHelpers.creditCards.CreditCard;
import com.orasi.utils.dataHelpers.creditCards.CreditCards;
import com.orasi.utils.dataHelpers.personFactory.Address;
import com.orasi.utils.dataHelpers.personFactory.Email;
import com.orasi.utils.dataHelpers.personFactory.Party;
import com.orasi.utils.dataHelpers.personFactory.Person;
import com.orasi.utils.dataHelpers.personFactory.Phone;
import com.orasi.utils.dataHelpers.personFactory.seeds.FemaleFirstNames;
import com.orasi.utils.dataHelpers.personFactory.seeds.MaleFirstNames;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestPersonFactory {
    Party party = new Party(1);

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("partyConstructor")
    @Test(groups = { "regression", "utils", "Party" })
    public void partyConstructor() {
        Assert.assertNotNull(party);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("partyPrimaryPerson")
    @Test(groups = { "regression", "utils", "Party" })
    public void partyPrimaryPerson() {
        Assert.assertNotNull(party.primaryPerson());
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("partyGetAllPersons")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "partyPrimaryPerson")
    public void partyGetAllPersons() {
        Assert.assertTrue(party.getAllPersons().size() == 1);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("partyAddPerson")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "partyGetAllPersons")
    public void partyAddPerson() {
        party.addPerson(new Person());
        party.addPerson(new Person());
        party.getAllPersons().get(1).setAge("15");
        party.getAllPersons().get(2).setAge("2");
        Assert.assertNotNull(party.getAllPersons().size() == 3);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("partyNumberOfAdults")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "partyAddPerson")
    public void partyNumberOfAdults() {
        Assert.assertTrue(party.numberOfAdults() == 1);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("partyNumberOfChildren")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "partyAddPerson")
    public void partyNumberOfChildren() {
        Assert.assertTrue(party.numberOfChildren() == 1);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("partyNumberOfInfants")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "partyAddPerson")
    public void partyNumberOfInfants() {
        Assert.assertTrue(party.numberOfInfants() == 1);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("maleFirstNames")
    @Test(groups = { "regression", "utils", "Party" })
    public void maleFirstNames() {
        Assert.assertNotNull(MaleFirstNames.getFirstName());
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("femaleFirstNames")
    @Test(groups = { "regression", "utils", "Party" })
    public void femaleFirstNames() {
        Assert.assertNotNull(FemaleFirstNames.getFirstName());
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personAge")
    @Test(groups = { "regression", "utils", "Party" })
    public void personAge() {
        party.primaryPerson().setAge("35");
        Assert.assertTrue(party.primaryPerson().getAge().equals("35"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personBirthDate")
    @Test(groups = { "regression", "utils", "Party" })
    public void personBirthDate() {
        party.primaryPerson().setBirthDate("05-05-2005");
        Assert.assertTrue(party.primaryPerson().getBirthDate().equals("05-05-2005"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personDeceased")
    @Test(groups = { "regression", "utils", "Party" })
    public void personDeceased() {
        party.primaryPerson().setDeceased(false);
        Assert.assertFalse(party.primaryPerson().getDeceased());
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personFirstName")
    @Test(groups = { "regression", "utils", "Party" })
    public void personFirstName() {
        party.primaryPerson().setFirstName("Joe");
        Assert.assertTrue(party.primaryPerson().getFirstName().equals("Joe"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("MiddleName")
    @Test(groups = { "regression", "utils", "Party" })
    public void personMiddleName() {
        party.primaryPerson().setMiddleName("Schmoe");
        Assert.assertTrue(party.primaryPerson().getMiddleName().equals("Schmoe"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personLastName")
    @Test(groups = { "regression", "utils", "Party" })
    public void personLastName() {
        party.primaryPerson().setLastName("Wat");
        Assert.assertTrue(party.primaryPerson().getLastName().equals("Wat"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personFullName")
    @Test(groups = { "regression", "utils", "Party" })
    public void personFullName() {
        party.primaryPerson().setLastName("Wat");
        Assert.assertTrue(party.primaryPerson().getFullName().equals(party.primaryPerson().getFirstName() + " " + party.primaryPerson().getLastName()));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personNickname")
    @Test(groups = { "regression", "utils", "Party" })
    public void personNickname() {
        party.primaryPerson().setNickname("Joey");
        Assert.assertTrue(party.primaryPerson().getNickname().equals("Joey"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personPassword")
    @Test(groups = { "regression", "utils", "Party" })
    public void personPassword() {
        party.primaryPerson().setPassword("secret");
        Assert.assertTrue(party.primaryPerson().getPassword().equals("secret"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personPrimary")
    @Test(groups = { "regression", "utils", "Party" })
    public void personPrimary() {
        party.primaryPerson().setPrimary(true);
        Assert.assertTrue(party.primaryPerson().isPrimary());
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personSsn")
    @Test(groups = { "regression", "utils", "Party" })
    public void personSsn() {
        party.primaryPerson().setSsn("0123456");
        Assert.assertTrue(party.primaryPerson().getSsn().equals("0123456"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personSuffix")
    @Test(groups = { "regression", "utils", "Party" })
    public void personSuffix() {
        party.primaryPerson().setSuffix("Jr.");
        Assert.assertTrue(party.primaryPerson().getSuffix().equals("Jr."));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personTitle")
    @Test(groups = { "regression", "utils", "Party" })
    public void personTitle() {
        party.primaryPerson().setTitle("Mstr.");
        Assert.assertTrue(party.primaryPerson().getTitle().equals("Mstr."));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personUsername")
    @Test(groups = { "regression", "utils", "Party" })
    public void personUsername() {
        party.primaryPerson().setUsername("Joe.Wat");
        Assert.assertTrue(party.primaryPerson().getUsername().equals("Joe.Wat"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personPrimaryAddress")
    @Test(groups = { "regression", "utils", "Party" })
    public void personPrimaryAddress() {
        Assert.assertNotNull(party.primaryPerson().primaryAddress());
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personPrimaryPhone")
    @Test(groups = { "regression", "utils", "Party" })
    public void personPrimaryPhone() {
        Assert.assertNotNull(party.primaryPerson().primaryPhone());
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personPrimaryEmail")
    @Test(groups = { "regression", "utils", "Party" })
    public void personPrimaryEmail() {
        Assert.assertNotNull(party.primaryPerson().primaryEmail());
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personAllAddress")
    @Test(groups = { "regression", "utils", "Party" })
    public void personAllAddress() {
        Assert.assertTrue(party.primaryPerson().getAllAddresses().size() == 1);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personAllPhone")
    @Test(groups = { "regression", "utils", "Party" })
    public void personAllPhone() {
        Assert.assertTrue(party.primaryPerson().getAllPhones().size() == 1);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personAllEmail")
    @Test(groups = { "regression", "utils", "Party" })
    public void personAllEmail() {
        Assert.assertTrue(party.primaryPerson().getAllEmails().size() == 1);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personAddress")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAllAddress")
    public void personAddAddressDefault() {
        party.primaryPerson().addAddress();
        Assert.assertNotNull(party.primaryPerson().getAllAddresses().size() == 2);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personAddress")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddAddressDefault")
    public void personAddAddressConstructor() {
        Address address = new Address();
        party.primaryPerson().addAddress(address);
        Assert.assertNotNull(party.primaryPerson().getAllAddresses().size() == 3);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personPhone")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAllPhone")
    public void personAddPhoneDefault() {
        party.primaryPerson().addPhone();
        Assert.assertNotNull(party.primaryPerson().getAllPhones().size() == 2);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("pePhoneress")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddPhoneDefault")
    public void personAddPhoneConstructor() {
        Phone phone = new Phone();
        party.primaryPerson().addPhone(phone);
        Assert.assertNotNull(party.primaryPerson().getAllPhones().size() == 3);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personEmail")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAllEmail")
    public void personAddEmailDefault() {
        party.primaryPerson().addEmail();
        Assert.assertNotNull(party.primaryPerson().getAllPhones().size() == 2);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personEmail")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddEmailDefault")
    public void personAddEmailConstructor() {
        Email email = new Email();
        party.primaryPerson().addEmail(email);
        Assert.assertNotNull(party.primaryPerson().getAllEmails().size() == 3);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("addressAddress1")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddAddressConstructor")
    public void addressAddress1() {
        party.primaryPerson().primaryAddress().setAddress1("123 Test Lane");
        Assert.assertTrue(party.primaryPerson().primaryAddress().getAddress1().equals("123 Test Lane"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("addressAddress2")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddAddressConstructor")
    public void addressAddress2() {
        party.primaryPerson().primaryAddress().setAddress2("Blah");
        Assert.assertTrue(party.primaryPerson().primaryAddress().getAddress2().equals("Blah"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("addressCity")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddAddressConstructor")
    public void addressCity() {
        party.primaryPerson().primaryAddress().setCity("Greensboro");
        Assert.assertTrue(party.primaryPerson().primaryAddress().getCity().equals("Greensboro"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("addressCountry")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddAddressConstructor")
    public void addressCountry() {
        party.primaryPerson().primaryAddress().setCountry("United States");
        Assert.assertTrue(party.primaryPerson().primaryAddress().getCountry().equals("United States"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("addressCountryAbbv")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddAddressConstructor")
    public void addressCountryAbbv() {
        party.primaryPerson().primaryAddress().setCountryAbbv("US");
        Assert.assertTrue(party.primaryPerson().primaryAddress().getCountryAbbv().equals("US"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("addressLocatorId")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddAddressConstructor")
    public void addressLocatorId() {
        party.primaryPerson().primaryAddress().setLocatorId("1234");
        Assert.assertTrue(party.primaryPerson().primaryAddress().getLocatorId().equals("1234"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("addressOptIn")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddAddressConstructor")
    public void addressOptIn() {
        party.primaryPerson().primaryAddress().setOptIn(true);
        Assert.assertTrue(party.primaryPerson().primaryAddress().isOptIn());
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("addressPrimary")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddAddressConstructor")
    public void addressPrimary() {
        party.primaryPerson().primaryAddress().setPrimary(true);
        Assert.assertTrue(party.primaryPerson().primaryAddress().isPrimary());
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("addressState")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddAddressConstructor")
    public void addressState() {
        party.primaryPerson().primaryAddress().setState("North Carolina");
        Assert.assertTrue(party.primaryPerson().primaryAddress().getState().equals("North Carolina"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("addressStateAbbv")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddAddressConstructor")
    public void addressStateAbbv() {
        party.primaryPerson().primaryAddress().setStateAbbv("NC");
        Assert.assertTrue(party.primaryPerson().primaryAddress().getStateAbbv().equals("NC"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("addressStreetName")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "addressAddress1")
    public void addressStreetName() {
        party.primaryPerson().primaryAddress().setStreetName("Telsa Lane");
        Assert.assertTrue(party.primaryPerson().primaryAddress().getStreetName().equals("Telsa Lane"));
        Assert.assertTrue(party.primaryPerson().primaryAddress().getAddress1().equals("123 Telsa Lane"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("addressStreetNumber")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "addressStreetName")
    public void addressStreetNumber() {
        party.primaryPerson().primaryAddress().setStreetNumber("543");
        Assert.assertTrue(party.primaryPerson().primaryAddress().getStreetNumber().equals("543"));
        Assert.assertTrue(party.primaryPerson().primaryAddress().getAddress1().equals("543 Telsa Lane"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("addressType")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddAddressConstructor")
    public void addressType() {
        party.primaryPerson().primaryAddress().setType("Business");
        Assert.assertTrue(party.primaryPerson().primaryAddress().getType().equals("Business"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("addressZipCode")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddAddressConstructor")
    public void addressZipCode() {
        party.primaryPerson().primaryAddress().setZipCode("27409");
        Assert.assertTrue(party.primaryPerson().primaryAddress().getZipCode().equals("27409"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("phoneType")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddPhoneConstructor")
    public void phoneType() {
        party.primaryPerson().primaryPhone().setType("Business");
        Assert.assertTrue(party.primaryPerson().primaryPhone().getType().equals("Business"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("phoneCountry")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddPhoneConstructor")
    public void phoneCountry() {
        party.primaryPerson().primaryPhone().setCountry("Mexico");
        Assert.assertTrue(party.primaryPerson().primaryPhone().getCountry().equals("Mexico"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("phoneLocatorId")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddPhoneConstructor")
    public void phoneLocatorId() {
        party.primaryPerson().primaryPhone().setLocatorId("154");
        Assert.assertTrue(party.primaryPerson().primaryPhone().getLocatorId().equals("154"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("phoneNumber")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddPhoneConstructor")
    public void phoneNumber() {
        party.primaryPerson().primaryPhone().setNumber("1234567890");
        Assert.assertTrue(party.primaryPerson().primaryPhone().getNumber().equals("1234567890"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("phoneFormattedNumber")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "phoneNumber")
    public void phoneFormattedNumber() {
        Assert.assertTrue(party.primaryPerson().primaryPhone().getFormattedNumber().equals("123-456-7890"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("phoneFormattedNumberCustomFormat")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "phoneNumber")
    public void phoneFormattedNumberCustomFormat() {
        Assert.assertTrue(party.primaryPerson().primaryPhone().getFormattedNumber("(###) ###-####").equals("(123) 456-7890"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("phonePrimary")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddPhoneConstructor")
    public void phonePrimary() {
        party.primaryPerson().primaryPhone().setPrimary(true);
        Assert.assertTrue(party.primaryPerson().primaryPhone().isPrimary());
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("emailPrimary")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddEmailConstructor")
    public void emailPrimary() {
        party.primaryPerson().primaryEmail().setPrimary(true);
        Assert.assertTrue(party.primaryPerson().primaryEmail().isPrimary());
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("emailType")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddEmailConstructor")
    public void emailType() {
        party.primaryPerson().primaryEmail().setType("Business");
        Assert.assertTrue(party.primaryPerson().primaryEmail().getType().equals("Business"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("emailCountry")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddEmailConstructor")
    public void emailCountry() {
        party.primaryPerson().primaryEmail().setCountry("Mexico");
        Assert.assertTrue(party.primaryPerson().primaryEmail().getCountry().equals("Mexico"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("emailAddress")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddEmailConstructor")
    public void emailAddress() {
        party.primaryPerson().primaryEmail().setEmail("test@automation.com");
        Assert.assertTrue(party.primaryPerson().primaryEmail().getEmail().equals("test@automation.com"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("emailLocatorId")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddEmailConstructor")
    public void emailLocatorId() {
        party.primaryPerson().primaryEmail().setLocatorId("154");
        Assert.assertTrue(party.primaryPerson().primaryEmail().getLocatorId().equals("154"));
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("emailOptIn")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddEmailConstructor")
    public void emailOptIn() {
        party.primaryPerson().primaryEmail().setOptIn(true);
        Assert.assertTrue(party.primaryPerson().primaryEmail().isOptIn());
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personAllCreditCards")
    @Test(groups = { "regression", "utils", "Party" })
    public void personAllCreditCards() {
        Assert.assertTrue(party.primaryPerson().getAllCreditCards().size() == 1);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personAddDefaultCreditCard")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAllCreditCards")
    public void personAddCreditCardDefault() {
        party.primaryPerson().addCreditCard();
        Assert.assertTrue(party.primaryPerson().getAllCreditCards().size() == 2);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personAddCreditCardByString")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddCreditCardDefault")
    public void personAddCreditCardConstructor() {
        CreditCard creditcard = CreditCards.getCreditCardByType("AMEX");
        party.primaryPerson().addCreditCard(creditcard);
        Assert.assertTrue(party.primaryPerson().getAllCreditCards().size() == 3);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personAddCreditCardByType")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddCreditCardConstructor")
    public void personAddCreditCardConstructorEnum() {
        CreditCard creditcard = CreditCards.getCreditCardByType(CardTypes.DISCOVER);
        party.primaryPerson().addCreditCard(creditcard);
        Assert.assertTrue(party.primaryPerson().getAllCreditCards().size() == 4);
    }

    @Features("Utilities")
    @Stories("PersonFactory")
    @Title("personCreditCardPrimary")
    @Test(groups = { "regression", "utils", "Party" }, dependsOnMethods = "personAddCreditCardConstructor")
    public void personCreditCardPrimary() {
        Assert.assertNotNull(party.primaryPerson().primaryCreditCard());
        Assert.assertTrue(party.primaryPerson().primaryCreditCard().getCardType().equalsIgnoreCase("Visa"));
    }
}
