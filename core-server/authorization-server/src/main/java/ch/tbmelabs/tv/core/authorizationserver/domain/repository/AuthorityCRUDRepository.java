package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.Authority;

@Repository
public interface AuthorityCRUDRepository extends CrudRepository<Authority, Long> {
  Page<Authority> findAll(Pageable pageable);
}