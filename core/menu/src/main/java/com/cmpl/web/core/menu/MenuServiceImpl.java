package com.cmpl.web.core.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.cmpl.web.core.common.service.BaseServiceImpl;

/**
 * Service du menu
 * 
 * @author Louis
 *
 */
@CacheConfig(cacheNames = "menus")
public class MenuServiceImpl extends BaseServiceImpl<MenuDTO, Menu> implements MenuService {

  private final MenuRepository menuRepository;

  public MenuServiceImpl(ApplicationEventPublisher publisher, MenuRepository menuRepository) {
    super(menuRepository, publisher);
    this.menuRepository = menuRepository;
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
  protected MenuDTO toDTO(Menu entity) {
    MenuDTO dto = new MenuDTO();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected Menu toEntity(MenuDTO dto) {
    Menu entity = new Menu();
    fillObject(dto, entity);
    return entity;
  }

  @Override
  @Cacheable(value = "listedMenus")
  public List<MenuDTO> getMenus() {
    return toListDTO(menuRepository.findAll(new Sort(Direction.ASC, "orderInMenu")));
  }

  @Override
  public List<MenuDTO> toListDTO(List<Menu> entities) {
    return computeMenus(entities);
  }

  MenuDTO computeMenuDTOToReturn(Menu menu) {
    MenuDTO menuDTO = toDTO(menu);

    List<Menu> children = menuRepository.findByParentId(String.valueOf(menu.getId()));
    menuDTO.setChildren(computeMenus(children));

    return menuDTO;
  }

  List<MenuDTO> computeMenus(List<Menu> entities) {
    List<MenuDTO> menus = new ArrayList<>();
    entities.forEach(entity -> menus.add(computeMenuDTOToReturn(entity)));
    return menus;
  }

}
