package py.org.fundacionparaguaya.pspserver.system.services.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.system.dtos.CityDTO;
import py.org.fundacionparaguaya.pspserver.system.entities.CityEntity;
import py.org.fundacionparaguaya.pspserver.system.mapper.CityMapper;
import py.org.fundacionparaguaya.pspserver.system.repositories.CityRepository;
import py.org.fundacionparaguaya.pspserver.system.services.CityService;



@Service
public class CityServiceImpl implements CityService {

	private Logger LOG = LoggerFactory.getLogger(CityServiceImpl.class);

	private CityRepository cityRepository;
	
	private final CityMapper cityMapper;
	
	public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
		this.cityRepository = cityRepository;
		this.cityMapper = cityMapper;
	}

	@Override
	public CityDTO updateCity(Long cityId, CityDTO cityDTO) {
		checkArgument(cityId > 0, "Argument was %s but expected nonnegative", cityId);

		return Optional.ofNullable(cityRepository.findOne(cityId))
                .map(city -> {
                    BeanUtils.copyProperties(cityDTO, city);
                    LOG.debug("Changed Information for City: {}", city);
                    return city;
                })
                .map(cityMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("City does not exist"));
	}

	@Override
	public CityDTO addCity(CityDTO cityDTO) {
		CityEntity city = new CityEntity();
		BeanUtils.copyProperties(cityDTO, city);
		CityEntity newCity = cityRepository.save(city);
		return cityMapper.entityToDto(newCity);
	}

	@Override
	public CityDTO getCityById(Long cityId) {
		checkArgument(cityId > 0, "Argument was %s but expected nonnegative", cityId);

        return Optional.ofNullable(cityRepository.findOne(cityId))
                .map(cityMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("City does not exist"));
	}

	@Override
	public List<CityDTO> getAllCities() {
		List<CityEntity> cities = cityRepository.findAll();
		return cityMapper.entityListToDtoList(cities);
	}

	@Override
	public void deleteCity(Long cityId) {
		checkArgument(cityId > 0, "Argument was %s but expected nonnegative", cityId);

        Optional.ofNullable(cityRepository.findOne(cityId))
                .ifPresent(city -> {
                	cityRepository.delete(city);
                    LOG.debug("Deleted City: {}", city);
                });
		
	}

	

}
