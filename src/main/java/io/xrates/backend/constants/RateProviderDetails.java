package io.xrates.backend.constants;

/*
 * TODO: correct the Rate Provider's id format
 * to accommodate more countries to this system. 
 */
public enum RateProviderDetails {
	DBS(1, "DBS"),
	OCBC(2, "OCBC"),
	REMIT(3, "REMIT GURU");
	
	private final long value;
	private final String providerName;
	
	private RateProviderDetails(int value, String providerName) {
		this.value = value;
		this.providerName = providerName;
	}
	
	public long getValue() {
		return value;
	}
	
	public String getProviderName() {
		return providerName;
	}
}
