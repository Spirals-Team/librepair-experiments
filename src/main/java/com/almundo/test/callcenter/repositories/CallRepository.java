package com.almundo.test.callcenter.repositories;

import com.almundo.test.callcenter.entities.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CallRepository extends JpaRepository<Call, Long> {

    @Query(value = "SELECT c FROM Call c WHERE c.finishedDate IS NOT NULL AND c.answeredDate IS NOT NULL ORDER BY c.finishedDate")
    List<Call> findAllFinished();

    @Query(value = "SELECT c FROM Call c WHERE c.answeredDate IS NOT NULL AND c.finishedDate IS NULL ORDER BY c.answeredDate")
    List<Call> findAllInProgress();

    @Query(value = "SELECT c FROM Call c WHERE c.answeredDate IS NULL AND c.finishedDate IS NULL ORDER BY c.createdDate")
    List<Call> findAllUnanswered();

}

