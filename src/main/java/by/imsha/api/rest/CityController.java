package by.imsha.api.rest;

import by.imsha.api.rest.AbstractRestHandler;
import by.imsha.domain.City;
import by.imsha.domain.dto.CityInfo;
import by.imsha.domain.dto.UpdateEntityInfo;
import by.imsha.domain.dto.mapper.CityMapper;
import by.imsha.exception.DataFormatException;
import by.imsha.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@Api(value = "cities", description = "ImshaBy API")
@RestController
@RequestMapping(value = "/api/cities")
public class CityController extends AbstractRestHandler {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a city resource", notes = "Returns the URL of the new resource in the Location header.")
    public City createCity( @Valid @RequestBody CityInfo city,
                                 HttpServletRequest request, HttpServletResponse response) {
        City createdCity = this.cityService.createCity(new City(city.getName()));
        return createdCity;
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all cities.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    Page<City> getAllCity(@ApiParam(value = "The page number (zero-based)", required = true)
                                      @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      @ApiParam(value = "Tha page size", required = true)
                                      @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                      HttpServletRequest request, HttpServletResponse response) {
        return this.cityService.getAllCities(page, size);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single city.", notes = "You have to provide a valid city ID.")
    public
    @ResponseBody
    City getCity(@ApiParam(value = "The ID of the city.", required = true)
                             @PathVariable("id") String id,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        City city = this.cityService.retrieveCity(id);
        checkResourceFound(city);
        //todo: http://goo.gl/6iNAkz
        return city;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update a cityDTO resource.", notes = "You have to provide a valid cityDTO ID in the URL and in the payload. The ID attribute can not be updated.")
    public UpdateEntityInfo updateCity(@ApiParam(value = "The ID of the existing cityDTO resource.", required = true)
                                 @PathVariable("id") String id, @Valid @RequestBody CityInfo cityDTO,
                                 HttpServletRequest request, HttpServletResponse response) {
        City resource = this.cityService.retrieveCity(id);
        checkResourceFound(resource);
        CityMapper.MAPPER.updateCityFromDTO(cityDTO, resource);
        City updatedSity = this.cityService.updateCity(resource);
      return new UpdateEntityInfo(updatedSity.getId(), UpdateEntityInfo.STATUS.UPDATED);
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete a city resource.", notes = "You have to provide a valid city ID in the URL. Once deleted the resource can not be recovered.")
    public UpdateEntityInfo deleteCity(@ApiParam(value = "The ID of the existing city resource.", required = true)
                                 @PathVariable("id") String id, HttpServletRequest request,
                                       HttpServletResponse response) {
        checkResourceFound(this.cityService.retrieveCity(id));
        this.cityService.removeCity(id);
        return new UpdateEntityInfo(id, UpdateEntityInfo.STATUS.DELETED);
    }
}
