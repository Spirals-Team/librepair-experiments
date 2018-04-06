package com.m2dl.dlmovie.movies.repositories;

import com.m2dl.dlmovie.movies.domain.Mark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends CrudRepository<Mark, Long>{
}
