package bank;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentServiceTest {

    @Test
    public void shouldCreatePaymentServiceAndCallMethod() throws Exception {
        PaymentService testedObject = new PaymentService();
        Account from = new Account("", 0);
        Account to = new Account("", 0);

        testedObject.transferMoney(from, to, 0);
    }

    @Test
    public void shouldHaveCorrectAmountsAfterTransfer() {
        PaymentService testedObject = new PaymentService();
        Account from = new Account("from", 10);
        Account to = new Account("to", 20);

        testedObject.transferMoney(from, to, 1);

        assertThat(from.getBalance()).isEqualTo(9);
        assertThat(to.getBalance()).isEqualTo(21);
    }


}
