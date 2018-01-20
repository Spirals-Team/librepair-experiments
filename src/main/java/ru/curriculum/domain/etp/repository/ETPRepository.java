package ru.curriculum.domain.etp.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.curriculum.domain.etp.entity.ETP;

public interface ETPRepository extends PagingAndSortingRepository<ETP, Integer> {
}
