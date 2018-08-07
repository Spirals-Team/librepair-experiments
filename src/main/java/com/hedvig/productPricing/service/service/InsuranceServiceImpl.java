package com.hedvig.productPricing.service.service;

import com.hedvig.productPricing.service.aggregates.ProductStates;
import com.hedvig.productPricing.service.query.ProductEntity;
import com.hedvig.productPricing.service.query.ProductRepository;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class InsuranceServiceImpl implements InsuranceService {

  private ProductRepository productRepository;

  public InsuranceServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Optional<ProductEntity> GetCurrentInsurance(String memberId) {

    Supplier<Stream<ProductEntity>> signedProducts =
        () ->
            productRepository
                .findByMemberId(memberId)
                .stream()
                .filter(x -> x.state == ProductStates.SIGNED);

    if (signedProducts.get().count() < 1) {
      return productRepository.findByMemberId(memberId).stream().reduce((first, second) -> second);
    }

    if (signedProducts.get().count() > 1) {
      if (signedProducts.get().anyMatch(x -> x.activeTo == null)) {
        return signedProducts.get().filter(x -> x.activeTo == null).findFirst();
      } else {
        return signedProducts.get().max(Comparator.comparing(x -> x.activeTo));
      }
    }
    return signedProducts.get().findFirst();
  }
}
