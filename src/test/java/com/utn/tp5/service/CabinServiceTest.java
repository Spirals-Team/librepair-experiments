package com.utn.tp5.service;

import com.utn.tp5.persistence.CabinPersistence;
import com.utn.tp5.model.Cabin;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CabinServiceTest {

    private CabinService cabinService;
    private Cabin cabin;
    private CabinPersistence cabinPersistence;

    @Before
    public void contextLoads() {
        this.cabinPersistence = mock(CabinPersistence.class);
        this.cabinService = new CabinService(cabinPersistence);
        this.cabin = mock(Cabin.class);
    }

    @Test
    public void whenACabinIsSaved() {
        when(cabinPersistence.save(this.cabin)).thenReturn(this.cabin);
        Boolean res = this.cabinService.saveCabin(this.cabin);
        assertEquals(Boolean.TRUE, res);
    }

    @Test
    public void whenACabinIsDeleted() {
        when(cabinPersistence.save(this.cabin)).thenReturn(this.cabin);
        when(cabinPersistence.deleteByName(this.cabin.getName())).thenReturn(true);
        this.cabinService.saveCabin(this.cabin);
        Boolean res = this.cabinService.deleteCabin(this.cabin.getName());
        assertEquals(Boolean.TRUE, res);
    }

    @Test
    public void whenACabinIsAskedById() {
        when(cabinPersistence.getOne(this.cabin.getId())).thenReturn(this.cabin);
        Cabin c = this.cabinService.getById(this.cabin.getId());
        assertEquals(this.cabin, c);
    }

    @Test
    public void whenTheCabinListIsAsked() {
        List<Cabin> expected = new ArrayList<>();
        when(cabinPersistence.findAll()).thenReturn(expected);
        List<Cabin> cabins = this.cabinService.getAll();
        assertEquals(cabins, expected);
    }
}
