package net.posesor.payments;

import lombok.val;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to provide reusable methods to create ready to use models.
 */
final class Given {

    // protected ctor - no need to create instance of helper class.
    private Given() {
    }

    /**
     * Creates the fully constructed payment for testing purposes, acceptable by server-side.
     * <p>
     * The payment has:
     * 1 entry
     * Random subjectName
     */
    static PaymentDto createFullPaymentDto() {
        val entry = new PaymentDto();
        entry.setSubjectName("Subject name " + randomString());
        entry.setBankAccountName("Bank account name " + randomString());
        entry.setCustomerName("settlementaccounts name " + randomString());
        entry.setPaymentDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        entry.setDescription("some description " + randomString());

        entry.setEntries(new PaymentDto.PaymentEntry[]{
                new PaymentDto.PaymentEntry(BigDecimal.valueOf(1), "entry1 " + randomString())
                });
        return entry;
    }

    static String randomString() {
        val random = new SecureRandom();
        return new BigInteger(60, random).toString(32);
    }

    public static PaymentDbModel createSimplePaymentDbModel() {
        val entry = new PaymentDbModel();
        entry.setSubjectName("Subject name " + randomString());
        entry.setPaymentId(randomString());
        entry.setSubjectId(randomString());
        entry.setCustomerName(null);
        entry.setPaymentDate(LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)));
        entry.setDescription("SIMPLE MODEL" );

        List <PaymentDbModel.PaymentEntry> list = new ArrayList<>();
        PaymentDbModel.PaymentEntry pe = new PaymentDbModel.PaymentEntry(){{
                    setPaymentTitle("woda");
                    setAmount(BigDecimal.TEN);
                }};
        list.add(pe);

        entry.setEntries(list);

        return entry;
    }

    public static PaymentDbModel createFullPaymentDbModel() {
        val entry = new PaymentDbModel();
        entry.setSubjectName("Subject name " + randomString());
        entry.setPaymentId("PaymentId "+randomString());
        entry.setSubjectId(randomString());
        entry.setCustomerName("Customer Name "+randomString());
        entry.setPaymentDate(LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)));
        entry.setDescription("FULL MODEL" );

        List <PaymentDbModel.PaymentEntry> list = new ArrayList<>();
        PaymentDbModel.PaymentEntry pe = new PaymentDbModel.PaymentEntry(){{
            setPaymentTitle("Czynsz");
            setAmount(BigDecimal.TEN);
        }};

        list.add(pe);
        entry.setEntries(list);

        return entry;
    }
}
