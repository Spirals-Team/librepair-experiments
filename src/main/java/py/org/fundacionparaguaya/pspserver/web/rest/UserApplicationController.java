package py.org.fundacionparaguaya.pspserver.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.org.fundacionparaguaya.pspserver.network.dtos.UserApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.services.UserApplicationService;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/user-applications")
public class UserApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(UserApplicationController.class);
	
	private UserApplicationService userApplicationService;
	
	public UserApplicationController(UserApplicationService userApplicationService) {
		this.userApplicationService = userApplicationService;
	}
	
	@PostMapping()
	public ResponseEntity<UserApplicationDTO> addApplication(@Valid @RequestBody UserApplicationDTO userApplicationDTO) throws URISyntaxException {
		UserApplicationDTO result = userApplicationService.addUserApplication(userApplicationDTO);
		return ResponseEntity.created(new URI("/api/v1/user-applications/" + result.getId()))
				.body(result);
	}
	
	@PutMapping("/{userApplicationId}")
	public ResponseEntity<UserApplicationDTO> updateUserApplication(@PathVariable("userApplicationId") Long userApplicationId, @RequestBody UserApplicationDTO userApplicationDTO) {
		UserApplicationDTO result = userApplicationService.updateUserApplication(userApplicationId, userApplicationDTO);
		return ResponseEntity.ok(result);
	}

	
	@GetMapping("/{userApplicationId}")
	public ResponseEntity<UserApplicationDTO> getUserApplicationById(@PathVariable("userApplicationId") Long userApplicationId) {
		UserApplicationDTO dto = userApplicationService.getUserApplicationById(userApplicationId);
		return ResponseEntity.ok(dto);
	}
	

	@GetMapping()
	public ResponseEntity<List<UserApplicationDTO>> getAllUserApplications() {
		List<UserApplicationDTO> userApplications = userApplicationService.getAllUserApplications();
		return ResponseEntity.ok(userApplications);
	}
	
	
	@DeleteMapping("/{userApplicationId}")
	public ResponseEntity<Void> deleteUserApplication(@PathVariable("userApplicationId") Long userApplicationId) {
		LOG.debug("REST request to delete UserApplication: {}", userApplicationId);
		userApplicationService.deleteUserApplication(userApplicationId);
		return ResponseEntity.ok().build();
	}

}