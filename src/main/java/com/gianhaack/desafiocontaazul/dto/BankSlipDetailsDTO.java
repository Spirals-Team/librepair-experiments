package com.gianhaack.desafiocontaazul.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gianhaack.desafiocontaazul.model.BankSlip;
import com.gianhaack.desafiocontaazul.model.BankSlipStatus;
import lombok.Getter;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Getter
public class BankSlipDetailsDTO {

    @JsonProperty("id")
    private final UUID id;

    @JsonProperty("due_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final Date dueDate;

    @JsonProperty("payment_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final Date paymentDate;

    @JsonProperty("total_in_cents")
    private final Long totalInCents;

    @JsonProperty("customer")
    private final String customer;

    @JsonProperty("fine")
    private double fine;

    @JsonProperty("status")
    private final BankSlipStatus status;

    public BankSlipDetailsDTO(BankSlip bankSlip) {
        this.id = bankSlip.getId();
        this.dueDate = bankSlip.getDueDate();
        this.paymentDate = bankSlip.getPaymentDate();
        this.totalInCents = bankSlip.getTotalInCents();
        this.customer = bankSlip.getCustomer();
        this.fine = 0D;
        this.status = bankSlip.getStatus();
    }

    public double getFine() {
        Calendar calendar = Calendar.getInstance();
        long today = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(this.dueDate);
        long dueDate = calendar.get(Calendar.DAY_OF_YEAR);

        long days = today - dueDate;

        if (days > 0 && days <= 10)
            this.fine = this.totalInCents * (0.5/100);
        else if (days > 10)
            this.fine = this.totalInCents * (1/100);

        return this.fine;
    }

}
