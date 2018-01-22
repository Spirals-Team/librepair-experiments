package py.org.fundacionparaguaya.pspserver.system.services;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;

public interface CountryService {
	
	CountryDTO getCountryById(Long countryId);
	
	List<CountryDTO> getAllCountries();
	
	
}