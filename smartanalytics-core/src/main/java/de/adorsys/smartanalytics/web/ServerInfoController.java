package de.adorsys.smartanalytics.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@SuppressWarnings("unused")
@Api(value = "/", tags = {"endpoint metadata"}, description = "endpoint metadata")
@RequestMapping(path = "/")
public class ServerInfoController {

    @Autowired
    private HttpServletRequest servletRequest;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "endpoint metadata", notes = "Provides meta information on this API Endpoint")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok")})
    public Resource<Object> info() {
        String urlBase = StringUtils.substringBeforeLast(servletRequest.getRequestURL().toString(), servletRequest.getRequestURI());
        return new Resource("REST API", new Link(urlBase + "/swagger-ui.html", "api-docs"));
    }

}
