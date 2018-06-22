package com.hashmapinc.haf.repository;

import com.hashmapinc.haf.entity.UserCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentialsEntity, String> {

    UserCredentialsEntity findByUserId(String userId);

    UserCredentialsEntity findByActivationToken(String activationToken);

    UserCredentialsEntity findByResetToken(String resetToken);
}
