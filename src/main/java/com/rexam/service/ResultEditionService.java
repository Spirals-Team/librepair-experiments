package com.rexam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rexam.dao.ComponentRepository;
import com.rexam.dao.RegistrationRepository;
import com.rexam.dao.ResultRepository;
import com.rexam.dao.TeachingUnitRepository;
import com.rexam.model.Component;
import com.rexam.model.Exam;
import com.rexam.model.Registration;
import com.rexam.model.TeachingUnit;

@Service
public class ResultEditionService {

    @Autowired
    ResultRepository rRepository;

    @Autowired
    RegistrationRepository regRepository;

    @Autowired
    TeachingUnitRepository tuRepository;

    @Autowired
    ComponentRepository cRepository;

    public void computeAvg(Exam exam) {
        for (Component c : cRepository.findByExam(exam)) {
            for (TeachingUnit tu : tuRepository.findByComponent(c.getId())) {
                for (Registration reg : regRepository.findByIdCodeTeachingUnit(tu.getCode())) {
                    rRepository.computeAvg(tu.getCode(), reg.getStudentYear().getId().getId(),
                            reg.getStudentYear().getId().getYear());
                }
            }
        }
    }

    public void setStatus(String status, Exam exam) {
        for (Component c : cRepository.findByExam(exam)) {
            for (TeachingUnit tu : tuRepository.findByComponent(c.getId())) {
                for (Registration reg : regRepository.findByIdCodeTeachingUnit(tu.getCode())) {
                    rRepository.setStatus(tu.getCode(), reg.getStudentYear().getId().getId(),
                            reg.getStudentYear().getId().getYear(), status);
                }
            }
        }
    }
}
