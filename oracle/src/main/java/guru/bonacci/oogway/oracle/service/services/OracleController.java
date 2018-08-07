package guru.bonacci.oogway.oracle.service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "oracle", description = "Know thyself")
public class OracleController {

	@ApiOperation(value = "What's the version again?")
	@GetMapping("/version")
	public String version(@Value("${build.version}") String buildVersion) {
		return buildVersion;
	}	
}
