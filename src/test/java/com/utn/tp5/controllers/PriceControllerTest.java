package com.utn.tp5.controllers;

import com.utn.tp5.DTO.PriceDTO;
import com.utn.tp5.service.PriceService;
import com.utn.tp5.model.Price;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PriceControllerTest {

    private PriceController priceController;
    private PriceService priceService;
    private Price price;

    @Before
    public void contextLoads() {
        this.priceService = mock(PriceService.class);
        this.priceController = new PriceController(this.priceService);
        this.price = new Price(1234, mock(Date.class));
        this.price.setId((long) 1);
        when(this.priceService.getById((long) 1)).thenReturn(this.price);
        when(this.priceService.savePrice(this.price)).thenReturn(true);
    }

    @Test
    public void whenPriceListIsAsked() {
        List<Price> prices = new ArrayList<>();
        prices.add(this.price);
        when(this.priceService.getAll()).thenReturn(prices);
        List<PriceDTO> DTOList = priceController.listPrices();
        for (Price price : prices) {
            DTOList.add(new PriceDTO(price));
        }
        assertEquals(prices.get(0).getPrice(), DTOList.get(0).getPrice());
    }

    @Test
    public void whenAPriceIsAskedById() {
        PriceDTO a = priceController.getPriceById(this.price.getId());
        PriceDTO b = new PriceDTO(this.price);
        assertEquals(a.getPrice(), b.getPrice());
    }

    @Test
    public void whenAPriceIsAdded() {
        /*this.priceService.savePrice(this.price);
        verify(this.priceService).savePrice(this.price);*/
    }

    @Test
    public void whenAPriceIsDeleted() {
        this.priceController.deletePrice(this.price);
        verify(this.priceService).deletePrice(this.price);
    }
}
