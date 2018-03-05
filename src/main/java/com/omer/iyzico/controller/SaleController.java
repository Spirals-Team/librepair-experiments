package com.omer.iyzico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omer.iyzico.model.Sale;
import com.omer.iyzico.service.SaleService;

@RestController
@RequestMapping("/")
public class SaleController {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Autowired
	SaleService saleService;

	@RequestMapping(path = "sales", produces = MediaType.APPLICATION_JSON_VALUE)
	public String sales() {
		List<Sale> findAll = saleService.findAll();
		return gson.toJson(findAll);
	}

	@RequestMapping(path = "sales/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String sale(@PathVariable("id") Integer id) {
		Sale sale = saleService.findById(id);
		return gson.toJson(sale);
	}
}
