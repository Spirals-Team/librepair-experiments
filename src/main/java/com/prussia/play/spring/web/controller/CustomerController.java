package com.prussia.play.spring.web.controller;

import com.prussia.play.spring.dao.CustomerDao;
import com.prussia.play.spring.domain.po.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.prussia.play.spring.dao.CachableCustomerDao;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/customer")
@Slf4j
public class CustomerController {
	
	@Autowired
	//@Qualifier ( "customerDao" ) //inject by specific name
			CustomerDao repository;
	
	@Autowired
	CachableCustomerDao cachableRepository;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String get(Long id) {
		log.info("method = get");
		log.info("run cachable method : " + cachableRepository.findByLastName("Bloch"));;
		log.info("customerDao" + repository.toString());
		if (id == null) {
			Iterable<Customer> customers = repository.findAll();
			customers.forEach(customer -> log.info(customer.toString()));
			return customers.toString();
		} else {
			Customer customer = repository.findOne(id);
			log.info(customer.toString());
			return customer.toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody long create(@RequestBody Customer customer) {
		Customer customerVo = repository.save(customer);
		return customerVo.getId();
	}
}
