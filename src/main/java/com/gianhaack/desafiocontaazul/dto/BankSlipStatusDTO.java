package com.gianhaack.desafiocontaazul.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gianhaack.desafiocontaazul.model.BankSlip;
import com.gianhaack.desafiocontaazul.model.BankSlipStatus;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
public class BankSlipStatusDTO {

    @JsonProperty("id")
    public UUID id;

    @JsonProperty("status")
    public BankSlipStatus status;

    @JsonProperty("payment_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date paymentDate;

    public BankSlipStatusDTO(BankSlip bankSlip) {
        this.id = bankSlip.getId();
        this.status = bankSlip.getStatus();
        this.paymentDate = bankSlip.getPaymentDate();
    }

    public BankSlipStatusDTO() {
    }

}
