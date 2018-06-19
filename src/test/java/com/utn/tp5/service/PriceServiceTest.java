package com.utn.tp5.service;

import com.utn.tp5.persistence.PricePersistence;

import com.utn.tp5.model.Price;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PriceServiceTest {

    private PriceService priceService;
    private Price price;
    private PricePersistence pricePersistence;

    @Before
    public void contextLoads() {
        this.pricePersistence = mock(PricePersistence.class);
        this.priceService = new PriceService(pricePersistence);
        this.price = mock(Price.class);
        when(pricePersistence.getOne(this.price.getId())).thenReturn(this.price);
    }

    @Test
    public void whenAPriceIsSaved() {
        when(pricePersistence.save(this.price)).thenReturn(this.price);
        Boolean res = this.priceService.savePrice(this.price);
        assertEquals(Boolean.TRUE, res);
    }


    @Test
    public void whenAPriceIsAskedById() {
        Price c = this.priceService.getById(this.price.getId());
        assertEquals(this.price, c);
    }

    @Test
    public void whenThePriceListIsAsked() {
        List<Price> expected = new ArrayList<>();
        when(pricePersistence.findAll()).thenReturn(expected);
        List<Price> prices = this.priceService.getAll();
        assertEquals(prices, expected);
    }

    @Test
    public void whenThePriceIsDeleted() {
        this.priceService.deletePrice(this.price);
        verify(this.pricePersistence).delete(this.price);
    }
}
