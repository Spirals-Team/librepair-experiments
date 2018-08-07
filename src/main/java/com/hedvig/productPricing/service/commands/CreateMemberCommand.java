package com.hedvig.productPricing.service.commands;

import com.hedvig.productPricing.service.aggregates.Address;
import com.hedvig.productPricing.service.aggregates.Product;
import com.hedvig.productPricing.service.serviceIntegration.botService.dto.CalculateQuoteRequest;
import com.hedvig.productPricing.service.web.dto.SafetyIncreaserType;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

@Value
@RequiredArgsConstructor
public class CreateMemberCommand {

	private static Logger log = LoggerFactory.getLogger(CreateMemberCommand.class);

    private String memberId;
    private String ssn;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Address address;
    private Float livingSpace;
    private Product.ProductTypes houseType;
    private String currentInsurer;
    private Integer personsInHouseHold;
    private List<SafetyIncreaserType> safetyIncreasers;

    public CreateMemberCommand(CalculateQuoteRequest user) {
        log.info("CreateProductCommand");
        
        this.memberId = user.getMemberId();
        this.ssn = user.getSsn();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthDate = user.getBirthDate();
        this.address = new Address(user.getAddress().getStreet(), user.getAddress().getCity(), user.getAddress().getZipCode());
        this.livingSpace = user.getLivingSpace();
        this.houseType = user.getHouseType();
        this.currentInsurer = user.getCurrentInsurer();
        this.personsInHouseHold = user.getPersonsInHouseHold();
        this.safetyIncreasers = user.getSafetyIncreasers();
        
        log.info(this.toString());
    }
}
