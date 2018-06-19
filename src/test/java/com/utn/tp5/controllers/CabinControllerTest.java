package com.utn.tp5.controllers;

import com.utn.tp5.service.CabinService;
import com.utn.tp5.DTO.CabinDTO;
import com.utn.tp5.model.Cabin;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CabinControllerTest {

    private CabinController cabinController;
    private CabinService cabinService;
    private Cabin cabin;

    @Before
    public void contextLoads() {
        this.cabinService = mock(CabinService.class);
        this.cabinController = new CabinController(this.cabinService);
        this.cabin = new Cabin("Example");
        this.cabin.setId((long) 1);
        when(this.cabinService.getById((long) 1)).thenReturn(this.cabin);
        when(this.cabinService.saveCabin(this.cabin)).thenReturn(true);
    }

    @Test
    public void whenCabinListIsAsked() {
        List<Cabin> cabins = new ArrayList<>();
        cabins.add(this.cabin);
        when(this.cabinService.getAll()).thenReturn(cabins);
        List<CabinDTO> DTOList = cabinController.listCabins();
        for (Cabin cabin : cabins){
            DTOList.add(new CabinDTO(cabin));
        }
        assertEquals(cabins.get(0).getName(), DTOList.get(0).getName());
    }

    @Test
    public void whenACabinIsAskedById() {
        CabinDTO a = cabinController.getCabinById(this.cabin.getId());
        CabinDTO b = new CabinDTO(this.cabin);
        assertEquals(a.getName(), b.getName());
    }

    @Test
    public void whenACabinIsAdded() {
        /*this.cabinService.saveCabin(this.cabin);
        verify(this.cabinService).saveCabin(this.cabin);*/
    }

    @Test
    public void whenACabinIsDeleted() {
        this.cabinController.deleteCabin(this.cabin.getName());
        verify(this.cabinService).deleteCabin(this.cabin.getName());
    }
}
