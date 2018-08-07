package guru.bonacci.oogway.oracle.service.services;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.oracle.service.beanmapping.GemMapper;
import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/gems")
@Api(value = "gemming", description = "Made for Gem Mining")
public class GemController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemRepository repo;

	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new GemValidator());
	}

	@ApiOperation(value = "Search for a gem", response = GemCarrier.class)
	@PreAuthorize("#oauth2.hasScope('resource-server-read')")
	@GetMapping
	public Optional<GemCarrier> search(@RequestParam("q") String q, 
							 		   @RequestParam(value="by", required = false) Optional<String> author) {
		logger.error("Receiving request for a wise answer on: '" + q + "'");//error: to show load balancing
		
		Optional<Gem> gem = author.map(a -> repo.consultTheOracle(q, a))
								  .orElse(repo.consultTheOracle(q));
		return gem.map(GemMapper.MAPPER::fromGem);
	}	

	@ApiOperation(value = "Pick a random gem", response = GemCarrier.class)
	@PreAuthorize("#oauth2.hasScope('resource-server-read')")
	@GetMapping("/random")
	public Optional<GemCarrier> random() {
		logger.info("Please find me a random gem");
		
		Optional<Gem> gem = repo.findRandom(); 
		gem.ifPresent(g -> logger.info("Random gem found: " + g.getSaying()));
		return gem.map(GemMapper.MAPPER::fromGem);
	}	

	@ApiOperation(value = "Add a gem")
	@PostMapping("/backdoor")
	public void index(@Valid @RequestBody GemCarrier carrier) {
		logger.info("Receiving secret request to index: '" + carrier + "'");
		
		repo.saveTheNewOnly(GemMapper.MAPPER.toGem(carrier));
	}
}
