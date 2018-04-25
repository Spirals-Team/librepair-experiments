package ar.edu.undav.semillero.service;

import ar.edu.undav.semillero.domain.entity.Node;
import ar.edu.undav.semillero.domain.entity.Student;
import ar.edu.undav.semillero.domain.repository.NodeRepository;
import ar.edu.undav.semillero.domain.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * @author Alejandro Gomez
 */
@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private NodeRepository nodeRepository;
    @Mock
    private QueryRunner queryRunner;

    @Before
    public void setUp() {
        studentService = new StudentService(studentRepository, nodeRepository, queryRunner);
    }

    @After
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(studentRepository, nodeRepository, queryRunner);
    }

    @Test
    public void testImportCSV() {
        Mockito.when(nodeRepository.findByName(Mockito.eq("Capital"))).thenReturn(Optional.of(new Node("Capital", "address")));
        Mockito.when(nodeRepository.findByName(Mockito.eq("Paternal"))).thenReturn(Optional.empty());
        Resource resource = new DefaultResourceLoader().getResource("classpath:egresados.csv");
        studentService.importCSV(resource);
        Mockito.verify(nodeRepository, Mockito.times(51)).findByName(Mockito.eq("Capital"));
        Mockito.verify(nodeRepository).findByName(Mockito.eq("Paternal"));
        ArgumentCaptor<Iterable<Student>> captor = ArgumentCaptor.forClass(Iterable.class);
        Mockito.verify(studentRepository).saveAll(captor.capture());
        Assertions.assertThat(captor.getValue()).hasSize(51);
        long graduatedCount = StreamSupport.stream(captor.getValue().spliterator(), true).filter(s -> Objects.nonNull(s.getGraduationDate())).count();
        Assertions.assertThat(graduatedCount).isEqualTo(18);
    }
}
