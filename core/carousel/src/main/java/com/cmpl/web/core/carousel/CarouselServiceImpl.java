package com.cmpl.web.core.carousel;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.Carousel;

@CacheConfig(cacheNames = "carousels")
public class CarouselServiceImpl extends BaseServiceImpl<CarouselDTO, Carousel> implements CarouselService {

  public CarouselServiceImpl(CarouselDAO carouselDAO, CarouselMapper carouselMapper) {
    super(carouselDAO, carouselMapper);
  }

  @Override
  @Cacheable(key = "#a0")
  public CarouselDTO getEntity(Long id) {
    return super.getEntity(id);
  }

  @Override
  @Cacheable(value = "pagedCarousels")
  public Page<CarouselDTO> getPagedEntities(PageRequest pageRequest) {
    return super.getPagedEntities(pageRequest);
  }

  @Override
  @CacheEvict(value = "pagedCarousels", allEntries = true)
  public CarouselDTO createEntity(CarouselDTO dto) {
    return super.createEntity(dto);
  }

  @Override
  @CacheEvict(value = "pagedCarousels", allEntries = true)
  public void deleteEntity(Long id) {
    super.deleteEntity(id);
  }

}
