package com.epam.brest.course.service;

import com.epam.brest.course.dao.TruckDao;
import com.epam.brest.course.dto.TruckWithAvgDto;
import com.epam.brest.course.model.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * Impl Class.
 */
@Service
@Transactional
public class TruckServiceImpl implements TruckService {

    /**
     * logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * dao.
     */
    @Autowired
    private TruckDao truckDao;

    /**
     *  @param truckDao1 injected.
    */
    public final void setTruckDao(final TruckDao truckDao1) {
        this.truckDao = truckDao1;
    }

    /**
     * @return list of trucks.
     *  @throws DataAccessException exception.
     */
    @Override
    public final Collection<Truck> getAllTrucks() throws DataAccessException {
        LOGGER.debug("getAllTrucks()");

        return truckDao.getAllTrucks();
    }

    /**
     * @param truck to add.
     * @return new truck.
     * @throws DataAccessException exception.
     */
    @Override
    public final Truck addTruck(final Truck truck)
                                                throws DataAccessException {
        LOGGER.debug("addTruck({})", truck);

        return truckDao.addTruck(truck);
    }

    /**
     * @param id to delete.
     * @throws DataAccessException exception.
     */
    @Override
    public final void deleteTruckById(final Integer id)
                                                throws DataAccessException {
        LOGGER.debug("deleteTruckById({})", id);
        Assert.notNull(id, "id cannot be null");

        truckDao.deleteTruckById(id);
    }

    /**
     * @param truck to update.
     * @throws DataAccessException exception.
     */
    @Override
    public final void updateTruck(final Truck truck)
                                                throws DataAccessException {
        LOGGER.debug("updateTruck({})", truck);
        Assert.notNull(truck, "truck cannot be null");
        truckDao.updateTruck(truck);
    }

    /**
     *
     * @param id .
     * @return truck with details and avg.
     */
    @Override
    public final TruckWithAvgDto getTruckById(final Integer id) {
        LOGGER.debug("getTruckById({})", id);

        Assert.notNull(id, "id cannot be null");
        return truckDao.getTruckById(id);
    }

}
