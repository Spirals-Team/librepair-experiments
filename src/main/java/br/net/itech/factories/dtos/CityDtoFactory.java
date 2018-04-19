package br.net.itech.factories.dtos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.itech.dtos.CityDto;
import br.net.itech.entities.City;

@Component
public class CityDtoFactory {
	@Autowired
	ProvinceDtoFactory provinceDtoFactory;

	public CityDto getCityDto(City city) {
		CityDto cityDto = new CityDto();
		if (city.getId() != null)
			cityDto.setId(Integer.toString(city.getId()));
		cityDto.setName(city.getName());
		if (city.getProvince() != null)
			cityDto.setProvince(provinceDtoFactory.getProvinceDto(city.getProvince()));
		return cityDto;
	}
}