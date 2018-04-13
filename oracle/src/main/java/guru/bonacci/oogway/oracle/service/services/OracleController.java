package guru.bonacci.oogway.oracle.service.services;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "oracle", description = "Know thyself")
public class OracleController {

	@ApiOperation(value = "What's the version again?")
	@RequestMapping(path = "/version", method = GET)
	public String version(@Value("${build.version}") String buildVersion) {
		return buildVersion;
	}	
}
