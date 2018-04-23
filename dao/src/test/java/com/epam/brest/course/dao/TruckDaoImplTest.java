package com.epam.brest.course.dao;

import com.epam.brest.course.dto.TruckFullDetailDto;
import com.epam.brest.course.dto.TruckWithAvgDto;
import com.epam.brest.course.model.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-db-spring.xml",
        "classpath:dao-test.xml", "classpath:dao.xml"})
@Rollback
@Transactional
public class TruckDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String DATE = "2006-01-21";
    private static final String BLUE_VAN = "BLUE VAN";
    private static final String TRUCK_CODE_1 = "BY2000";
    private static final String TRUCK_CODE = "BY8754";
    private static final String NEW_TRUCK_CODE = "BY8888";
    private static final int ONE = 1;
    private static final String AN_EXISTING_TRUCK = "BY2334";

    private SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");

    private static final int ID = 2;
    private static final int SIZE = 5;

    @Autowired
    private TruckDao truckDao;

    @Autowired
    private Mapper mapper;


    @Test(expected = EmptyResultDataAccessException.class)
    public void getTruckByIdWithNullValue() {
        LOGGER.debug("test: getTruckByIdWithNullValue()");

        //give null value we should get empty result .
        TruckWithAvgDto truck = truckDao.getTruckById(null);
        Assert.assertNotNull(truck);
    }

    @Test
    public void getTruckByIdWithValidInput() {
        LOGGER.debug("test: getTruckByIdWithValidInput()");

        TruckWithAvgDto truck = truckDao.getTruckById(ID);
        Assert.assertNotNull(truck);
    }

    @Test
    public void getAllTrucks() {
        LOGGER.debug("test: getAllTrucks()");

        Collection<Truck> trucks = truckDao.getAllTrucks();
        //assert we have list of trucks
        Assert.assertNotNull(trucks);
        Assert.assertTrue(trucks.size() > SIZE);
    }

    //this test for getting full truck details with orders for this truck.
    @Test
    public void getTruckDetailsFull() {
        TruckFullDetailDto truckDetails = truckDao.getTruckFullDetailById(1);
        Assert.assertNotNull(truckDetails);
    }

    //test for get truck by code with average per month for this truck.
    @Test
    public void getTruckDetailLiteById() {

        TruckWithAvgDto truck = truckDao.getTruckById(1);
        Assert.assertNotNull(truck);
        //avg is 0 for this truck
        Assert.assertTrue(truck.getAvgPerMonth() == 0.0);
    }


    @Test(expected = DataIntegrityViolationException.class)
    public void addTruckWithTruckCodeAlreadyInUse() throws Exception {
        LOGGER.debug("test: addTruckWithTruckCodeAlreadyInUse()");

        Date date = formatter.parse(DATE);
        Truck truck = new Truck(AN_EXISTING_TRUCK, date, BLUE_VAN);
        //save truck
        truckDao.addTruck(truck);
        Assert.assertNotNull(truck);
    }


    @Test
    public void addTruckWithTruckCodeUnique() throws Exception {
        LOGGER.debug("test: addTruckWithTruckCodeUnique()");

        Date date = formatter.parse(DATE);
        Truck truck = new Truck(TRUCK_CODE_1, date, BLUE_VAN);
        //save method
        truckDao.addTruck(truck);

        //assert not null and code is what i expect
        Assert.assertNotNull(truck);
        Assert.assertEquals(truck.getTruckCode(), TRUCK_CODE_1);
    }


    @Test
    public void updateAnExistingTruck() throws Exception {
        LOGGER.debug("test: updateAnExistingTruck()");

        TruckWithAvgDto truckWithAvgDto = truckDao.getTruckById(ID);
        Assert.assertEquals(truckWithAvgDto.getTruckCode(), TRUCK_CODE);
        truckWithAvgDto.setTruckCode(NEW_TRUCK_CODE);

        Truck truck = mapper.map(truckWithAvgDto, Truck.class);

        truckDao.updateTruck(truck);

        //get same truck from db
        TruckWithAvgDto truckWithNewCode = truckDao.getTruckById(ID);
        Assert.assertEquals(truckWithNewCode.getTruckCode(), NEW_TRUCK_CODE);
    }

    @Test
    public void deleteAnExistingTruck() throws Exception {
        LOGGER.debug("test: deleteAnExistingTruck()");

        Collection<Truck> collection = truckDao.getAllTrucks();

        int sizeBefore = collection.size();
        truckDao.deleteTruckById(ID);
        Collection<Truck> collectionAfterDelete = truckDao.getAllTrucks();
        int sizeAfter = collectionAfterDelete.size();
        Assert.assertTrue((sizeAfter + ONE) == sizeBefore);
    }

}
