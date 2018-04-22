package ar.edu.undav.semillero.controller;

import ar.edu.undav.semillero.domain.entity.Company;
import ar.edu.undav.semillero.dto.CompanyDTO;
import ar.edu.undav.semillero.request.CreateCompanyRequest;
import ar.edu.undav.semillero.request.FilterCompaniesRequest;
import ar.edu.undav.semillero.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/company")
@CrossOrigin
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // Agregar una company
    @PostMapping
    public Company addCompany(@Valid @RequestBody CreateCompanyRequest request) {
        return companyService.save(request);
    }

    // Obtener company por ID
    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable long id) {
        return WebUtils.emptyToNotFound(companyService.findById(id));
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<Company> update(@PathVariable long id, @Valid @RequestBody CreateCompanyRequest request) {
        return WebUtils.emptyToNotFound(companyService.update(id, request));
    }

    @ResponseBody
    @GetMapping
    public Page<CompanyDTO> list(@Valid FilterCompaniesRequest request, Pageable pageable) {
        return companyService.list(request, pageable);
    }
}
