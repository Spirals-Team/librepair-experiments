package ar.edu.undav.semillero.service;

import ar.edu.undav.semillero.domain.entity.Company;
import ar.edu.undav.semillero.domain.repository.CompanyRepository;
import ar.edu.undav.semillero.dto.CompanyDTO;
import ar.edu.undav.semillero.request.CreateCompanyRequest;
import ar.edu.undav.semillero.request.FilterCompaniesRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final QueryRunner queryRunner;

    public CompanyService(CompanyRepository companyRepository, QueryRunner queryRunner) {
        this.companyRepository = companyRepository;
        this.queryRunner = queryRunner;
    }

    @Transactional
    public Company save(CreateCompanyRequest request) {
        return companyRepository.save(new Company(request.getName(), request.getContact(), request.getEmail(), request.getComments()));
    }

    @Transactional(readOnly = true)
    public Page<CompanyDTO> list(FilterCompaniesRequest request, Pageable pageable) {
        QueryBuilder<CompanyDTO> queryBuilder = QueryBuilder.of(CompanyDTO.class)
                .select("new ar.edu.undav.semillero.dto.CompanyDTO(c.id, c.name, c.email, (select count(i) from ar.edu.undav.semillero.domain.entity.Interview i where i.company = c and i.workFromDate <= now() and (i.workToDate = null or i.workToDate >= now())), (select count(i) from ar.edu.undav.semillero.domain.entity.Interview i where i.company = c))")
                .from(Company.class, "c")
                .ilike("c.name", request.getName())
                .condition(request.isWorkers(), "exists (from ar.edu.undav.semillero.domain.entity.Interview i where i.company = c and i.workFromDate <= now() and (i.workToDate = null or i.workToDate >= now()))")
                .condition(request.isInterviews(), "exists (from ar.edu.undav.semillero.domain.entity.Interview i where i.company = c)")
                .orderAscBy("c.id");
        return queryRunner.run(CompanyDTO.class, queryBuilder, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Transactional
    public Optional<Company> update(long id, CreateCompanyRequest request) {
        Optional<Company> optCompany = companyRepository.findById(id);
        optCompany.ifPresent(c -> {
            c.setName(request.getName());
            c.setContact(request.getContact());
            c.setEmail(request.getEmail());
            c.setComments(request.getComments());
        });
        return optCompany;
    }
}
