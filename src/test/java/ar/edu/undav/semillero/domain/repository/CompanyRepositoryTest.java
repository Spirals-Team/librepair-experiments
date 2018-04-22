package ar.edu.undav.semillero.domain.repository;

import ar.edu.undav.semillero.domain.entity.Company;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

/**
 * Juan Lagostena on 12/10/16 jlagostena@bitsense.com.ar .
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void testSave() {
        Company company = new Company("compañia", "Pepe", "mail@server.com", "comentarios");
        companyRepository.save(company);
    }

    @Test
    public void testFindAll() {
        Collection<Company> companies = companyRepository.findAll();
        Assertions.assertThat(companies).isNotEmpty();
    }
}
