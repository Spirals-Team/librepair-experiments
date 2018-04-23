package com.epam.brest.course.dao;

import com.epam.brest.course.dto.TruckFullDetailDto;
import com.epam.brest.course.dto.TruckWithAvgDto;
import com.epam.brest.course.model.Truck;
import java.util.Collection;


/**
 * the interface of Truck dao.
 */
public interface TruckDao {
    /**
     *
     * @return collection.
     */
    Collection<Truck> getAllTrucks();

    /**
     *
     * @param truck to add.
     * @return truck.
     */
    Truck addTruck(Truck truck);

    /**
     *
     * @param id to delete.
     */
    void deleteTruckById(Integer id);

    /**
     *
     * @param truck to update.
     */
    void updateTruck(Truck truck);

    /**
     * @param id .
     * @return Truck.
     */
     TruckFullDetailDto getTruckFullDetailById(Integer id);

    /**
     *
     * @param id .
     * @return truck with avg per month.
     */
    TruckWithAvgDto getTruckById(final Integer id);

}
