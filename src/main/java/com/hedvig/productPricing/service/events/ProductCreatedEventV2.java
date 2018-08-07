package com.hedvig.productPricing.service.events;

import com.hedvig.productPricing.service.aggregates.Address;
import com.hedvig.productPricing.service.aggregates.Product.ProductTypes;
import com.hedvig.productPricing.service.aggregates.SafetyIncreaser;
import com.hedvig.productPricing.service.web.dto.PerilDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Value;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.serialization.Revision;

@Value
@Revision("1.0")
public class ProductCreatedEventV2 {

    @AggregateIdentifier
    private UUID id;

    private String memberId;

    private BigDecimal currentTotalPrice;

    private Boolean insuredAtOtherCompany;
    private String currentInsurer;
    private Boolean isStudentInsurance;

    private Address address;
    private Float livingSpace;
    private ProductTypes houseType;
    private Integer personsInHouseHold;
    private List<SafetyIncreaser> safetyIncreasers;
    private List<PerilDTO> perils;
}
