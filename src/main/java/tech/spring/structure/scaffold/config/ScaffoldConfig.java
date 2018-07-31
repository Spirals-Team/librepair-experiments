package tech.spring.structure.scaffold.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "structure.model")
public class ScaffoldConfig {

    private List<String> packages;

    public ScaffoldConfig() {

    }

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

}
