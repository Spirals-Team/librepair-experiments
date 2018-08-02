package touk.parkingmeter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import touk.parkingmeter.service.ChargeService;
import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    private ChargeService chargeService;

    @Autowired
    public OwnerController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @GetMapping("/{date}")
    public ResponseEntity<BigDecimal> getDailyIncome(@PathVariable String date) {
        BigDecimal income = chargeService.calculateIncomeFromDay(LocalDate.parse(date));

        return new ResponseEntity<BigDecimal>(income, HttpStatus.OK);
    }
}
