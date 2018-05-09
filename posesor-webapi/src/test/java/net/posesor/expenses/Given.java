package net.posesor.expenses;

import lombok.val;

import java.math.BigDecimal;
import java.util.UUID;

public class Given {

    private static int counter = 1;

    /**
     * Disable ability to create utility class.
     */
    private Given() {
    }

    public static ExpenseDocumentDto prepareFullExpenseDtoModel() {
        val entity = new ExpenseDocumentDto();
        val random = UUID.randomUUID().toString();
        entity.setSubjectName("Subject Name " + random);
        entity.setCustomerName("Customer name " + random);
        entity.setPaymentTitle("Payment title" + random);
        entity.setPaymentDate("2001-02-03");
        entity.setAmount(BigDecimal.valueOf(99));
        entity.setDescription("Description " + random);
        return entity;
    }
}
