package net.posesor.charges;

import lombok.val;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;

public class Given {

    private static int counter = 1;

    public Given() {
    }

    /**
     * Creates a full (in term of the DTO content) model of ChargeDto.
     *
     * Should be accepted by server as new Charge item.
     * @return
     */
    public static ChargeDocumentDto prepareFullChargeDocumentDto() {
        val entry = new ChargeDocumentDto();
        entry.setCustomerName("CustomerName " + randomString());
        entry.setSubjectName("Subject Name " + randomString());
        entry.setPaymentDate("2001-02-03");
        entry.setPaymentTitle("Financial Settlement " + randomString());
        entry.setAmount(BigDecimal.valueOf(100));
        return entry;
    }

    static String randomString() {
        val random = new SecureRandom();
        return new BigInteger(60, random).toString(32);
    }
}
