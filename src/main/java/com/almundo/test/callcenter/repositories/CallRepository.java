package com.almundo.test.callcenter.repositories;

import com.almundo.test.callcenter.entities.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CallRepository extends JpaRepository<Call, Long> {

    @Query(value = "SELECT c FROM Call c WHERE c.employee IS NULL")
    List<Call> findAllUnanswered();

}

