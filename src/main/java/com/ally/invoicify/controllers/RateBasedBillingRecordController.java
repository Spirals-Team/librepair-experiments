package com.ally.invoicify.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ally.invoicify.models.Company;
import com.ally.invoicify.models.RateBasedBillingRecord;
import com.ally.invoicify.models.User;
import com.ally.invoicify.repositories.BillingRecordRepository;
import com.ally.invoicify.repositories.CompanyRepository;

@RestController
@RequestMapping("/api/billing-record/rate-based")
public class RateBasedBillingRecordController {

	private BillingRecordRepository recordRepository;
	private CompanyRepository companyRepository;

	public RateBasedBillingRecordController(BillingRecordRepository recordRepository,
			CompanyRepository companyRepository) {
		this.recordRepository = recordRepository;
		this.companyRepository = companyRepository;
	}

	@PostMapping("{clientId}")
	public RateBasedBillingRecord create(@RequestBody RateBasedBillingRecord record, @PathVariable long clientId, Authentication auth) {
		 User user = (User) auth.getPrincipal();
		Company client = companyRepository.findOne(clientId);
		record.setClient(client);
		 record.setCreatedBy(user);

		return recordRepository.save(record);
	}

}
