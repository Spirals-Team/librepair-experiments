package com.cmpl.web.core.widget;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.Widget;

@Repository
public interface WidgetRepository extends BaseRepository<Widget> {

  Widget findByName(String widgetName);
}
