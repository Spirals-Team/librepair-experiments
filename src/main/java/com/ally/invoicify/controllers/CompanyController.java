package com.ally.invoicify.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ally.invoicify.models.Company;
import com.ally.invoicify.repositories.CompanyRepository;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
	
	@Autowired
	CompanyRepository companyRepo;
	
	@GetMapping
	public List<Company> getAll(){
		return companyRepo.findAll();
	}
	
	@GetMapping("{id}")
	public Company getOne(@PathVariable long id){
		return companyRepo.findOne(id);
	}
	
	@PostMapping
	public Company create(@RequestBody Company company){
		return companyRepo.save(company);
	}
	
	@PutMapping("{id}")
	public Company update(@RequestBody Company company, @PathVariable long id){
		company.setId(id);
		return companyRepo.save(company);
	}
	
	@DeleteMapping("{id}")
	public Company update(@PathVariable long id){
		Company original = companyRepo.findOne(id);
		companyRepo.delete(original);
		return original;
	}
	
}
