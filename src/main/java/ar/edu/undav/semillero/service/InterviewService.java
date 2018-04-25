package ar.edu.undav.semillero.service;

import ar.edu.undav.semillero.domain.entity.Interview;
import ar.edu.undav.semillero.domain.repository.CompanyRepository;
import ar.edu.undav.semillero.domain.repository.StudentRepository;
import ar.edu.undav.semillero.domain.repository.InterviewRepository;
import ar.edu.undav.semillero.request.CreateInterviewRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;

    public InterviewService(InterviewRepository interviewRepository, StudentRepository studentRepository, CompanyRepository companyRepository) {
        this.interviewRepository = interviewRepository;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public Interview save(CreateInterviewRequest request) {
        return companyRepository.findById(request.getCompany())
                .flatMap(company -> studentRepository.findById(request.getStudent()).map(student -> Pair.of(company, student)))
                .map(pair -> interviewRepository.save(new Interview(pair.getSecond(), pair.getFirst(), "Sin comentarios")))
                .orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    public Collection<Interview> findAll() {
        return interviewRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Interview> findById(Long id) {
        return interviewRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Collection<Interview> findByDate(LocalDate when) {
        return interviewRepository.findByDate(when);
    }

    @Transactional(readOnly = true)
    public Collection<Interview> findByStudent(long studentId) {
        return studentRepository.findById(studentId)
                .map(interviewRepository::findByStudent)
                .orElseGet(Collections::emptyList);
    }

    @Transactional(readOnly = true)
    public Collection<Interview> findAllOrderByIdDesc() {
        return interviewRepository.findAllByOrderByIdDesc();
    }
}
