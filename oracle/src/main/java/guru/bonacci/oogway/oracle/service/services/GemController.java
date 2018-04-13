package guru.bonacci.oogway.oracle.service.services;

import static guru.bonacci.oogway.utils.MyFileUtils.readToList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
public class GemController implements InitializingBean {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemRepository repo;

	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new GemValidator());
	}

	@Autowired
	Environment env;
	
	@Override
	public void afterPropertiesSet() {
		// creative exclusion, is it not?
		if (env.acceptsProfiles("!unit-test")) {
			try {
				Gem[] friedrichsBest = readToList("nietzsche.txt").stream()
																	.map(quote -> new Gem(quote, "Friedrich Nietzsche"))
																	.toArray(Gem[]::new);
				repo.saveTheNewOnly(friedrichsBest);
			} catch (IOException e) {
				logger.error("Nietzsche!!", e);
			}
		}	
	}	

	
	@ApiOperation(value = "Search for a gem", response = GemCarrier.class)
	@PreAuthorize("#oauth2.hasScope('resource-server-read')")
	@RequestMapping(method = GET)
	public Optional<GemCarrier> search(@RequestParam("q") String q, 
							 		   @RequestParam(value="by", required = false) Optional<String> author) {
		logger.info("Receiving request for a wise answer on: '" + q + "'");
		
		Optional<Gem> gem = author.map(a -> repo.consultTheOracle(q, a))
								  .orElse(repo.consultTheOracle(q));
		return gem.map(GemMapper.MAPPER::fromGem);
	}	

	@ApiOperation(value = "Pick a random gem", response = GemCarrier.class)
	@PreAuthorize("#oauth2.hasScope('resource-server-read')")
	@RequestMapping(path = "/random", method = GET)
	public Optional<GemCarrier> random() {
		logger.info("Please find me a random gem");
		
		Optional<Gem> gem = repo.findRandom(); 
		gem.ifPresent(g -> logger.info("Random gem found: " + g.getSaying()));
		return gem.map(GemMapper.MAPPER::fromGem);
	}	

	@ApiOperation(value = "Add a gem")
	@RequestMapping(path = "/backdoor", method = POST)
	public void index(@Valid @RequestBody GemCarrier carrier) {
		logger.info("Receiving secret request to index: '" + carrier + "'");
		
		repo.saveTheNewOnly(GemMapper.MAPPER.toGem(carrier));
	}
}
