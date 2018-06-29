package com.hedvig.productPricing.service.serviceIntegration.memberService;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("member-service")
public interface MemberServiceClient {

}
