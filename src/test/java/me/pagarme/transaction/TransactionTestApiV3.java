package me.pagarme.transaction;

import me.pagarme.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.pagar.model.Transaction;
import me.pagarme.factory.CustomerFactory;
import me.pagarme.factory.TransactionFactory;

public class TransactionTestApiV3 extends BaseTest {
    private CustomerFactory customerFactory = new CustomerFactory();
    private TransactionFactory transactionFactory = new TransactionFactory();
    private static Integer AMOUNT = 1000;

    @Before
    public void SetUpV3() {
        super.setUp("2017-08-28");
        transaction = new Transaction();
    }

    @Test
    public void testCreditCardTransactionApiV3() throws Throwable {
        transaction = transactionFactory.createCreditCardTransactionApiV3();
        transaction.setAmount(AMOUNT);
        customer = customerFactory.createApiV3();
        transaction.setCustomer(customer);
        transaction.save();

        Assert.assertEquals(Transaction.Status.PAID, transaction.getStatus());
    }

    @Test
    public void testBoletoTransactionApiV3() throws Throwable {
        transaction = transactionFactory.createBoletoTransactionApiV3();
        transaction.setAmount(AMOUNT);
        customer = customerFactory.createApiV3();
        transaction.setCustomer(customer);
        transaction.save();

        Assert.assertEquals(Transaction.Status.WAITING_PAYMENT, transaction.getStatus());
    }
}
