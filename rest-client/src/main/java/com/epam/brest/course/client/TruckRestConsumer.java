package com.epam.brest.course.client;

import com.epam.brest.course.dto.TruckWithAvgDto;
import com.epam.brest.course.model.Truck;
import com.epam.brest.course.service.TruckService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

/**
 * client rest consumer service.
 */
public class TruckRestConsumer implements TruckService {


    /**
     * logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * url.
     */
    private final String url;
    /**
     * rest template.
     */
    private RestTemplate restTemplate;


    /**
     * @param url1          url for getting resource.
     * @param restTemplate1 restTemplate.
     */
    public TruckRestConsumer(final String url1,
                             final RestTemplate restTemplate1) {
        url = url1;
        this.restTemplate = restTemplate1;
    }

    /**
     * @return list of trucks.
     * @throws DataAccessException exception.
     */
    @SuppressWarnings("unchecked")
    @Override
    public final Collection<Truck> getAllTrucks() throws DataAccessException {
        LOGGER.debug("getAllTrucks()");
        ResponseEntity responseEntity =
                restTemplate.getForEntity(url, List.class);
        List<Truck> trucks = (List<Truck>) responseEntity.getBody();
        responseEntity.getBody();
        return trucks;
    }

    /**
     * @param truck to add.
     * @return new truck.
     * @throws DataAccessException exception.
     */
    @Override
    public final Truck addTruck(final Truck truck) throws DataAccessException {
        LOGGER.debug("addTruck({})", truck);
        ResponseEntity<Truck> responseEntity =
                restTemplate.postForEntity(url, truck, Truck.class);
        Truck result = responseEntity.getBody();
        return result;
    }

    /**
     * @param id to get.
     * @return truck.
     * @throws DataAccessException exception.
     */
    @Override
    public final TruckWithAvgDto getTruckById(final Integer id)
            throws DataAccessException {
        LOGGER.debug("getTruckById({})", id);

        ResponseEntity<TruckWithAvgDto> responseEntity =

                restTemplate.getForEntity(url + "/" + id, TruckWithAvgDto.class);
        TruckWithAvgDto result = responseEntity.getBody();

        return result;
    }

    /**
     * @param id to delete.
     * @throws DataAccessException exception.
     */
    @Override
    public final void deleteTruckById(final Integer id)
            throws DataAccessException {
        LOGGER.debug("deleteTruckById({})", id);

        restTemplate.delete(url + "/" + id);
    }

    /**
     * @param truck to update.
     * @throws DataAccessException exception.
     */
    @Override
    public final void updateTruck(final Truck truck)
            throws DataAccessException {
        LOGGER.debug("updateTruck({})", truck);

        restTemplate.put(url, truck);

    }

}
