package com.revature.hydra.trainer.repo;

import com.revature.hydra.trainer.model.Tier;
import com.revature.hydra.trainer.model.Trainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TrainerRepo extends CrudRepository<Trainer, Integer> {
    Set<Trainer> findByTier(@Param("tier") Tier tier);
}
