package com.epam.brest.course.service;


import com.epam.brest.course.dto.TruckWithAvgDto;
import com.epam.brest.course.model.Truck;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

/**
 * Truck service abstracted methods.
 */
public interface TruckService {

    /**
     *@throws DataAccessException exception.
     * @return collection.
     */
    Collection<Truck> getAllTrucks() throws DataAccessException;

    /**
     *@throws DataAccessException exception.
     * @param truck to add.
     * @return truck.
     */
    Truck addTruck(Truck truck) throws DataAccessException;


    /**
     *@throws DataAccessException exception.
     * @param id to delete.
     */
    void deleteTruckById(Integer id) throws DataAccessException;

    /**
     *@throws DataAccessException exception.
     * @param truck to update.
     */
    void updateTruck(Truck truck) throws DataAccessException;

    /**
     *
     * @param id .
     * @return truck with avg per month.
     */
    TruckWithAvgDto getTruckById(final Integer id);


}
