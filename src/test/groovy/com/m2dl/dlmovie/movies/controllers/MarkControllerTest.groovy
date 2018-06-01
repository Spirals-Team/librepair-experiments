package com.m2dl.dlmovie.movies.controllers

import com.m2dl.dlmovie.movies.domain.Mark
import com.m2dl.dlmovie.movies.domain.Movie
import com.m2dl.dlmovie.movies.requests.MarkRequest
import com.m2dl.dlmovie.movies.services.MarkService
import com.m2dl.dlmovie.movies.services.MovieService
import com.m2dl.dlmovie.users.domain.User
import com.m2dl.dlmovie.users.services.UserService
import spock.lang.Specification

class MarkControllerTest extends Specification {

    def "get mark list for a movie"(){
        given: "A controller with a stub service returning 4 marks"
            MarkService markService = Mock()
            MovieService movieService = Mock()
            UserService userService = Mock()
            MarkController markController = new MarkController(markService, movieService, userService);
            markService.getAll() >> {
                User user = new User("val", "v@v.com", "ynwa");
                List<Mark> list = new ArrayList<>();
                list.add(new Mark(user: user));
                list.add(new Mark(user: user));
                list.add(new Mark(user: user));
                list.add(new Mark(user: user));
                return list
            }
        when: "we call the controller"
            List<Mark> marks = markController.getAll();
        then: "You have 4 marks"
            marks.size() == 4
    }

    def "save a new mark"(){
        given: "a mark to save"
            MarkService markService = Mock();
            MovieService movieService = Mock();
            UserService userService = Mock();
            Movie movie = new Movie(title: "SG8 LVP", description: "Super film", date: new Date(), director: "Klopp");
            User user = new User("val", "v@v.com", "ynwa");
            Mark mark = new Mark(value: 4, date: new Date(), movie: movie, user: user)
            userService.getById(1L) >> {
                return user
            }
            movieService.get(1L) >> {
                return movie
            }
            markService.createMark(_) >> {
                mark.setId(1L)
                mark
            }
            MarkController markController = new MarkController(markService, movieService, userService)
        when: "We save a new Mark"
            Mark result = markController.save(new MarkRequest(4, 1L, 1L));
        then: "the mark has an id"
            result.getId() == 1L
    }

}
