package com.gianhaack.desafiocontaazul.service;

import com.gianhaack.desafiocontaazul.model.BankSlip;
import com.gianhaack.desafiocontaazul.model.BankSlipStatus;
import com.gianhaack.desafiocontaazul.repository.BankSlipRepository;
import com.gianhaack.desafiocontaazul.service.exceptions.BankslipNotProvidedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BankSlipService {

    @Autowired
    private BankSlipRepository bankSlipRepository;

    public BankSlip save(BankSlip bankSlip) {
        if (bankSlip == null) {
            throw new BankslipNotProvidedException("Bankslip not provided in the request body");
        }

        if (bankSlip.getId() == null) {
            bankSlip.setId(UUID.randomUUID());
            bankSlip.setStatus(BankSlipStatus.PENDING);
        }

        return bankSlipRepository.save(bankSlip);
    }

    public BankSlip findByUuid(UUID uuid) {
        return bankSlipRepository
                .findById(uuid)
                .orElseThrow(() -> new BankslipNotProvidedException("Bankslip not found with the specified id"));
    }

    public Iterable<BankSlip> findAll() {
        return bankSlipRepository.findAll();
    }

}
