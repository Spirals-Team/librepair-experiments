package com.m2dl.dlmovie.movies.services;

import com.m2dl.dlmovie.movies.domain.Mark;
import com.m2dl.dlmovie.movies.domain.Movie;
import com.m2dl.dlmovie.movies.repositories.MarkRepository;
import com.m2dl.dlmovie.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarkService{

    @Autowired
    private MarkRepository markRepository;


    public MarkService(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    public List<Mark> getAll(Movie movie) {
        return (List<Mark>) markRepository.findAll();
    }

    public Mark createMark(Movie movie, User user, Mark mark) {
        return markRepository.save(mark);
    }
}
