package app;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel="learningOutcomes", path ="learningOutcomes")
public interface LearningOutcomeRepository extends CrudRepository<LearningOutcome, Long> {
    List<LearningOutcome> findByName(String name);
    List<LearningOutcome> findByCategory (Category category);
    List<LearningOutcome> findByCourse (Course course);
}