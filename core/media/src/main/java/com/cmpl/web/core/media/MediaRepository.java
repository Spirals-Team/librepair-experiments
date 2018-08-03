package com.cmpl.web.core.media;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.Media;

@Repository
public interface MediaRepository extends BaseRepository<Media> {

  Media findByName(String name);

}
