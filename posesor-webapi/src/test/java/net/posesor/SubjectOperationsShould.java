package net.posesor;

import lombok.val;
import net.posesor.allocations.MongoTestConfig;
import net.posesor.subjects.SubjectOperations;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MongoTestConfig.class})
public class SubjectOperationsShould {

  @Autowired
  MongoOperations operations;

  @Test
  public void shouldInsertNewSubject() {
    val randomName = "Subject " + UUID.randomUUID().toString();
    val repo = new SubjectOperations(operations, UUID.randomUUID().toString());
    val result = repo.getOrAdd(randomName);

    Assertions.assertThat(result.getSubjectId()).isNotNull();
  }

  /**
   * SubjectOperations#getOrAdd returns existing model or create new one with required name.
   */
  @Test
  public void shouldReturnExistingSubject() {
    val randomName = "Subject " + UUID.randomUUID().toString();
    val repo = new SubjectOperations(operations, UUID.randomUUID().toString());
    val expected = repo.getOrAdd(randomName);

    val actual = repo.getOrAdd(randomName);

    Assertions.assertThat(actual.getSubjectId()).isEqualTo(expected.getSubjectId());
  }

  @Test
  public void shouldDistinguishSubjectWithSameNamesOwnedByDifferentPrincipals() {
    val randomName = "Subject " + UUID.randomUUID().toString();
    val repo1 = new SubjectOperations(operations, UUID.randomUUID().toString());
    val repo2 = new SubjectOperations(operations, UUID.randomUUID().toString());

    val subject1 = repo1.getOrAdd(randomName);
    val subject2 = repo2.getOrAdd(randomName);

    Assertions.assertThat(subject1.getSubjectId()).isNotEqualTo(subject2.getSubjectId());
  }

}
