package com.ally.invoicify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ally.invoicify.models.InvoiceLineItem;

public interface InvoiceLineItemRepository extends JpaRepository<InvoiceLineItem, Long> {

}
