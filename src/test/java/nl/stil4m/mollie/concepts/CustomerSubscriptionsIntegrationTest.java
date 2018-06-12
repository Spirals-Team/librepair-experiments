package nl.stil4m.mollie.concepts;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import nl.stil4m.mollie.Client;
import nl.stil4m.mollie.ResponseOrError;
import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static nl.stil4m.mollie.TestUtil.strictClientWithApiKey;
import nl.stil4m.mollie.domain.CreateCustomer;
import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.CreateSubscription;
import nl.stil4m.mollie.domain.Customer;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Payment;
import nl.stil4m.mollie.domain.Subscription;
import nl.stil4m.mollie.domain.customerpayments.FirstRecurringPayment;
import nl.stil4m.mollie.domain.subpayments.ideal.CreateIdealPayment;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CustomerSubscriptionsIntegrationTest {

    private CustomerPayments customerPayments;
    private CustomerSubscriptions customerSubscriptions;
    private WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        //setup selenium
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void before() throws InterruptedException, IOException {
        Thread.sleep(TEST_TIMEOUT);
        Client client = strictClientWithApiKey(VALID_API_KEY);

        String uuid = UUID.randomUUID().toString();
        Map<String, Object> defaultMetadata = new HashMap<>();
        defaultMetadata.put("foo", "bar");
        defaultMetadata.put("id", uuid);

        String name = "Test Customer " + uuid;
        Customer customer = client.customers().create(new CreateCustomer(name, uuid + "@foobar.com", Optional.empty(), defaultMetadata)).getData();

        customerPayments = client.customerPayments(customer.getId());
        customerSubscriptions = client.customerSubscriptions(customer.getId());

        //create new selenium driver
        driver = new ChromeDriver();
    }

    @After
    public void after() {
        driver.quit();
    }

    @Test
    public void testList() throws IOException, URISyntaxException {
        ResponseOrError<Page<Subscription>> list = customerSubscriptions.list(Optional.empty(), Optional.empty());

        assertThat(list.getSuccess(), is(true));
    }

    @Test
    public void testCreate() throws IOException, URISyntaxException, InterruptedException {
        // https://www.mollie.com/en/docs/reference/subscriptions/create
        //create payment that creates membate (required to create subscription), this is kinda a confirm for the payment info to do the recurring payment over
        CreatePayment createIdealPayment = new CreateIdealPayment(10d, "test payment for subscription", "https://example.com/thank/you", Optional.empty(), null, null);
        FirstRecurringPayment customerPayment = new FirstRecurringPayment(createIdealPayment);
        Payment payment = customerPayments.create(customerPayment).getData();

        String paymentUrl = payment.getLinks().getPaymentUrl();

        //open paymentUrl and set it to paid
        //open payment link
        driver.get(paymentUrl);
        // Find ok button and click it
        driver.findElement(By.name("issuer")).click();

        //find "paid" radiobutton and click it
        driver.findElements(By.name("final_state")).forEach(radioButton -> {
            if (radioButton.getAttribute("value").equals("paid")) {
                radioButton.click();
            }
        });

        //submit form
        driver.findElement(By.cssSelector("#footer > button")).click();

        //check if payment is complete
        payment = customerPayments.get(payment.getId()).getData();//get new version of payment
        if (!payment.getStatus().equals("paid")) {
            fail("completing payment failed");
            return;
        }

        Double amount = 10d;
        Integer times = 2;
        String interval = "7 days";
        Date startDate = new Date();
        String description = "test subscription";
        String webhookUrl = "https://example.com/api/payment";

        CreateSubscription createSubscription = new CreateSubscription(amount, times, interval, new SimpleDateFormat("yyyy-MM-dd").format(startDate), description, null, webhookUrl);

        ResponseOrError<Subscription> result = customerSubscriptions.create(createSubscription);

        assertThat(result.getSuccess(), is(true));
        assertThat(result.getData().getId(), notNullValue());
        assertThat(result.getData().getAmount(), is(amount));
        assertThat(result.getData().getTimes(), is(times));
        assertThat(result.getData().getInterval(), is(interval));
        assertThat(result.getData().getStartDate(), notNullValue());//time will differ due to timezone-stuff
        assertThat(result.getData().getDescription(), is(description));
        assertThat(result.getData().getLinks().getWebhookUrl(), is(webhookUrl));
    }

    @Test
    public void testCancel() throws IOException, URISyntaxException, InterruptedException {
        // https://www.mollie.com/en/docs/reference/subscriptions/create
        //create payment that creates membate (required to create subscription), this is kinda a confirm for the payment info to do the recurring payment over
        CreatePayment createIdealPayment = new CreateIdealPayment(10d, "test payment for subscription", "https://example.com/thank/you", Optional.empty(), null, null);
        FirstRecurringPayment customerPayment = new FirstRecurringPayment(createIdealPayment);
        Payment payment = customerPayments.create(customerPayment).getData();

        String paymentUrl = payment.getLinks().getPaymentUrl();

        //open paymentUrl and set it to paid
        //setup selenium
        WebDriver driver = new ChromeDriver();

        driver.get(paymentUrl);
        // Find ok button and click it
        driver.findElement(By.name("issuer")).click();

        //find "paid" radiobutton and click it
        driver.findElements(By.name("final_state")).forEach(radioButton -> {
            if (radioButton.getAttribute("value").equals("paid")) {
                radioButton.click();
            }
        });

        //submit form
        driver.findElement(By.cssSelector("#footer > button")).click();

        //check if payment is complete
        payment = customerPayments.get(payment.getId()).getData();//get new version of payment
        if (!payment.getStatus().equals("paid")) {
            fail("completing payment failed");
            return;
        }

        Double amount = 10d;
        Integer times = 2;
        String interval = "7 days";
        Date startDate = new Date();
        String description = "test subscription";
        String webhookUrl = "https://example.com/api/payment";

        CreateSubscription createSubscription = new CreateSubscription(amount, times, interval, new SimpleDateFormat("yyyy-MM-dd").format(startDate), description, null, webhookUrl);

        ResponseOrError<Subscription> result = customerSubscriptions.create(createSubscription);
        Subscription subscription = result.getData();

        //cancel subscription
        result = customerSubscriptions.delete(subscription.getId());
        subscription = result.getData();
        assertEquals("cancelled", subscription.getStatus());
        assertNotNull(subscription.getCancelledDatetime());
    }
}
