package touk.parkingmeter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import touk.parkingmeter.dto.TicketDto;
import touk.parkingmeter.service.TicketService;

@RestController
@RequestMapping("/operator")
public class OperatorController {

    private TicketService ticketService;

    @Autowired
    public OperatorController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{plateNumber}")
    public ResponseEntity<TicketDto> checkTicket(@PathVariable String plateNumber) {
        TicketDto ticket = ticketService.checkTicket(plateNumber);

        return new ResponseEntity<TicketDto>(ticket, HttpStatus.OK);
    }
}
