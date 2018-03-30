package com.codesquad.library.domain.repositories;

import com.codesquad.library.domain.Featured;
import org.springframework.data.repository.CrudRepository;

public interface FeaturedRepository extends CrudRepository<Featured, Long> {
}
