package com.utn.tp5.controllers;
import com.utn.tp5.DTO.PriceDTO;
import com.utn.tp5.service.PriceService;
import com.utn.tp5.model.Price;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/price")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping(value = "/", produces = "application/json")
    public List<PriceDTO> listPrices() {
        List<Price> prices = priceService.getAll();
        List<PriceDTO> rtn = new ArrayList<>();
        for (Price price : prices) {
            rtn.add(new PriceDTO(price));
        }
        return rtn;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public PriceDTO getPriceById(@PathVariable("id") Long id) {
        Price price = priceService.getById(id);
        PriceDTO rtn = new PriceDTO(price);
        return rtn;
    }

    @PostMapping(value = "/create")
    public void createPrice(int price, String date) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yy");
        Date dateFrom = formatter.parse(date);
        Price price2 = new Price(price, dateFrom);
        priceService.savePrice(price2);
    }

    @DeleteMapping(value = "/delete")
    public void deletePrice(@RequestBody Price price) {
        priceService.deletePrice(price);
    }
}
