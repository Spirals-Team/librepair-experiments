package com.gianhaack.desafiocontaazul.controller;

import com.gianhaack.desafiocontaazul.dto.BankSlipDTO;
import com.gianhaack.desafiocontaazul.dto.BankSlipDetailsDTO;
import com.gianhaack.desafiocontaazul.dto.BankSlipStatusDTO;
import com.gianhaack.desafiocontaazul.model.BankSlip;
import com.gianhaack.desafiocontaazul.model.BankSlipStatus;
import com.gianhaack.desafiocontaazul.service.BankSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/bankslips")
public class BankSlipController {

    @Autowired
    private BankSlipService bankSlipService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll() {
        List<BankSlipDTO> dtos = new ArrayList<>();
        bankSlipService.findAll().forEach(bankSlip -> {
            BankSlipDTO dto = new BankSlipDTO(bankSlip);
            dtos.add(dto);
        });

        return ResponseEntity.ok().body(dtos);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@Valid @RequestBody BankSlipDTO bankSlipDTO) {
        BankSlip bankSlipSave = bankSlipService.save(bankSlipDTO.convert());
        return ResponseEntity.status(HttpStatus.CREATED).body(new BankSlipDTO(bankSlipSave));
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity detail(@PathVariable UUID uuid) {
        BankSlip bankSlip = bankSlipService.findByUuid(uuid);
        BankSlipDetailsDTO datailsDto = new BankSlipDetailsDTO(bankSlip);
        return ResponseEntity.ok().body(datailsDto);
    }

    @RequestMapping(value = "/{uuid}/payments", method = RequestMethod.PUT)
    public ResponseEntity pay(@PathVariable UUID uuid, @RequestBody BankSlipStatusDTO bankSlipStatusDTO) {
        BankSlip bankSlip = bankSlipService.findByUuid(uuid);

        bankSlip.setPaymentDate(bankSlipStatusDTO.getPaymentDate());
        bankSlip.setStatus(BankSlipStatus.PAID);
        bankSlipService.save(bankSlip);

        BankSlipStatusDTO statusDTO = new BankSlipStatusDTO(bankSlip);
        return ResponseEntity.ok().body(statusDTO);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    public ResponseEntity cancel(@PathVariable UUID uuid) {
        BankSlip bankSlip = bankSlipService.findByUuid(uuid);

        bankSlip.setStatus(BankSlipStatus.CANCELED);
        bankSlipService.save(bankSlip);

        BankSlipStatusDTO statusDTO = new BankSlipStatusDTO(bankSlip);
        return ResponseEntity.ok().body(statusDTO);
    }
}
