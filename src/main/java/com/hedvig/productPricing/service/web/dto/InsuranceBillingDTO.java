package com.hedvig.productPricing.service.web.dto;

import com.hedvig.productPricing.service.service.InsuranceBillingInfo;
import java.util.Map.Entry;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import lombok.Value;
import org.javamoney.moneta.Money;

@Value
public class InsuranceBillingDTO {
  String memberId;
  MonetaryAmount subscription;

  public InsuranceBillingDTO(Entry<String, Long> entry) {
    this.memberId = entry.getKey();
    //TODO: pass currency code in da database in order to support multiple countries
    this.subscription = Money.of(entry.getValue(), Monetary.getCurrency("SEK"));
  }

    public InsuranceBillingDTO(InsuranceBillingInfo billingInfo) {
        this.memberId = billingInfo.getMemberId();
        //TODO: pass currency code in da database in order to support multiple countries
        this.subscription = Money.of(billingInfo.getPrice(), Monetary.getCurrency("SEK"));
    }
}
