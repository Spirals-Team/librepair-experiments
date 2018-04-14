package com.prussia.play.spring.repository;

import java.util.List;

import com.prussia.play.spring.domain.po.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepostory extends JpaRepository<Customer, Long> {
	
	public List<Customer> findByFirstName(String firstName);

}
