package com.cmpl.web.core.website;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.Website;

@Repository
public interface WebsiteRepository extends BaseRepository<Website> {

  Website findByName(String websiteName);

}
