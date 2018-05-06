package io.apicollab.server.repository;

import io.apicollab.server.constant.ApiStatus;
import io.apicollab.server.domain.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApiRepository extends JpaRepository<Api, String> {

    Optional<Api> findByApplicationIdAndNameAndVersion(String applicationId, String name, String version);

    Collection<Api> findByApplicationId(String applicationId);

    @Query("FROM Api WHERE status in (:statusCodes)")
    Collection<Api> getAllByStatus(@Param("statusCodes") List<ApiStatus> statusCodes);
}
