package br.net.itech.factories.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.net.itech.dtos.CityDto;
import br.net.itech.entities.City;

@Component
public class CityFactory {
	@Autowired
	ProvinceFactory provinceFactory;

	public City getCity(CityDto cityDto) {
		City city = new City();
		if (cityDto.getId() != null)
			city.setId(Integer.parseInt(cityDto.getId()));
		city.setName(cityDto.getName());
		city.setProvince(provinceFactory.getProvince(cityDto.getProvince()));
		return city;
	}
}