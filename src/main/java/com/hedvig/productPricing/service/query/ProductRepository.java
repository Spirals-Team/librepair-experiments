package com.hedvig.productPricing.service.query;

import com.hedvig.productPricing.service.aggregates.ProductStates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findById(UUID s);

    List<ProductEntity> findByMemberId(String s);

    List<ProductEntity> findByState(ProductStates state);

    @Query("from ProductEntity pe where lower(pe.member.firstName) like lower(concat('%', :query, '%')) " +
        "or lower(pe.member.lastName) like lower(concat('%', :query, '%')) " +
        "or lower(pe.member.id) like lower(concat('%', :query, '%'))")
    List<ProductEntity> findByMember(@Param("query") String query);

    @Query("from ProductEntity pe where (lower(pe.member.firstName) like lower(concat('%', :query, '%')) " +
        "or lower(pe.member.lastName) like lower(concat('%', :query, '%')) " +
        "or lower(pe.member.id) like lower(concat('%', :query, '%'))) " +
        "and pe.state = :state")
    List<ProductEntity> findByMemberAndState(@Param("query") String query, @Param("state") ProductStates states);
}
