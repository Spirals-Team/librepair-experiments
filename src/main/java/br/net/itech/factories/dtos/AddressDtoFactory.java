package br.net.itech.factories.dtos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.itech.dtos.AddressDto;
import br.net.itech.entities.Address;

@Component
public class AddressDtoFactory {
	@Autowired
	CityDtoFactory cityDtoFactory;

	public AddressDto getAddressDto(Address address) {
		AddressDto addressDto = new AddressDto();
		if (address.getId() != null)
			addressDto.setId(Integer.toString(address.getId()));
		addressDto.setStreet(address.getStreet());
		addressDto.setDistrict(address.getDistrict());
		addressDto.setNumber(address.getNumber());
		if (address.getComplement() != null)
			addressDto.setComplement(Optional.ofNullable(address.getComplement()));
		if (address.getCity() != null)
			addressDto.setCity(cityDtoFactory.getCityDto(address.getCity()));
		return addressDto;
	}
}