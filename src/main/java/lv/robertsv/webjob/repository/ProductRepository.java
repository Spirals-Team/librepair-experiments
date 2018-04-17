package lv.robertsv.webjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.robertsv.webjob.domain.JobEntity;

public interface ProductRepository extends JpaRepository<JobEntity, Long>{
}