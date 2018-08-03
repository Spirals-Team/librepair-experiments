package com.cmpl.web.core.menu;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.Menu;

public class MenuDAOImpl extends BaseDAOImpl<Menu> implements MenuDAO {

  private final MenuRepository menuRepository;

  public MenuDAOImpl(MenuRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Menu.class, entityRepository, publisher);
    this.menuRepository = entityRepository;
  }

  @Override
  public List<Menu> getMenus(Sort sort) {
    return menuRepository.findAll(sort);
  }

  @Override
  public List<Menu> findByParentId(String parentId) {
    return menuRepository.findByParentId(parentId);
  }

}
