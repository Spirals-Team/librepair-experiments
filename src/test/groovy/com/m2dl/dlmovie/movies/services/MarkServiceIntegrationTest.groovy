package com.m2dl.dlmovie.movies.services

import com.m2dl.dlmovie.DlmovieApplication
import com.m2dl.dlmovie.movies.domain.Mark
import com.m2dl.dlmovie.movies.domain.Movie
import com.m2dl.dlmovie.movies.repositories.MarkRepository
import com.m2dl.dlmovie.users.domain.User
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

import javax.validation.ConstraintViolationException


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
            def count = markRepository.findAll().size()
        when: "we retrieve all marks"
            List<Mark> marks = markService.getAll();
        then: "we have the three marks"
            marks.size() == count
    }

    def "Test Save mark"(){
        given: "A valid mark to save"
            Mark mark = new Mark(value: 1, date: new Date());
        when: "we save the mark"
            mark = markService.createMark(mark)
        then: "the mark has an id"
            mark.getId() != null
    }

    def "Test not valide Save mark"(){
        given: "An invalid mark to save"
            Mark mark = new Mark(value: 10);
        when: "we save the mark"
            mark = markService.createMark(mark)
        then: "the mark has an id"
            thrown(ConstraintViolationException)
            mark.getId() == null
    }

    def "Test save mark with user"(){
        given: "A mark with a user"
        User user = new User("valentin", "v@v", "ynwa-go-to-kiev")
            Mark mark = new Mark(value: 2, user: user, date: new Date())
        when: "we save the mark"
            mark = markService.createMark(mark)
        then: "the mark has the user"
            mark.getUser() != null
    }

    def "Test save mark with movie"(){
        given: "A mark with a user"
            Movie movie = new Movie(title: "Go to kiev", description: "La sixième", date: new Date(), director: "Klopp")
            Mark mark = new Mark(value: 2, movie: movie, date: new Date())
        when: "we save the mark"
            mark = markService.createMark(mark)
        then: "the mark has the user"
            mark.getMovie() != null
    }

}
