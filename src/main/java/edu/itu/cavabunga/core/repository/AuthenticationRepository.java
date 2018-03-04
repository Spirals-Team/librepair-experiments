package edu.itu.cavabunga.core.repository;

import edu.itu.cavabunga.core.entity.Authentication;
import edu.itu.cavabunga.core.entity.authentication.AuthenticationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Long>{
    public Authentication findByAuthenticationKey(String authenticationKey);

    public List<Authentication> findByAuthenticationType(AuthenticationType authenticationType);
}
