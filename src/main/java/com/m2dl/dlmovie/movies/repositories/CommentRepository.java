package com.m2dl.dlmovie.movies.repositories;

import com.m2dl.dlmovie.movies.domain.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment,Long> {
}
