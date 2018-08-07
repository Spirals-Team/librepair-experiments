package guru.bonacci.intercapere.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import guru.bonacci.intercapere.InterCapereService;
import guru.bonacci.intercapere.SurroundService;

@Configuration
@ConditionalOnClass(InterCapereService.class)
@EnableConfigurationProperties(InterCapereProperties.class)
public class InterCapereAutoConfiguration {

	private final InterCapereProperties properties;

	public InterCapereAutoConfiguration(InterCapereProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "inter.capere", value = "prefix")
	public InterCapereService surroundService() {
		return new SurroundService(this.properties.getPrefix(),
				this.properties.getSuffix());
	}

}
