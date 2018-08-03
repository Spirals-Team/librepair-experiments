package com.cmpl.web.core.style;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Style;

public interface StyleDAO extends BaseDAO<Style> {

  Style getStyle();

}
