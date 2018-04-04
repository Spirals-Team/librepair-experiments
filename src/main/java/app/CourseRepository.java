package app;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Faisal on 2018-03-20.
 */


@RepositoryRestResource(collectionResourceRel="courses", path ="courses")
public interface CourseRepository extends CrudRepository<Course, Long> {
        List<Course> findAll();
        List<Course> findByName(String name);
        List<Course> findByProgramsIn(List<Program> program);
        List<Course> findByYear(AcademicYear year);
}

