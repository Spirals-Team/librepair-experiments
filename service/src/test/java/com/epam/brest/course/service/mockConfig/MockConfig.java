package com.epam.brest.course.service.mockConfig;

import com.epam.brest.course.dao.OrderDao;
import com.epam.brest.course.dao.TruckDao;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class MockConfig {

    @Bean
    public OrderDao orderDao() {
        return Mockito.mock(OrderDao.class);
    }

    @Bean
    public TruckDao truckDao() {
        return Mockito.mock(TruckDao.class);
    }



}
