package com.rexam.dao;

import org.springframework.data.repository.CrudRepository;

import com.rexam.model.IdResult;
import com.rexam.model.Result;

public interface ResultRepository extends CrudRepository<Result, IdResult>{

}
