package com.hedvig.productPricing.service.query;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class ContractEntity {

    public enum ContractType {
        InsuranceMandate
    }

    @Id
    public String memberId;


    public byte[] contract;
    public ContractType type;
    // Should includes some data about whether the contract is signed or not

    public ContractEntity() {}
}
