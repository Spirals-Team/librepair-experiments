package net.posesor.payments;

import lombok.val;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class Mapper {
    private Mapper() {
    }

    public static PaymentDbModel map(PaymentDto entry) {
        val result = new PaymentDbModel();
        result.setPaymentDate(LocalDate.parse(entry.getPaymentDate(), DateTimeFormatter.ISO_LOCAL_DATE));
        result.setPaymentId(entry.getPaymentId());
        result.setAccountName(entry.getBankAccountName());
        result.setCustomerName(entry.getCustomerName());
        result.setSubjectName(entry.getSubjectName());
        result.setDescription(entry.getDescription());
        result.setEntries(
                Arrays.stream(entry.getEntries())
                        .map(it -> new PaymentDbModel.PaymentEntry(it.getPaymentTitle(), it.getAmount()))
                        .collect(Collectors.toList())
        );
        return result;
    }

    public static PaymentDto map(PaymentDbModel entry) {
        val result = new PaymentDto();
        result.setBankAccountName(entry.getAccountName());
        result.setCustomerName(entry.getCustomerName());
        result.setSubjectName(entry.getSubjectName());
        Optional.of(entry.getPaymentDate())
                .map(it -> it.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .ifPresent(result::setPaymentDate);
        result.setEntries(entry.getEntries().stream().map(it -> new PaymentDto.PaymentEntry(it.getAmount(), it.getPaymentTitle())).toArray(PaymentDto.PaymentEntry[]::new));
        result.setDescription(entry.getDescription());
        return result;
    }

    public static Stream<PaymentDto> map(Iterable<PaymentDbModel> userPayments) {
        return StreamSupport
                .stream(userPayments.spliterator(), false)
                .map(Mapper::map);
    }
}
