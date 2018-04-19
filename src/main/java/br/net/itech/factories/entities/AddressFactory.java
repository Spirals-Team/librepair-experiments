package br.net.itech.factories.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.itech.dtos.AddressDto;
import br.net.itech.entities.Address;

@Component
public class AddressFactory {
	@Autowired
	CityFactory cityFactory;

	public Address getAddress(AddressDto addressDto) {
		Address address = new Address();
		if (addressDto.getId() != null)
			address.setId(Integer.parseInt(addressDto.getId()));
		address.setStreet(addressDto.getStreet());
		address.setDistrict(addressDto.getDistrict());
		address.setNumber(addressDto.getNumber());
		if (addressDto.getComplement().isPresent())
			address.setComplement(addressDto.getComplement().get());
		address.setCity(cityFactory.getCity(addressDto.getCity()));
		return address;
	}
}