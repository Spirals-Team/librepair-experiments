package com.hedvig.memberservice.externalApi.productsPricing;

import com.hedvig.memberservice.externalApi.productsPricing.dto.ContractSignedRequest;
import com.hedvig.memberservice.externalApi.productsPricing.dto.InsuranceStatusDTO;
import com.hedvig.memberservice.externalApi.productsPricing.dto.SafetyIncreasersDTO;
import com.hedvig.memberservice.externalApi.productsPricing.dto.SetCancellationDateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "productPricing", url = "${hedvig.productsPricing.url}")
public interface ProductClient {

  @RequestMapping(value = "/_/insurance/contractSigned", method = RequestMethod.POST)
  String contractSinged(@RequestBody ContractSignedRequest req);

  @RequestMapping(value = "/_/insurance/{memberId}/safetyIncreasers", method = RequestMethod.GET)
  ResponseEntity<SafetyIncreasersDTO> getSafetyIncreasers(@PathVariable("memberId") long memberId);

  @RequestMapping(value = "/insurance/{memberId}/insuranceStatus")
  ResponseEntity<InsuranceStatusDTO> getInsuranceStatus(@PathVariable("memberId") long memberId);

  @RequestMapping(value = "/_/insurance/contract/{memberId}")
  ResponseEntity<byte[]> getContract(@PathVariable("memberId") String contractId);

  @RequestMapping(value = "/_/insurance/{memberId}/setCancellationDate")
  ResponseEntity<String> setCancellationDate(
      @PathVariable("memberId") Long memberId, SetCancellationDateRequest body);
}
