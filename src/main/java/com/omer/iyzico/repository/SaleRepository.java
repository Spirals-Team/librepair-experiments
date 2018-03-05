package com.omer.iyzico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.omer.iyzico.model.Sale;

@Transactional
public interface SaleRepository extends JpaRepository<Sale, Integer> {

	public Sale findById(Integer id);
	
	public void deleteByProcessId(String processId);
}