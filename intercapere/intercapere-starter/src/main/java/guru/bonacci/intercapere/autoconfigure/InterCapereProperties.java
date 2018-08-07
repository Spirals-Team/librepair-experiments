package guru.bonacci.intercapere.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("inter.capere")
public class InterCapereProperties {

	@Getter @Setter private String prefix;

	@Getter @Setter private String suffix;
}
