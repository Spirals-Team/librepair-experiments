package com.ipiecoles.java.java340.repository;

import com.ipiecoles.java.java340.model.Technicien;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface TechnicienRepository extends BaseEmployeRepository<Technicien> {

    List<Technicien> findByGradeBetween(Integer gradeLower, Integer gradeUpper);

    Slice<Technicien> findTop5ByGrade(Integer grade);

}
