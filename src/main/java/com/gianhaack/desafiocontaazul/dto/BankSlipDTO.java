package com.gianhaack.desafiocontaazul.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gianhaack.desafiocontaazul.model.BankSlip;
import com.gianhaack.desafiocontaazul.model.BankSlipStatus;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Getter
public class BankSlipDTO {

    @JsonProperty("id")
    private UUID id;

    @NotNull(message = "Due date can't be null")
    @JsonProperty("due_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    @JsonProperty("total_in_cents")
    private Long totalInCents;

    @NotNull(message = "Customer name can't be null")
    @NotEmpty(message = "Customer name can't be empty")
    @JsonProperty("customer")
    private String customer;

    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private BankSlipStatus status;

    public BankSlipDTO() {
    }

    public BankSlipDTO(BankSlip bankSlip) {
        this.id = bankSlip.getId();
        this.dueDate = bankSlip.getDueDate();
        this.totalInCents = bankSlip.getTotalInCents();
        this.customer = bankSlip.getCustomer();
        this.status = bankSlip.getStatus();
    }

    public BankSlip convert() {
        BankSlip bankSlip = new BankSlip();
        bankSlip.setDueDate(getDueDate());
        bankSlip.setTotalInCents(getTotalInCents());
        bankSlip.setCustomer(getCustomer());
        bankSlip.setStatus(getStatus());

        return bankSlip;
    }


}
