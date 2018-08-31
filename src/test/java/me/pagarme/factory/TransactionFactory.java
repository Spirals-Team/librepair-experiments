package me.pagarme.factory;

import org.joda.time.LocalDate;
import java.util.Collection;

import me.pagar.model.Billing;
import me.pagar.model.Card;
import me.pagar.model.Item;
import me.pagar.model.Shipping;
import me.pagar.model.Transaction;

public class TransactionFactory {

	public static final Integer AMOUNT = 100;
	public static final String EMV_DATA = "9F26009F02009F10009F37009F360095009A009C005F2A009F1A0082009F03009F33009F3400";
	public static final Integer INSTALLMENTS = 1;
	public static final String CARD_TRACK_1 = "0";
	public static final String CARD_TRACK_2 = "0";
	public static final String CARD_TRACK_3 = "0";

    /**
     * @param pinMode can be online or offline. With online option, the bank will check the card, and with offline the pin is used to check the card. 
     * @return the transaction
     */
        private BillingFactory billingFactory = new BillingFactory();
        private CustomerFactory customerFactory = new CustomerFactory();
        private CardFactory cardFactory = new CardFactory();
        private ItemFactory itemFactory = new ItemFactory();
        private ShippingFactory shippingFactory = new ShippingFactory();

    public Transaction createCreditCardOfflineTransaction() {

        Card card = cardFactory.create();

        Transaction transaction = new Transaction();

        transaction.setAmount(AMOUNT);
        transaction.setPaymentMethod(Transaction.PaymentMethod.DEBIT_CARD);
        transaction.setCaptureMethod(Transaction.CaptureMethod.EMV);
        transaction.setCardHolderName(card.getHolderName());
        transaction.setCardExpirationDate(card.getExpiresAt());
        transaction.setCardNumber(card.getNumber());
        transaction.setInstallments(INSTALLMENTS);
        transaction.setCardEmvData(EMV_DATA);
        transaction.setCardTrack1(CARD_TRACK_1);
        transaction.setCardTrack2(CARD_TRACK_2);
        transaction.setCardTrack3(CARD_TRACK_3);
        transaction.setCardPinMode("offline");

        return transaction;
    }

    public Transaction createCreditCardOnlineTransaction(){

        Card card = cardFactory.create();

        Transaction transaction = new Transaction();

        transaction.setAmount(AMOUNT);
        transaction.setPaymentMethod(Transaction.PaymentMethod.DEBIT_CARD);
        transaction.setCaptureMethod(Transaction.CaptureMethod.EMV);
        transaction.setCardHolderName(card.getHolderName());
        transaction.setCardExpirationDate(card.getExpiresAt());
        transaction.setCardNumber(card.getNumber());
        transaction.setInstallments(INSTALLMENTS);
        transaction.setCardEmvData(EMV_DATA);
        transaction.setCardTrack1(CARD_TRACK_1);
        transaction.setCardTrack2(CARD_TRACK_2);
        transaction.setCardTrack3(CARD_TRACK_3);
        transaction.setCardPinMode("online");

        transaction.setCardPin("3131313131313131313131313131313131313131");
        transaction.setCardPinKek("3131313131313131313131313131313131313131");

        return transaction;
    }

    public Transaction createCreditCardTransactionWithoutPinMode() {

        Card card = cardFactory.create();

        Transaction transaction = new Transaction();

        transaction.setAmount(AMOUNT);
        transaction.setPaymentMethod(Transaction.PaymentMethod.CREDIT_CARD);
        transaction.setCardHolderName(card.getHolderName());
        transaction.setCardExpirationDate(card.getExpiresAt());
        transaction.setCardCvv(Integer.toString(card.getCvv()));
        transaction.setCardNumber(card.getNumber());
        transaction.setInstallments(INSTALLMENTS);

        return transaction;
    }

    public Transaction createBoletoTransaction() {

        Transaction transaction = new Transaction();

        transaction.setAmount(AMOUNT);
        transaction.setPaymentMethod(Transaction.PaymentMethod.BOLETO);
        return transaction;
    }

    public Transaction createCreditCardTransactionApiV3() {
        Card card = cardFactory.create();
        Billing billing = billingFactory.create();
        Shipping shipping = shippingFactory.create();
        Collection<Item> items = itemFactory.create();

        Transaction transaction = new Transaction();
        transaction.setPaymentMethod(Transaction.PaymentMethod.CREDIT_CARD);
        transaction.setCardHolderName(card.getHolderName());
        transaction.setCardNumber(card.getNumber());
        transaction.setCardExpirationDate(card.getExpiresAt());
        transaction.setCardCvv(Integer.toString(card.getCvv()));
        transaction.setBilling(billing);
        transaction.setShipping(shipping);
        transaction.setItems(items);

        return transaction;
    }

    public Transaction createBoletoTransactionApiV3() {
        Billing billing = billingFactory.create();
        Shipping shipping = shippingFactory.create();
        Collection<Item> items = itemFactory.create();

        Transaction transaction = new Transaction();
        transaction.setPaymentMethod(Transaction.PaymentMethod.BOLETO);
        transaction.setBilling(billing);
        transaction.setShipping(shipping);
        transaction.setItems(items);

        return transaction;
    }
}
