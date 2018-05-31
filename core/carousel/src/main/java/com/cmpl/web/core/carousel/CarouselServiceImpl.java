package com.cmpl.web.core.carousel;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cmpl.web.core.common.service.BaseServiceImpl;

@CacheConfig(cacheNames = "carousels")
public class CarouselServiceImpl extends BaseServiceImpl<CarouselDTO, Carousel> implements CarouselService {

  private final CarouselRepository carouselRepository;
  private final CarouselItemService carouselItemService;

  public CarouselServiceImpl(ApplicationEventPublisher publisher, CarouselRepository carouselRepository,
      CarouselItemService carouselItemService) {
    super(carouselRepository, publisher);
    this.carouselRepository = carouselRepository;
    this.carouselItemService = carouselItemService;
  }

  @Override
  protected CarouselDTO toDTO(Carousel entity) {
    CarouselDTO dto = CarouselDTOBuilder.create()
        .carouselItems(carouselItemService.getByCarouselId(String.valueOf(entity.getId()))).build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected Carousel toEntity(CarouselDTO dto) {
    Carousel carousel = CarouselBuilder.create().build();
    fillObject(dto, carousel);
    return carousel;
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
