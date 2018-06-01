package com.m2dl.dlmovie.movies.services;

import com.m2dl.dlmovie.movies.domain.Mark;
import com.m2dl.dlmovie.movies.repositories.MarkRepository;
import com.m2dl.dlmovie.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MarkService{

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private UserService userRepository;

    @Autowired
    private MovieService movieService;

    public MarkService(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    public List<Mark> getAll() {
        return markRepository.findAll();
    }

    public Mark createMark(Mark mark) {
        if(mark.getUser() != null && mark.getUser().getId() == null){
            userRepository.register(mark.getUser());
        }
        if(mark.getMovie() != null && mark.getMovie().getId() == null){
            movieService.create(mark.getMovie());
        }
        return markRepository.save(mark);
    }

}
