package com.gianhaack.desafiocontaazul.repository;

import com.gianhaack.desafiocontaazul.model.BankSlip;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BankSlipRepository extends CrudRepository<BankSlip, UUID> {

}
