package pl.kayzone.exchange.model;

import org.bson.types.ObjectId;
import pl.kayzone.exchange.model.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestClassCreator {

    Currency currency;
    CurrencyCourse currencyCourse;
    Customers customers;
    Transaction transaction;
    TransactionCurrency transactionCurrency;

    public TestClassCreator() {
        /*this.currency = new Currency();
        this.currencyCourse = new CurrencyCourse();
        this.customers = new Customers();
        this.transaction = new Transaction();
        this.transactionCurrency = new TransactionCurrency();*/
    }

    public Currency getCurrency() {
        this.currency = new Currency();
             currency.setIdCode("USD");
             currency.setName("dolar amerykański");
             currency.setTablesType("A");
             currency.setUrlNbp("http://api.nbp.pl/api/exchangerates/rates/{table}/{code}/");
             currency.setRates(1.0);
             currency.setLastUpdate(LocalDateTime.now());
        return currency;
    }

    public CurrencyCourse getCurrencyCourse() {
        this.currencyCourse = new CurrencyCourse();
             currencyCourse.setId(new ObjectId());
             currencyCourse.setIdCode(getCurrency());
             currencyCourse.setActive(true);
             currencyCourse.setAsk(BigDecimal.valueOf(3.45));
             currencyCourse.setBid(BigDecimal.valueOf(3.54));
             currencyCourse.setDate(LocalDateTime.now());
             currencyCourse.setValidTo(LocalDateTime.now().plusDays(1L));
             return currencyCourse;
    }
    public Customers getCustomers() {
        this.customers = new Customers();
             customers.setId(new ObjectId());
             customers.setName("Kowalski");
             customers.setFirstName("Jan");
             customers.setSurname("Kowalski");
             customers.setAddress("ul.Blotna 22");
             customers.setCity("Wrzeszcz");
             customers.setZip("43-334");
             customers.setCountry("Poland");
             customers.setNip("883-220-90-33");
        return customers;
    }

    public TransactionCurrency getTransactionCurrency() {
        this.transactionCurrency = new TransactionCurrency();
             transactionCurrency.setCurrencyCourse(getCurrencyCourse());
             transactionCurrency.setCourse(BigDecimal.valueOf(3.44));
             transactionCurrency.setQuantity(BigDecimal.valueOf(100.00));
             return transactionCurrency;
    }
    public Transaction getTransaction() {
        List<TransactionCurrency> items = new ArrayList<>();
         items.add(getTransactionCurrency());
        this.transaction = new Transaction();
             transaction.setId(new ObjectId());
             transaction.setCustomers(getCustomers());
             transaction.setTransactionCurrencyList(items);
             transaction.setTransactionTime(LocalDateTime.now());
             BigDecimal value  = getTransactionCurrency().getQuantity().multiply(getTransactionCurrency().getCourse());
             transaction.setValueTransaction(value);
             return transaction;
    }
}

