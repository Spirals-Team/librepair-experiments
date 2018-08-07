package com.hedvig.productPricing.service.query;

import com.hedvig.productPricing.service.aggregates.ProductStates;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
  Optional<ProductEntity> findById(UUID s);

  List<ProductEntity> findByMemberId(String s);

  List<ProductEntity> findByState(ProductStates state);

  @Query(
      "from ProductEntity pe where lower(pe.member.firstName) like lower(concat('%', :query, '%')) "
          + "or lower(pe.member.lastName) like lower(concat('%', :query, '%')) "
          + "or lower(pe.member.id) like lower(concat('%', :query, '%'))")
  List<ProductEntity> findByMember(@Param("query") String query);

  @Query(
      "from ProductEntity pe where (lower(pe.member.firstName) like lower(concat('%', :query, '%')) "
          + "or lower(pe.member.lastName) like lower(concat('%', :query, '%')) "
          + "or lower(pe.member.id) like lower(concat('%', :query, '%'))) "
          + "and pe.state = :state")
  List<ProductEntity> findByMemberAndState(
      @Param("query") String query, @Param("state") ProductStates states);

  @Query("from ProductEntity pe where pe.activeTo <= :time " + "and pe.state != :state")
  List<ProductEntity> findByMemberActiveToAndNotState(
      @Param("time") LocalDateTime time, @Param("state") ProductStates states);

  @Query(
      " from ProductEntity p"
          + " where p.activeFrom is not null"
          + " and date_part ('month', p.activeFrom ) <= :month"
          + " and date_part ('year', p.activeFrom ) <= :year")
  List<ProductEntity> findBillingByDate(@Param("month") int month, @Param("year") int year);

  @Query(
      " from ProductEntity p"
          + " where p.activeFrom is not null"
          + " and date_part ('month', p.activeFrom ) <= :month"
          + " and date_part ('year', p.activeFrom ) <= :year"
          + " and p.member.id = :memberId")
  List<ProductEntity> findBillingByDateByMemberId(
      @Param("month") int month, @Param("year") int year, @Param("memberId") String memberId);

  @Query(
      "from ProductEntity p"
          + " where p.activeTo is null"
          + " and active_from::date = :activationDate::date")
  List<ProductEntity> findByActiveFromAndActiveToNull(
      @Param("activationDate") LocalDate activationDate);
}
