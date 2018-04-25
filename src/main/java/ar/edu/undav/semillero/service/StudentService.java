package ar.edu.undav.semillero.service;

import ar.edu.undav.semillero.domain.entity.Node;
import ar.edu.undav.semillero.domain.entity.Student;
import ar.edu.undav.semillero.domain.repository.NodeRepository;
import ar.edu.undav.semillero.domain.repository.StudentRepository;
import ar.edu.undav.semillero.dto.StudentDTO;
import ar.edu.undav.semillero.request.CreateStudentRequest;
import ar.edu.undav.semillero.request.FilterStudentsRequest;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;
    private final NodeRepository nodeRepository;
    private final QueryRunner queryRunner;

    public StudentService(StudentRepository studentRepository, NodeRepository nodeRepository, QueryRunner queryRunner) {
        this.studentRepository = studentRepository;
        this.nodeRepository = nodeRepository;
        this.queryRunner = queryRunner;
    }

    @Transactional
    public Student save(CreateStudentRequest request) {
        return nodeRepository.findById(request.getNode())
                .map(node -> studentRepository.save(
                        new Student(request.getName(),
                            request.getLastName(),
                            request.getCourseDate(),
                            request.getGraduationDate(),
                            node,
                            request.getEmail(),
                            request.getPhone(),
                            request.getResumeUrl(),
                            request.isLookingForWork(),
                            request.isWorking(),
                            request.getFeedback())))
                .orElseThrow(RuntimeException::new);
    }

    @Transactional
    public boolean deleteById(long id) {
        return studentRepository.softDeleteById(id) == 1;
    }

    @Transactional(readOnly = true)
    public Collection<Student> findAll() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<StudentDTO> list(FilterStudentsRequest filters, Pageable pageable) {
        QueryBuilder<StudentDTO> queryBuilder = QueryBuilder.of(StudentDTO.class)
                    .select("new ar.edu.undav.semillero.dto.StudentDTO(s.id, s.name, s.lastName, s.node.name, s.deleted, " +
                        "s.courseDate, s.graduationDate, s.email, s.phone, s.resumeUrl, s.lookingForWork, s.working, s.feedback, " +
                         " (select count(i) from ar.edu.undav.semillero.domain.entity.Interview i where i.student = s))")
                    .from(Student.class, "s")
                    .gte("graduationDate", filters.getMinGraduationDate())
                    .lte("graduationDate", filters.getMaxGraduationDate())
                    .eq("node.id", filters.getNode())
                    .notNull(filters.isGraduated(), "graduationDate")
                    .condition(filters.isWorking(), "working = true")
                    .condition(filters.isLookingForWork(), "lookingForWork = true");
        return queryRunner.run(StudentDTO.class, queryBuilder, pageable);
    }

    @Transactional
    public void importCSV(InputStreamSource csvResource) {
        try (Reader reader = new InputStreamReader(csvResource.getInputStream())) {
            CSVParser records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            List<Student> students = StreamSupport.stream(records.spliterator(), true)
                    .map(record -> Pair.of(record, nodeRepository.findByName(record.get("nodo"))))
                    .map(this::pairToStudent)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            studentRepository.saveAll(students);
        } catch (IOException ex) {
            LOGGER.error("Error with stream", ex);
            throw new RuntimeException("Error importing CSV", ex);
        }
    }

    private Student pairToStudent(Pair<CSVRecord, Optional<Node>> pair) {
        return pair.getSecond().map(node -> {
            CSVRecord record = pair.getFirst();
            boolean egresado = "Si".equalsIgnoreCase(record.get("egresado"));
            LocalDate date = extractDate(record);
            return new Student(record.get("nombre"), record.get("apellido"), date, egresado ? date : null, node, record.get("mail"), record.get("celular"), null, Boolean.FALSE, Boolean.FALSE, null);
        }).orElse(null);
    }

    private LocalDate extractDate(CSVRecord record) {
        String fecha = record.get("fecha");
        if (StringUtils.isEmpty(fecha)) {
            return null;
        } else if (fecha.endsWith("-1")) {
            return LocalDate.of(NumberUtils.toInt(fecha.substring(0, 4)), Month.JUNE, 30);
        } else {
            return LocalDate.of(NumberUtils.toInt(fecha.substring(0, 4)), Month.DECEMBER, 31);
        }
    }

    @Transactional
    public Optional<Student> update(long id, @Valid CreateStudentRequest request) {
        Optional<Student> optStudent = studentRepository.findById(id);
        optStudent.ifPresent(s -> {
            s.setEmail(request.getEmail());
            s.setLastName(request.getLastName());
            s.setLookingForWork(request.isLookingForWork());
            s.setResumeUrl(request.getResumeUrl());
            s.setName(request.getName());
            s.setCourseDate(request.getCourseDate());
            s.setGraduationDate(request.getGraduationDate());
            s.setFeedback(request.getFeedback());
            if (!s.getNode().getId().equals(request.getNode())){
                Optional<Node> optNode = nodeRepository.findById(request.getNode());
                if (optNode.isPresent()){
                    s.setNode(optNode.get());
                }else{
                    throw new RuntimeException();
                }
            }
        });
        return optStudent;
    }
}
