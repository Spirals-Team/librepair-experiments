package py.org.fundacionparaguaya.pspserver.system.services;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.system.dtos.CityDTO;



public interface CityService {

	CityDTO updateCity(Long cityId, CityDTO cityDTO);

	CityDTO addCity(CityDTO cityDTO);
	
	CityDTO getCityById(Long cityId);
	
	List<CityDTO> getAllCities();
	
	void deleteCity(Long cityId);
	
	
	
}
