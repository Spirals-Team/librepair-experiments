package com.cmpl.web.core.design;

import java.util.List;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Design;

public interface DesignDAO extends BaseDAO<Design> {

  List<Design> findByWebsiteId(Long websiteId);

  List<Design> findByStyleId(Long styleId);

  Design findByWebsiteIdAndStyleId(Long websiteId, Long styleId);

}
