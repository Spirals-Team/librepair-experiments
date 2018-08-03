package com.cmpl.web.core.media;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Media;

public interface MediaDAO extends BaseDAO<Media> {

  Media findByName(String name);

}
