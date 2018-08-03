package com.cmpl.web.core.carousel.item;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.CarouselItem;

@CacheConfig(cacheNames = "carouselItems")
public class CarouselItemServiceImpl extends BaseServiceImpl<CarouselItemDTO, CarouselItem>
    implements CarouselItemService {

  private final CarouselItemDAO carouselItemDAO;

  public CarouselItemServiceImpl(CarouselItemDAO carouselItemDAO, CarouselItemMapper carouselItemMapper) {
    super(carouselItemDAO, carouselItemMapper);
    this.carouselItemDAO = carouselItemDAO;
  }

  @Override
  @Cacheable(value = "forCarousel", key = "#a0")
  public List<CarouselItemDTO> getByCarouselId(String carouselId) {
    return mapper.toListDTO(carouselItemDAO.getByCarouselId(carouselId));
  }

  @Override
  @CacheEvict(value = "forCarousel", key = "#a0.carouselId")
  public CarouselItemDTO createEntity(CarouselItemDTO dto) {
    return mapper.toDTO(carouselItemDAO.createEntity(mapper.toEntity(dto)));
  }

  @Override
  @Cacheable(key = "#a0")
  public CarouselItemDTO getEntity(Long id) {
    return super.getEntity(id);
  }

  @Override
  @CacheEvict(value = "forCarousel", key = "#a0.carouselId")
  public void deleteEntityInCarousel(String carouselId, Long id) {
    deleteEntity(id);
  }

}
