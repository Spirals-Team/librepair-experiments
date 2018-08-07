package com.hedvig.productPricing.service.web.dto;

import com.hedvig.productPricing.service.aggregates.Product.ProductTypes;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class InsuranceModificationDTO {

    public UUID idToBeReplaced;

    public String memberId;
    public Boolean isStudent;

    public String street;
    public String city;
    public String zipCode;
    public Integer floor;

    public Float livingSpace;
    public ProductTypes houseType;
    public Integer personsInHouseHold;

    public List<SafetyIncreaserType> safetyIncreasers;
}
