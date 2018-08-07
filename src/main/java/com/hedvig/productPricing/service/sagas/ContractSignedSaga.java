package com.hedvig.productPricing.service.sagas;

import com.hedvig.productPricing.service.events.ContractSignedEvent;
import com.hedvig.productPricing.service.query.ProductEntity;
import com.hedvig.productPricing.service.query.ProductRepository;
import com.hedvig.productPricing.service.service.InsuranceTransferService;
import com.hedvig.productPricing.service.web.dto.InsuranceCompaniesSE;
import lombok.val;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;


@Saga
public class ContractSignedSaga {

    @Autowired
    transient ProductRepository productRepository;

    @Autowired
    transient InsuranceTransferService insuranceTransferService;


    @StartSaga
    @SagaEventHandler(associationProperty = "memberId")
    @EndSaga
    public void start(ContractSignedEvent event) {
        val productEntity = productRepository.findById(event.getProductId());

        if(productEntity.isPresent()){
            ProductEntity product = productEntity.get();
            if(product.insuredAtOtherCompany) {
                InsuranceCompaniesSE insurer = InsuranceCompaniesSE.create(product.currentInsurer);
                insuranceTransferService.startTransferProcess(event.getMemberId(), product.insuredAtOtherCompany,insurer);
            }
        }
    }
}
