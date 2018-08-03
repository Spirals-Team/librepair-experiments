package com.cmpl.web.core.website;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Website;

public interface WebsiteDAO extends BaseDAO<Website> {

  Website getWebsiteByName(String websiteName);
}
