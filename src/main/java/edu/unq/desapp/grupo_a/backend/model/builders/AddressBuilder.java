package edu.unq.desapp.grupo_a.backend.model.builders;

import edu.unq.desapp.grupo_a.backend.model.Address;

public class AddressBuilder {
	public static AddressBuilder anAddress() {
		return new AddressBuilder();
	}
	
	public Address build() {
		return new Address();
	}
}
