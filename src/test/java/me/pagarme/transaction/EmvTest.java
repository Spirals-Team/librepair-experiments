package me.pagarme.transaction;

import com.google.gson.JsonObject;
import me.pagar.converter.JSonConverter;
import me.pagar.model.Transaction;
import me.pagar.util.JSONUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by henriquekano on 9/1/17.
 */
public class EmvTest {

    private String receivedDebitTransactionMock = "{\"payment_method\": \"debit_card\"}";
    private String receivedEmvCapturedTransactionMock = "{\"capture_method\": \"emv\"}";

    private String emvReceivableParameters = "{\"card_emv_response\": \"emv_response\"}";

    @Test
    public void testReceivedDebitPaymentMethod() {
        Transaction debitTransaction = deserialize(receivedDebitTransactionMock);
        Assert.assertEquals(Transaction.PaymentMethod.DEBIT_CARD, debitTransaction.getPaymentMethod());
    }

    @Test
    public void testReceivedEmvCaptureMethod() {
        Transaction debitTransaction = deserialize(receivedEmvCapturedTransactionMock);
        Assert.assertEquals(Transaction.CaptureMethod.EMV, debitTransaction.getCaptureMethod());
    }

    @Test
    public void testOtherSendibleParameters(){
        Transaction transaction = new Transaction();
        transaction.setCardEmvData("emv_data");
        transaction.setCardEmvData("emv_data");
        transaction.setCardTrack1("track_1");
        transaction.setCardTrack2("track_2");
        transaction.setCardTrack3("track_3");
        transaction.setCardPinKek("pin_kek");
        transaction.setCardPin("pin");

        Map<String, Object> serializedTransaction = serialize(transaction);
        Assert.assertEquals("emv_data", serializedTransaction.get("card_emv_data"));
        Assert.assertEquals("track_1", serializedTransaction.get("card_track_1"));
        Assert.assertEquals("track_2", serializedTransaction.get("card_track_2"));
        Assert.assertEquals("track_3", serializedTransaction.get("card_track_3"));
        Assert.assertEquals("pin_kek", serializedTransaction.get("card_pin_kek"));
        Assert.assertEquals("pin", serializedTransaction.get("card_pin"));
    }

    @Test
    public void testSentPinModeOnline() {
        Transaction transaction = new Transaction();
        transaction.setCardPinMode("online");
        Map<String, Object> serializedObject = serialize(transaction);
        Assert.assertEquals("online", serializedObject.get("card_pin_mode"));
    }

    @Test
    public void testSentPinModeOffline() {
        Transaction transaction = new Transaction();
        transaction.setCardPinMode("offline");
        Map<String, Object> serializedObject = serialize(transaction);
        Assert.assertEquals("offline", serializedObject.get("card_pin_mode"));
    }

    @Test
    public void testRecivableParameters(){
        Transaction transaction = deserialize(emvReceivableParameters);

        Assert.assertEquals("emv_response", transaction.getCardEmvResponse());
    }

    private Transaction deserialize(String json){
        JsonObject jsonObject = JSonConverter.gson.fromJson(json, JsonObject.class);
        Transaction deserializedTransaction = JSONUtils.getAsObject(jsonObject, Transaction.class);
        return deserializedTransaction;
    }

    private HashMap<String, Object> serialize(Transaction transaction){
        Map<String, Object> serializedObject = JSONUtils.objectToMap(transaction);
        return new HashMap<String, Object>(serializedObject);
    }

}
