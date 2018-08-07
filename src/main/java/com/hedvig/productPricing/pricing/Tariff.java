package com.hedvig.productPricing.pricing;

public class Tariff {
	public Tariff(String product, String variable, Double effect, Integer lowValue, Integer highValue) {
		super();
		this.product = product;
		this.variable = variable;
		this.effect = effect;
		this.lowValue = lowValue;
		this.highValue = highValue;
	}
	public String product;
	public String variable;
	public Double effect;
	public Integer lowValue;
	public Integer highValue;
	
	public String toString(){
		return this.product + ":" + 
				this.variable + ":" + 
				this.effect + ":" + 
				this.lowValue + ":" + 
				this.highValue;
				
	}
}
