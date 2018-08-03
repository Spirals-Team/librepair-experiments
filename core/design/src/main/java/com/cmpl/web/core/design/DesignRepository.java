package com.cmpl.web.core.design;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.Design;

@Repository
public interface DesignRepository extends BaseRepository<Design> {

  List<Design> findByWebsiteId(Long websiteId);

  List<Design> findByStyleId(Long styleId);

  Design findByWebsiteIdAndStyleId(Long websiteId, Long styleId);

}
