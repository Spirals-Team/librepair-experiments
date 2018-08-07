package com.hedvig.productPricing.service.commands;

import com.hedvig.productPricing.service.aggregates.Address;
import com.hedvig.productPricing.service.aggregates.Product;
import com.hedvig.productPricing.service.serviceIntegration.botService.dto.CalculateQuoteRequest;
import com.hedvig.productPricing.service.web.dto.SafetyIncreaserType;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Value
@RequiredArgsConstructor
public class CreateMemberCommandV2 {

	private static Logger log = LoggerFactory.getLogger(CreateMemberCommandV2.class);

    private String memberId;
    private String ssn;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public CreateMemberCommandV2(CalculateQuoteRequest user) {
        log.info("CreateProductCommandV2");
        
        this.memberId = user.getMemberId();
        this.ssn = user.getSsn();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthDate = user.getBirthDate();
        
        log.info(this.toString());
    }
}
