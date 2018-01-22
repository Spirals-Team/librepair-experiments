package py.org.fundacionparaguaya.pspserver.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import py.org.fundacionparaguaya.pspserver.security.constants.TermCondPolType;
import py.org.fundacionparaguaya.pspserver.security.dtos.TermCondPolDTO;
import py.org.fundacionparaguaya.pspserver.security.services.TermCondPolService;

@RestController
@RequestMapping(value = "/api/v1/termcondpol")
public class TermCondPolController {

    private TermCondPolService service;

    public TermCondPolController(TermCondPolService service) {
        this.service = service;
    }

    @GetMapping("/last")
    public ResponseEntity<TermCondPolDTO> getLastTermCondPolByType(
        @RequestParam(value = "type", required = true)
        TermCondPolType type) {
        TermCondPolDTO dto = service.getLastTermCondPol(type);
        return ResponseEntity.ok(dto);
    }
}
