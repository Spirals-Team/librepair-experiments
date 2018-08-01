package net.posesor.query.paymenttitles;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface PaymentTitleRepository extends MongoRepository<PaymentTitleEntry, String> {
    Stream<PaymentTitleEntry> findByPrincipalNameAndNameLike(String principalName, String username);
}

