package com.hedvig.productPricing.service.query;

import com.hedvig.productPricing.service.aggregates.ProductStates;
import com.hedvig.productPricing.service.serviceIntegration.botService.dto.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class UserEntity {

    private static Logger log = LoggerFactory.getLogger(UserEntity.class);

    @Id
    public String id;
    public String firstName;
    public String lastName;
    public LocalDate birthDate;

    public Instant signedOn;

    @Embedded
    public Address address;
    public Float livingSpace;
    public String houseType;
    public String currentInsurer;
    public Integer personsInHouseHold;

    @ElementCollection
    @CollectionTable(name="user_items")
    @MapKeyJoinColumn(name="memberId")
    @Column(name="item")
    public List<String> goodToHaveItems;

    @Enumerated(EnumType.STRING)
    public ProductStates insuranceState;
    public LocalDateTime insuranceActiveFrom;
    public LocalDateTime insuanceActiveTo;

    public UserEntity(String id) {
        this.id = id;
    }

    public UserEntity() {
        // TODO Auto-generated constructor stub
    }
}
