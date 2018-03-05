package com.omer.iyzico.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iyzipay.model.BinNumber;
import com.omer.iyzico.checkers.BinNumberCheck;
import com.omer.iyzico.checkers.PaymentCheck;
import com.omer.iyzico.model.Request;

@RestController
@RequestMapping("/")
public class PaymentController {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Autowired
	PaymentCheck paymentCheck;

	@RequestMapping(path = "/payment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map payment(@RequestBody Request request) {
		String result = paymentCheck.payment(request);
		return Collections.singletonMap("result", result);
	}

	@RequestMapping(path = "/getBinNumber", produces = MediaType.APPLICATION_JSON_VALUE)
	public BinNumber binNumber(@RequestParam String binNumber) {
		return new BinNumberCheck().checkBinNumber(binNumber);
	}
}