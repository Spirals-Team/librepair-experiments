package com.utn.tp5.controllers;

import com.utn.tp5.DTO.CabinDTO;
import com.utn.tp5.service.CabinService;
import com.utn.tp5.model.Cabin;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cabin")
public class CabinController {
    @Autowired
    private CabinService cabinService;

    @GetMapping(value = "/", produces = "application/json")
    public List<CabinDTO> listCabins() {
        List<Cabin> cabins = cabinService.getAll();
        List<CabinDTO> rtn = new ArrayList<>();
        for (Cabin cabin : cabins) {
            rtn.add(new CabinDTO(cabin));
        }
        return rtn;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public CabinDTO getCabinById(@PathVariable("id") Long id) {
        Cabin cabin = cabinService.getById(id);
        CabinDTO rtn = new CabinDTO(cabin);
        return rtn;
    }

    @GetMapping(value = "/create/{name}")
    public void createCabin(@PathVariable("name")String name) {
        Cabin cabin = new Cabin(name);
        cabinService.saveCabin(cabin);
    }

    @DeleteMapping(value = "/delete/{name}")
    public void deleteCabin(@PathVariable("name")String name) {
        cabinService.deleteCabin(name);
    }
}
