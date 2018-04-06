package com.m2dl.dlmovie.movies.services

import com.m2dl.dlmovie.DlmovieApplication
import com.m2dl.dlmovie.movies.domain.Mark
import com.m2dl.dlmovie.movies.repositories.MarkRepository
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification


@ContextConfiguration
@SpringBootTest
class MarkServiceIntegrationTest extends Specification {

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private MarkService markService;

    def "setup"(){

    }

    def "Test get all"() {
        given: "3 saved marks"
            markRepository.save(new Mark(1, new Date()))
            markRepository.save(new Mark(2, new Date()))
            markRepository.save(new Mark(3, new Date()))
        when: "we retrieve all marks"
            List<Mark> marks = markService.getAll();
        then: "we have the three marks"
            marks.size() == 3
    }
}
