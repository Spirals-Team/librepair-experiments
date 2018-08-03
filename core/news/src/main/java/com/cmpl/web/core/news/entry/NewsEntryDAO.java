package com.cmpl.web.core.news.entry;

import java.util.List;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.NewsEntry;

public interface NewsEntryDAO extends BaseDAO<NewsEntry> {

  List<NewsEntry> findByFacebookId(String facebookId);
}
