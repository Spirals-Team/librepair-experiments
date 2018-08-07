package com.hedvig.productPricing.pricing;

public interface PricingEngine {

	PricingResult getPrice(PricingQuote pq);
	Boolean isStartupComplete();
	Boolean isFileDataRead();
}
