package ar.edu.undav.semillero.domain.repository;

import ar.edu.undav.semillero.domain.entity.Student;
import ar.edu.undav.semillero.domain.entity.Node;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class NodeRepositoryTest {

    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testSave() {
        Node node = new Node("Bariloche", "Calle Verdadera 123");
        Student student = new Student("Daniel", node);
        node.addStudent(student);
        nodeRepository.save(node);
        studentRepository.save(student);
    }
}
