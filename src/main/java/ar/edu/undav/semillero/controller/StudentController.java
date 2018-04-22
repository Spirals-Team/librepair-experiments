package ar.edu.undav.semillero.controller;

import ar.edu.undav.semillero.domain.entity.Student;
import ar.edu.undav.semillero.dto.StudentDTO;
import ar.edu.undav.semillero.request.CreateStudentRequest;
import ar.edu.undav.semillero.request.FilterStudentsRequest;
import ar.edu.undav.semillero.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Guardar un graduado
    @PostMapping
    public Student saveStudent(@Valid @RequestBody CreateStudentRequest request) {
        return studentService.save(request);
    }

    // Obtener graduados por ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        return WebUtils.emptyToNotFound(studentService.findById(id));
    }

    // Eliminar un graduado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        return studentService.deleteById(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Obtener todos los graduados
    @GetMapping
    public Page<StudentDTO> list(@Valid FilterStudentsRequest request, Pageable pageable) {
       return studentService.list(request, pageable);
    }

    @PostMapping("/bulk")
    public ResponseEntity<Void> create(@RequestPart("csv") MultipartFile csvFile) {
        studentService.importCSV(csvFile);
        return ResponseEntity.accepted().build();
    }
}
