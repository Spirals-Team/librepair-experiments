package com.gianhaack.desafiocontaazul.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "bankslip")
@Getter
@Setter
public class BankSlip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    @Column(name = "id")
    private UUID id;

    @Temporal(TemporalType.DATE)
    @JsonProperty("due_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @JsonProperty("total_in_cents")
    @Column(name = "total_in_cents", nullable = false)
    private Long totalInCents;

    @JsonProperty("customer")
    @Column(name = "costumer", nullable = false)
    private String customer;

    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    @Column(name = "status", nullable = false)
    private BankSlipStatus status;

    @Temporal(TemporalType.DATE)
    @JsonProperty("payment_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "payment_date", nullable = true)
    private Date paymentDate;

    public BankSlip() {
    }

}
