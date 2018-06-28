package com.ally.invoicify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ally.invoicify.models.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
