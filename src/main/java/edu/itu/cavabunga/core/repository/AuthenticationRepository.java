package edu.itu.cavabunga.core.repository;

import edu.itu.cavabunga.core.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Long>{
    public Authentication findByAuthenticationKey(String authenticationKey);

    public Authentication findByAuthenticationType(String authenticationType);
}
