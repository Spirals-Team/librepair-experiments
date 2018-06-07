package net.posesor.payments;

import lombok.val;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public final class MapperShould {

    @Test
    public void convertDto2ModelFullData() {
        val given = new PaymentDto();
        given.setBankAccountName("Account name");
        given.setCustomerName("Customer name");
        given.setPaymentDate("2001-02-03");
        given.setSubjectName("SubjectDbModel name");
        given.setDescription("description");
        given.setEntries(new PaymentDto.PaymentEntry[]{
                new PaymentDto.PaymentEntry(BigDecimal.valueOf(1), "ad1")
                });

        val actual = Mapper.map(given);
        assertThat(actual.getAccountName()).isEqualTo("Account name");
        assertThat(actual.getCustomerName()).isEqualTo("Customer name");
        assertThat(actual.getPaymentDate()).isEqualTo(LocalDate.of(2001, 2, 3));
        assertThat(actual.getSubjectName()).isEqualTo("SubjectDbModel name");
        assertThat(actual.getDescription()).isEqualTo("description");
        val entry1 = actual.getEntries().get(0);
        assertThat(entry1.getAmount()).isEqualTo(BigDecimal.valueOf(1));
        assertThat(entry1.getPaymentTitle()).isEqualTo("ad1");
    }

    @Test
    public void shouldConvertModel2DtoFullData() {
        val given = new PaymentDbModel();
        given.setAccountName("Account name");
        given.setCustomerName("Customer name");
        given.setSubjectName("SubjectDbModel name");
        given.setPaymentDate(LocalDate.of(2001, 2, 3));
        given.setDescription("description");
        val entry = new PaymentDbModel.PaymentEntry();
        entry.setAmount(BigDecimal.valueOf(1));
        entry.setPaymentTitle("ad1");
        given.setEntries(Collections.singletonList(entry));

        val actual = Mapper.map(given);
        assertThat(actual.getBankAccountName()).isEqualTo("Account name");
        assertThat(actual.getCustomerName()).isEqualTo("Customer name");
        assertThat(actual.getSubjectName()).isEqualTo("SubjectDbModel name");
        assertThat(actual.getPaymentDate()).isEqualTo("2001-02-03");
        assertThat(actual.getDescription()).isEqualTo("description");
        val entry1 = actual.getEntries()[0];
        assertThat(entry1.getAmount()).isEqualTo(BigDecimal.valueOf(1));
        assertThat(entry1.getPaymentTitle()).isEqualTo("ad1");
    }
}
