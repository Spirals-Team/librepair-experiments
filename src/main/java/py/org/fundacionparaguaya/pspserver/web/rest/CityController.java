package py.org.fundacionparaguaya.pspserver.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import py.org.fundacionparaguaya.pspserver.system.dtos.CityDTO;
import py.org.fundacionparaguaya.pspserver.system.services.CityService;

@RestController
@RequestMapping(value = "/api/v1/cities")
public class CityController {

	private static final Logger LOG = LoggerFactory.getLogger(CityController.class);
	
	private CityService cityService;
	
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@PostMapping()
	public ResponseEntity<CityDTO> addCity(@Valid @RequestBody CityDTO cityDTO) throws URISyntaxException {
		CityDTO result = cityService.addCity(cityDTO);
		return ResponseEntity.created(new URI("/api/v1/cities/" + result.getId()))
				.body(result);
	}
	
	
	@PutMapping("/{cityId}")
	public ResponseEntity<CityDTO> updateCity(@PathVariable("cityId") Long cityId, @RequestBody CityDTO cityDTO) {
		CityDTO result = cityService.updateCity(cityId, cityDTO);
		return ResponseEntity.ok(result);
	}

	
	@GetMapping("/{cityId}")
	public ResponseEntity<CityDTO> getCityById(@PathVariable("cityId") Long cityId) {
		CityDTO dto = cityService.getCityById(cityId);
		return ResponseEntity.ok(dto);
	}
	

	@GetMapping()
	public ResponseEntity<List<CityDTO>> getAllCities() {
		List<CityDTO> cities = cityService.getAllCities();
		return ResponseEntity.ok(cities);
	}
	
	
	@DeleteMapping("/{cityId}")
	public ResponseEntity<Void> deleteCity(@PathVariable("cityId") Long cityId) {
		LOG.debug("REST request to delete City: {}", cityId);
		cityService.deleteCity(cityId);
		return ResponseEntity.ok().build();
	}

}