package apna.Maholla.repository;

import apna.Maholla.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<Verification, Integer> {

    Verification findFirstByUserid(String UserId);
}
