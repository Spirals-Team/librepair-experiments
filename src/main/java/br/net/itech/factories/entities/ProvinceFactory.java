package br.net.itech.factories.entities;

import org.springframework.stereotype.Component;

import br.net.itech.dtos.ProvinceDto;
import br.net.itech.entities.Province;

@Component
public class ProvinceFactory {
	public Province getProvince(ProvinceDto provinceDto) {
		Province province = new Province();
		if (provinceDto.getId() != null)
			province.setId(Integer.parseInt(provinceDto.getId()));
		province.setName(provinceDto.getName());
		if (provinceDto.getUf().isPresent())
			province.setUf(provinceDto.getUf().get());
		return province;
	}
}