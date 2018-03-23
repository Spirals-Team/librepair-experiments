package ch.tbmelabs.tv.core.authorizationserver.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.domain.BlacklistedIp;

@Repository
public interface IPBlacklistCRUDRepository extends CrudRepository<BlacklistedIp, Long> {
}