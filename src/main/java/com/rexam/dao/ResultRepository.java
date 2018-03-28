package com.rexam.dao;

import java.util.List;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.rexam.model.Exam;
import com.rexam.model.IdResult;
import com.rexam.model.Result;

public interface ResultRepository extends CrudRepository<Result, IdResult> {

    public List<Result> findByExam(Exam exam);

    
    @Procedure(name="computeAvg")
    public void computeAvg(@Param("tu_code") String tu_code, @Param("sy_id")Integer sy_id,@Param("sy_y") Integer sy_y);
}
