package ar.edu.undav.semillero.domain.repository;

import ar.edu.undav.semillero.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Modifying
    @Query("update ar.edu.undav.semillero.domain.entity.Student g set g.deleted = true where g.id = :id")
    int softDeleteById(@Param("id") long id);
}
