package com.epam.brest.course.rest;

import com.epam.brest.course.dto.TruckWithAvgDto;
import com.epam.brest.course.model.Truck;
import com.epam.brest.course.service.TruckService;
import com.epam.brest.course.utility.data.TruckDto;
import com.epam.brest.course.utility.data.TruckLiteDto;
import com.epam.brest.course.utility.dozer.MappingService;
import com.epam.brest.course.utility.validator.TruckValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


/**
 * truck controller.
 */
@CrossOrigin
@RestController
public class TruckRestController {

    /**
     * Log class for debug.
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * time formatter.
     */
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Autowired mapping service bean dozer.
     */
    @Autowired
    private MappingService mappingService;

    /**
     * @param binder .
     */
    @InitBinder
    protected final void initBinder(final WebDataBinder binder) {
        binder.setValidator(new TruckValidator());
        formatter.setLenient(false);
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(formatter, true));
    }

    /**
     * truck service.
     */
    @Autowired
    private TruckService truckService;

    /**
     * @param truckId param .
     * @return new truck through dto.
     */
    @GetMapping(value = "/trucks/{truckId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public final TruckDto getTruckId(@PathVariable(value = "truckId")
                                         final Integer truckId) {

        LOGGER.debug("test: truckId({})", truckId);
        TruckWithAvgDto truck = truckService.getTruckById(truckId);
        return mappingService.map(truck, TruckDto.class);
    }


    /**
     * @return collection of just order list through dto.
     */
    @GetMapping(value = "/trucks")
    public final Collection<TruckLiteDto> getAllTrucks() {

        LOGGER.debug("test: getAllTrucks()");
        Collection<Truck> trucks = truckService.getAllTrucks();
        return mappingService.map(trucks, TruckLiteDto.class);
    }


    /**
     * @param truckDto take dto and convert to model and save.
     *                 then convert to dto.
     * @return posted truck.
     */
    @PostMapping(value = "/trucks")
    @ResponseStatus(HttpStatus.CREATED)
    public final TruckDto addTruck(@Valid
                                       @RequestBody final TruckDto truckDto) {
        LOGGER.debug("addTruck({})", truckDto);

        Truck mappedTruck = mappingService.map(truckDto, Truck.class);
        Truck persisted = truckService.addTruck(mappedTruck);
        return mappingService.map(persisted, TruckDto.class);
    }

    /**
     * @param truckDto take dto convert to model and update.
     */
    @PutMapping(value = "/trucks")
    @ResponseStatus(HttpStatus.OK)
    public final void update(@Valid @RequestBody final TruckDto truckDto) {
        LOGGER.debug("update({})", truckDto);

        Truck mappedTruck = mappingService.map(truckDto, Truck.class);
        truckService.updateTruck(mappedTruck);
    }

    /**
     * @param truckId param.
     */
    @DeleteMapping(value = "/trucks/{truckId}")
    @ResponseStatus(HttpStatus.FOUND)
    public final void deleteTruck(@PathVariable(value = "truckId")
                                                      final Integer truckId) {

        LOGGER.debug("deleteTruck({})", truckId);
        truckService.deleteTruckById(truckId);
    }

}
