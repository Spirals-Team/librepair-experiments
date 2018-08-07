package com.hedvig.productPricing.service.sagas;

import com.hedvig.productPricing.service.events.ActivationDateUpdatedEvent;
import com.hedvig.productPricing.service.query.ProductEntity;
import com.hedvig.productPricing.service.query.ProductRepository;
import com.hedvig.productPricing.service.service.InsuranceTransferService;
import java.util.Objects;
import lombok.val;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class ActivationDateUpdatedSaga {

  @Autowired transient ProductRepository productRepository;

  @Autowired transient InsuranceTransferService insuranceTransferService;

  @StartSaga
  @SagaEventHandler(associationProperty = "memberId")
  @EndSaga
  public void start(ActivationDateUpdatedEvent event) {
    val productEntity =
        productRepository
            .findByMemberId(event.getMemberId())
            .stream()
            .filter(x -> Objects.equals(x.id, event.getProductId()))
            .findFirst();

    if (productEntity.isPresent()) {
      ProductEntity ue = productEntity.get();
      if (ue.insuredAtOtherCompany) {
        insuranceTransferService.activationDateUpdated(
            event.getMemberId(), ue.member.currentInsurer, event.getActivationDate());
      }
    }
  }
}
