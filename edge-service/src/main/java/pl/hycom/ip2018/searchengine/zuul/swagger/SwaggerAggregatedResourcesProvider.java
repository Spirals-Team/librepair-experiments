package pl.hycom.ip2018.searchengine.zuul.swagger;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@EnableAutoConfiguration
public class SwaggerAggregatedResourcesProvider implements SwaggerResourcesProvider {

    public List<SwaggerResource> get() {
        List resources = new ArrayList<SwaggerResource>();
        resources.add(buildSwaggerResource("hello-service", "/api/hello/v2/api-docs", "2.0"));
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