package com.m2dl.dlmovie.movies.services

import com.m2dl.dlmovie.movies.domain.Mark
import com.m2dl.dlmovie.movies.domain.Movie
import com.m2dl.dlmovie.movies.repositories.MarkRepository
import com.m2dl.dlmovie.movies.services.MarkService
import com.m2dl.dlmovie.users.domain.User
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification

@SpringBootTest
class MarkServiceTest extends Specification {

    private MarkService markService;

    private MarkRepository markRepository;

    def setup() {
        markRepository = Stub(MarkRepository.class)
        markService = new MarkService(markRepository);
    }

    def "Test get all"(){
        given:
            Movie movie = new Movie();
            User user = new User("val", "v@v.com", "ynwa");
            markRepository.findAll() >> {
                List<Mark> list = new ArrayList<>();
                list.add(new Mark(user: user, movie: movie));
                list.add(new Mark(user: user, movie: movie));
                list.add(new Mark(user: user, movie: movie));
                list.add(new Mark(user: user, movie: movie));
                return list
            }
        when:
            List<Mark> markList = markService.getAll(movie);
        then:
            markList.size() == 4;
    }

    def "Test save"(){
        given:
            Movie movie = new Movie();
            Mark mark = new Mark();
            User user = new User("val", "v@v", "ynwa");
            markRepository.save(mark) >> {
                mark.setId(1L);
                return mark;
            }
        when:
            Mark markResult = markService.createMark(movie, user, mark);
        then:
            markResult.getId() == 1L;
    }

}
