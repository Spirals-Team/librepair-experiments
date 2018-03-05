package com.omer.iyzico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omer.iyzico.model.Sale;
import com.omer.iyzico.repository.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;

	public Sale findById(Integer id){
		return saleRepository.findById(id);
	}

	public List<Sale> findAll() {
		return saleRepository.findAll();
	}

	public Sale save(Sale sale) {
		return saleRepository.save(sale);
	}
	public void delete(String processId) {
		saleRepository.deleteByProcessId(processId);
	}
}
