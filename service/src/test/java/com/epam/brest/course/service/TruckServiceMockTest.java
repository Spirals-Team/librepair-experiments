package com.epam.brest.course.service;

import com.epam.brest.course.dao.TruckDao;
import com.epam.brest.course.dto.TruckWithAvgDto;
import com.epam.brest.course.model.Truck;
import com.epam.brest.course.service.mockConfig.MockConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MockConfig.class, TruckServiceImpl.class})
public class TruckServiceMockTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int ID = 12;
    private static final String DESCRIPTIONS = "BLACK TRUCK";

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Autowired
    private TruckService truckService;

    @Autowired
    private TruckDao truckDao;

    private Truck truck;
    private Truck truck2;
    private TruckWithAvgDto truckWithAvgDto;

    @Before
    public void setup() {

        truck = new Truck();
        truck.setDescriptions("NEW TRUCK");
        truck.setTruckCode("BY2442");
        truck.setTruckId(32);

        truck2 = new Truck();
        truck2.setDescriptions(DESCRIPTIONS);
        truck2.setTruckCode("BY2606");
        truck2.setTruckId(12);

        truckWithAvgDto = new TruckWithAvgDto();
        truckWithAvgDto.setTruckId(12);
        truckWithAvgDto.setAvgPerMonth(20.0);
        truckWithAvgDto.setDescriptions(DESCRIPTIONS);
        truckWithAvgDto.setTruckCode("BY2442");

    }

    @After
    public void tearDown(){
        Mockito.reset(truckDao);
    }


    @Test
    public void getTrucks() {
        LOGGER.debug("test: getTrucks()");

        when(truckDao.getAllTrucks()).thenReturn(Arrays.asList(truck, truck2));

        Collection<Truck> truckCollection =
                truckService.getAllTrucks();
        Assert.assertTrue(truckCollection.containsAll(Arrays.asList(truck, truck2)));
        Mockito.verify(truckDao).getAllTrucks();
    }


    @Test
    public void saveTruck() {
        LOGGER.debug("test: saveTruck()");

        when(truckDao.addTruck(truck)).thenReturn(truck2);

        Truck newTruck = truckService.addTruck(truck);

        //assertions
        Assert.assertEquals(newTruck.getTruckCode(), "BY2606");
        Mockito.verify(truckDao).addTruck(truck);
    }


    @Test
    public void getTruckById() {
        LOGGER.debug("test: getTruckById()");

        when(truckDao.getTruckById(ID)).thenReturn(truckWithAvgDto);
        TruckWithAvgDto truckWithAvgDto = truckService.getTruckById(ID);

        //assertions
        Assert.assertEquals(truckWithAvgDto.getTruckCode(), "BY2442");
        Mockito.verify(truckDao).getTruckById(ID);
    }


    @Test
    public void deleteTruck() {
        LOGGER.debug("test: deleteTruck()");

       truckService.deleteTruckById(ID);
        //assertions
        Mockito.verify(truckDao).deleteTruckById(ID);
    }

    @Test
    public void updateAnExistingTruck() {
        LOGGER.debug("test: updateAnExistingTruck()");

         truck.setTruckCode("BY9099");
         truckService.updateTruck(truck);

        Assert.assertTrue(truck.getTruckCode().matches("BY9099"));
        Mockito.verify(truckDao).updateTruck(truck);
    }

    @Test
    public void findOneTruck_ThrowsIllegalArgs() {

        LOGGER.debug("test: findOneTruck_ThrowsIllegalArgs()");

        when(truckDao.getTruckById(null)).thenThrow(new IllegalArgumentException("") {
        });

        exception.expect(IllegalArgumentException.class);
        truckService.getTruckById(null);
    }



}
