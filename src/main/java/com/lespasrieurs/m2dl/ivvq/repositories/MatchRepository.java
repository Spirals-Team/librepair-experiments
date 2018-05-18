package com.lespasrieurs.m2dl.ivvq.repositories;

import com.lespasrieurs.m2dl.ivvq.domain.Match;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Marti_000 on 30/03/2018.
 */
public interface MatchRepository extends PagingAndSortingRepository<Match, Long> {
}
