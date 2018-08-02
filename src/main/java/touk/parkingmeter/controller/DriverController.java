package touk.parkingmeter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import touk.parkingmeter.dto.TicketDto;
import touk.parkingmeter.service.TicketService;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private TicketService ticketService;

    @Autowired
    public DriverController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/startTicket")
    public ResponseEntity<TicketDto> startTicket(@RequestBody TicketDto ticketDto) {
        TicketDto ticket = ticketService.startTicket(ticketDto);

        return new ResponseEntity<TicketDto>(ticket, HttpStatus.CREATED);
    }

    @PostMapping("/endTicket")
    public ResponseEntity<TicketDto> endTicket(@RequestBody TicketDto ticketDto) {
        TicketDto ticket = ticketService.endTicket(ticketDto.getPlateNumber());

        return new ResponseEntity<TicketDto>(ticket, HttpStatus.OK);
    }

    @GetMapping("/{plateNumber}")
    public ResponseEntity<TicketDto> checkTicket(@PathVariable String plateNumber) {
        TicketDto ticket = ticketService.checkTicket(plateNumber);

        return new ResponseEntity<TicketDto>(ticket, HttpStatus.OK);
    }

}
