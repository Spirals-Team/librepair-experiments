package br.net.itech.factories.dtos;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.net.itech.dtos.ProvinceDto;
import br.net.itech.entities.Province;

@Component
public class ProvinceDtoFactory {
	public ProvinceDto getProvinceDto(Province province) {
		ProvinceDto provinceDto = new ProvinceDto();
		if (province.getId() != null)
			provinceDto.setId(String.valueOf(province.getId()));
		provinceDto.setName(province.getName());
		if (province.getUf() != null)
			provinceDto.setUf(Optional.ofNullable(province.getUf()));
		return provinceDto;
	}
}