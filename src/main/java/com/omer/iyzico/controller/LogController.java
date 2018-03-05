package com.omer.iyzico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omer.iyzico.model.Log;
import com.omer.iyzico.service.LogService;

@RestController
@RequestMapping("/")
public class LogController {
	
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Autowired
	LogService logService;

	@RequestMapping(path = "/logs", produces = MediaType.APPLICATION_JSON_VALUE)
	public String logs() {
		List<Log> logs = logService.findAll();
		return gson.toJson(logs);
	}

	@RequestMapping(path = "/logs/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String log(@PathVariable("id") Integer id) {
		Log log = logService.findById(id);
		return gson.toJson(log);
	}
}
