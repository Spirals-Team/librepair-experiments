package pl.hycom.ip2018.searchengine.zuul.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
@Primary
@EnableAutoConfiguration
public class SwaggerAggregatedResourcesProvider implements SwaggerResourcesProvider {

    @Autowired
    private Environment environment;

    @Autowired
    @Qualifier("routesProperties")
    private Properties routesProperties;

    public List<SwaggerResource> get() {
        List resources = new ArrayList<SwaggerResource>();

        String prefix = environment.getProperty("zuul.prefix");
        for (Object key : routesProperties.keySet()) {
            String keyStr = (String) key;
            String property = "zuul.routes.".concat(keyStr);
            String name = environment.getProperty(property.concat(".serviceId"));
            String location = (prefix + environment.getProperty(property + ".path")).replace("**", "v2/api-docs");
            resources.add(buildSwaggerResource(name, location, "2.0"));
        }
        return resources;
    }

    private SwaggerResource buildSwaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}