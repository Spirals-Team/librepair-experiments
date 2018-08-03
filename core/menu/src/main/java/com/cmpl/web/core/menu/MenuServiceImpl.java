package com.cmpl.web.core.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.Menu;

/**
 * Service du menu
 * 
 * @author Louis
 *
 */
@CacheConfig(cacheNames = "menus")
public class MenuServiceImpl extends BaseServiceImpl<MenuDTO, Menu> implements MenuService {

  private final MenuDAO menuDAO;

  public MenuServiceImpl(MenuDAO menuDAO, MenuMapper menuMapper) {
    super(menuDAO, menuMapper);
    this.menuDAO = menuDAO;
  }

  @Override
  @CacheEvict(value = {"pagedMenus", "listedMenus"}, allEntries = true)
  public MenuDTO createEntity(MenuDTO dto) {
    return super.createEntity(dto);
  }

  @Override
  @Cacheable(key = "#a0")
  public MenuDTO getEntity(Long id) {
    return super.getEntity(id);
  }

  @Override
  @CachePut(key = "#a0.id")
  public MenuDTO updateEntity(MenuDTO dto) {
    return super.updateEntity(dto);
  }

  @Override
  @Cacheable(value = "pagedMenus")
  public Page<MenuDTO> getPagedEntities(PageRequest pageRequest) {
    return super.getPagedEntities(pageRequest);
  }

  @Override
  @Cacheable(value = "listedMenus")
  public List<MenuDTO> getMenus() {
    return computeMenus(menuDAO.getMenus(new Sort(Direction.ASC, "orderInMenu")));
  }

  MenuDTO computeMenuDTOToReturn(Menu menu) {
    MenuDTO menuDTO = mapper.toDTO(menu);

    List<Menu> children = menuDAO.findByParentId(String.valueOf(menu.getId()));
    menuDTO.setChildren(computeMenus(children));

    return menuDTO;
  }

  List<MenuDTO> computeMenus(List<Menu> entities) {
    List<MenuDTO> menus = new ArrayList<>();
    entities.forEach(entity -> menus.add(computeMenuDTOToReturn(entity)));
    return menus;
  }

}
