package by.imsha.api.rest;

import by.imsha.domain.City;
import by.imsha.domain.Mass;
import by.imsha.domain.dto.MassSchedule;
import by.imsha.domain.dto.UpdateEntityInfo;
import by.imsha.exception.ResourceNotFoundException;
import by.imsha.service.CityService;
import by.imsha.service.MassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Alena Misan
 */

@Api(value = "Mass services", description = "Methods for managing masses")
@RestController
@RequestMapping(value = "/api/mass")
public class MassController extends AbstractRestHandler {

    @Value("${imsha.date.format}")
    private String dateFormat;

    @Autowired
    private MassService massService;

    @Autowired
    private CityService cityService;


    @ApiOperation(value = "Create mass", response = Mass.class)
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Mass> createMass(@Valid @RequestBody Mass mass, HttpServletRequest request, HttpServletResponse response){
        return ResponseEntity.ok().body(massService.createMass(mass));
    }

    @ApiOperation(value = "Get mass details by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of mass detail", response = Mass.class),
            @ApiResponse(code = 404, message = "When the mass with the given id is not found"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    @RequestMapping(value = "/{massId}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Resource<Mass> retrieveMass(@PathVariable("massId") String id,
                                           HttpServletRequest request, HttpServletResponse response){
        Mass mass = massService.getMass(id);
        checkResourceFound(mass);
        Resource<Mass> massResource = new Resource<Mass>(mass);
        massResource.add(linkTo(methodOn(MassController.class).retrieveMass(id,request, response)).withSelfRel());
        return massResource;
    }

    @ApiOperation(value = "Update mass with data provided")
    @RequestMapping(value = "/{massId}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public Resource<Mass> updateMass(@PathVariable("massId") String id,@Valid @RequestBody Mass mass,
                             HttpServletRequest request, HttpServletResponse response) {
        Mass massForUpdate = this.massService.getMass(id);
        checkResourceFound(massForUpdate);
        // TODO check implementation??
        mass.setId(id);
        Mass updatedMass = this.massService.updateMass(mass);
        Resource<Mass> massResource = new Resource<Mass>(updatedMass);
        massResource.add(linkTo(methodOn(MassController.class).updateMass(id,mass,request, response)).withSelfRel());
        return massResource;
    }

    @RequestMapping(value = "/{massId}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public UpdateEntityInfo removeMass(@PathVariable("massId") String id, HttpServletRequest request,
                                       HttpServletResponse response) {
        checkResourceFound(this.massService.getMass(id));
        this.massService.removeMass(id);
        return new UpdateEntityInfo(id, UpdateEntityInfo.STATUS.DELETED);
    }


    @RequestMapping(value = "/week",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Resource<MassSchedule> retrieveMassesByCity(@CookieValue(value = "cityId", required = false) String cityId,@RequestParam(value = "date", required = false) String day, HttpServletRequest request, HttpServletResponse response){
        if(cityId == null){
            if(log.isWarnEnabled()){
                log.warn("Looking for default city..");
            }
            City defaultCity = cityService.defaultCity();
            if(defaultCity == null){
                throw new ResourceNotFoundException(String.format("No default city (name = %s) founded", cityService.getDefaultCityName()));
            }
            cityId = defaultCity.getId();
            if(log.isWarnEnabled()){
                log.warn(String.format("Default city with id = %s is found.", cityId));
            }
        }

        List<Mass> masses = this.massService.getMassByCity(cityId); // TODO filter by date as well

        LocalDate date = null;
        if(day != null){
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                date = LocalDate.parse(day, formatter);
           }catch (DateTimeParseException ex){
                log.warn(String.format("Date format is incorrect. Date - %s,format - %s ", day, dateFormat));
           }
        }
        if(date == null){
            date = LocalDate.now();
        }

        MassSchedule massHolder = new MassSchedule(date);

        massHolder.build(masses).buildSchedule();

        if(log.isDebugEnabled()){
            log.debug(String.format("%s masses found: %s. Scheduler is built.", masses.size(), masses));
        }
        Resource<MassSchedule> massResource = new Resource<MassSchedule>(massHolder);
        // TODO add links
//        massResource.add(linkTo(methodOn(MassController.class).retrieveMassByParish(parishId,request, response)).withSelfRel());
        return massResource;
    }



    @RequestMapping(value = "",
            method = RequestMethod.GET,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Mass> filterMasses(@RequestParam("filter") String filter,
                                       @RequestParam(value="offset", required = false, defaultValue = "0") String page,
                                       @RequestParam(value="limit", required = false, defaultValue = "10") String perPage,
                                       @RequestParam(value="sort", required = false, defaultValue = "+name") String sorting,
                                       HttpServletRequest request, HttpServletResponse response ){
//        massService.search(filter);
        return massService.search(filter, Integer.parseInt(page), Integer.parseInt(perPage), sorting);
    }



}
