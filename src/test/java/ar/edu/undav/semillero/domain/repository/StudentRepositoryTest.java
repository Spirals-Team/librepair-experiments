package ar.edu.undav.semillero.domain.repository;

import ar.edu.undav.semillero.domain.entity.Student;
import ar.edu.undav.semillero.domain.entity.Interview;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private NodeRepository nodeRepository;

    @Test
    public void testSave() {
        nodeRepository.findById(1001L)
                .flatMap(node -> companyRepository.findById(1001L).map(company -> Pair.of(node, company)))
                .map(pair -> {
                    Student student = new Student("Daniel", pair.getFirst());
                    student.addInterview(new Interview(student, pair.getSecond(), "no comments"));
                    return studentRepository.save(student);
                })
                .orElseThrow(RuntimeException::new);
    }

    @Test
    public void testSoftDeleteById() {
        Student studentBefore = studentRepository.getOne(1001L);
        Assertions.assertThat(studentBefore.isDeleted()).isFalse();
        int rowCount = studentRepository.softDeleteById(1001L);
        Assertions.assertThat(rowCount).isEqualTo(1);
        Student studentAfter = studentRepository.getOne(1001L);
        Assertions.assertThat(studentAfter.isDeleted()).isFalse();
    }

    @Test
    public void testSoftDeleteByIdNotFound() {
        int rowCount = studentRepository.softDeleteById(999L);
        Assertions.assertThat(rowCount).isEqualTo(0);
    }
}
