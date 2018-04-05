package com.almundo.test.callcenter.repositories;

import com.almundo.test.callcenter.entities.Call;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.H2;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE, connection = H2)
public class CallRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CallRepository repository;

    @Test
    public void should_store_a_call() {
        // given
        Call call = new Call();
        entityManager.persist(call);

        // when
        Call found = repository.getOne(call.getId());

        // then
        assertThat(found.getCreatedDate()).isEqualTo(call.getCreatedDate());
        assertThat(found.getId()).isOfAnyClassIn(Long.class);
        assertThat(found.getId()).isNotNull();
    }

    @Test
    public void should_find_all_calls() {
        Call call1 = new Call();
        entityManager.persist(call1);

        Call call2 = new Call();
        entityManager.persist(call2);

        Call call3 = new Call();
        entityManager.persist(call3);

        List<Call> customers = repository.findAll();

        assertThat(customers).hasSize(3).contains(call1, call2, call3);
    }

    @Test
    public void should_find_all_unanswered_calls_if_all_have_no_been_answered() {
        Call call = new Call();
        entityManager.persist(call);

        List<Call> calls = repository.findAllUnanswered();

        assertThat(calls).hasSize(1).contains(call);
    }

    @Test
    public void should_find_no_unanswered_calls_if_all_have_been_answered() {
        Call call = new Call();
        call.setAnsweredDate(new Date());
        entityManager.persist(call);

        List<Call> calls = repository.findAllUnanswered();

        assertThat(calls).hasSize(0);
    }

    @Test
    public void should_find_all_in_progress_calls_if_all_have_no_been_finished() {
        Call call = new Call();
        call.setAnsweredDate(new Date());
        entityManager.persist(call);

        List<Call> calls = repository.findAllInProgress();

        assertThat(calls).hasSize(1).contains(call);
    }

    @Test
    public void should_find_no_in_progress_calls_if_all_have_been_finished() {
        Call call = new Call();
        call.setAnsweredDate(new Date());
        call.setFinishedDate(new Date());
        entityManager.persist(call);

        List<Call> calls = repository.findAllInProgress();

        assertThat(calls).hasSize(0);
    }

    @Test
    public void should_find_all_finished_calls_if_all_have_been_finished() {
        Call call = new Call();
        call.setAnsweredDate(new Date());
        call.setFinishedDate(new Date());
        entityManager.persist(call);

        List<Call> calls = repository.findAllFinished();

        assertThat(calls).hasSize(1).contains(call);
    }

    @Test
    public void should_find_no_finished_calls_if_all_have_no_been_finished() {
        Call call = new Call();
        call.setFinishedDate(new Date());
        entityManager.persist(call);

        List<Call> calls = repository.findAllFinished();

        assertThat(calls).hasSize(0);
    }

}
