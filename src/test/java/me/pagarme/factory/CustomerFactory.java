package me.pagarme.factory;

import java.util.Arrays;
import java.util.Collection;
import org.joda.time.LocalDate;

import me.pagar.model.Address;
import me.pagar.model.Customer;
import me.pagar.model.Phone;

public class CustomerFactory {

    public static final String DEFAULT_DOCUMENT_NUMBER = "34413106750";
    public static final String DEFAULT_DOCUMENT_TYPE = "cpf";
    public static final String DEFAULT_NAME = "Theo Emanuel Felipe Oliveira";
    public static final String DEFAULT_EMAIL = "theo.emanuel.oliveira@negocios-de-valor.com";
    public static final String DEFAULT_GENDER = "M";
    public static final LocalDate DEFAULT_BORN_AT = new LocalDate();
    public static final String DEFAULT_EXTERNAL_ID = "12345666";
    public static final Customer.Type DEFAULT_TYPE = Customer.Type.INDIVIDUAL;
    public static final String DEFAULT_COUNTRY = "br";
    public static final Collection<String> DEFAULT_PHONES = Arrays.asList("+551187486324", "+551187486327");
    public static final String DEFAULT_BIRTHDAY = "1994-11-05";

    private AddressFactory addressFactory = new AddressFactory();
    private DocumentFactory documentFactory = new DocumentFactory();
    private PhoneFactory phoneFactory = new PhoneFactory();

    public Customer create(Address address, Phone phone){
        Customer customer = create();
        customer.setAddress(address);
        customer.setPhone(phone);
        return customer;
    }

    public Customer create(){
        Customer customer = new Customer();
        customer.setBornAt(DEFAULT_BORN_AT);
        customer.setGender(DEFAULT_GENDER);
        customer.setEmail(DEFAULT_EMAIL);
        customer.setName(DEFAULT_NAME);
        customer.setDocumentNumber(DEFAULT_DOCUMENT_NUMBER);
        customer.setDocumentType(DEFAULT_DOCUMENT_TYPE);
        customer.setPhone(phoneFactory.create());
        customer.setAddress(addressFactory.create());
        return customer;
    }

    public Customer createApiV3() {
        Customer customer = new Customer();
        customer.setExternalId(DEFAULT_EXTERNAL_ID);
        customer.setName(DEFAULT_NAME);
        customer.setType(DEFAULT_TYPE);
        customer.setCountry(DEFAULT_COUNTRY);
        customer.setEmail(DEFAULT_EMAIL);
        customer.setDocuments(documentFactory.create());
        customer.setPhoneNumbers(DEFAULT_PHONES);
        customer.setBirthday(DEFAULT_BIRTHDAY);

        return customer;
    }
}
