package com.rexam.controller;

import java.util.List;

import javax.validation.Valid;

import com.rexam.model.Result;

public class Results {

    @Valid
    private List<Result> examResults;

    public List<Result> getExamResults() {
        return examResults;
    }

    public void setExamResults(@Valid List<Result> examResults) {
        this.examResults = examResults;
    }
    
}
