package com.ally.invoicify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ally.invoicify.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
