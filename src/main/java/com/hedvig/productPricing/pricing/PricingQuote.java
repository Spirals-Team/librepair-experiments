package com.hedvig.productPricing.pricing;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.hedvig.productPricing.pricing.PricingResult.ResultTypes;

@Entity
public class PricingQuote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="query")
	private PricingQuery pricingQuery;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="result")
	private Set<PricingResult> pricingResults;
	
    public String userId;
    public LocalDate registrationDate = LocalDate.now();
    
    public PricingQuote(){
    	this.pricingResults = new HashSet<PricingResult>();
    }
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PricingQuery getPricingQuery() {
		return pricingQuery;
	}
	public void setPricingQuery(PricingQuery pricingQuery) {
		this.pricingQuery = pricingQuery;
	}
	public PricingResult getPricingResult(ResultTypes type) {
		for(PricingResult pr : pricingResults){
			if(pr.type.equals(type))return pr;
		}
		return null;
	}
	
	public void addPricingResult(PricingResult pricingResult) {
		this.pricingResults.add(pricingResult);
	}
	
}
