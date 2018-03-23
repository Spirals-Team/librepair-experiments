package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.GrantType;

@Repository
public interface GrantTypeCRUDRepository extends CrudRepository<GrantType, Long> {
  Page<GrantType> findAll(Pageable pageable);
}