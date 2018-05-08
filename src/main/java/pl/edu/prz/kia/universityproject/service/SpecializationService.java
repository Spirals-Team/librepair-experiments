package pl.edu.prz.kia.universityproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.prz.kia.universityproject.model.Faculty;
import pl.edu.prz.kia.universityproject.model.Specialization;
import pl.edu.prz.kia.universityproject.repository.FacultyRepository;
import pl.edu.prz.kia.universityproject.repository.SpecializationRepository;

import java.util.List;

@Service
public class SpecializationService {

    private SpecializationRepository specializationRepository;

    @Autowired
    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    public List<Specialization> findAll() {
        return specializationRepository.findAll();
    }
}
