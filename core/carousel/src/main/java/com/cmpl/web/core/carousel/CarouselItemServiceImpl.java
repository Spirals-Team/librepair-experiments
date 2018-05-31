package com.cmpl.web.core.carousel;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.media.MediaService;

@CacheConfig(cacheNames = "carouselItems")
public class CarouselItemServiceImpl extends BaseServiceImpl<CarouselItemDTO, CarouselItem> implements
    CarouselItemService {

  private final CarouselItemRepository carouselItemRepository;
  private final MediaService mediaService;

  public CarouselItemServiceImpl(ApplicationEventPublisher publisher, CarouselItemRepository carouselItemRepository,
      MediaService mediaService) {
    super(carouselItemRepository, publisher);
    this.carouselItemRepository = carouselItemRepository;
    this.mediaService = mediaService;
  }

  @Override
  @Cacheable(value = "forCarousel", key = "#a0")
  public List<CarouselItemDTO> getByCarouselId(String carouselId) {
    return toListDTO(carouselItemRepository.findByCarouselIdOrderByOrderInCarousel(carouselId));
  }

  @Override
  protected CarouselItemDTO toDTO(CarouselItem entity) {
    CarouselItemDTO dto = CarouselItemDTOBuilder.create()
        .media(mediaService.getEntity(Long.valueOf(entity.getMediaId()))).build();
    fillObject(entity, dto);

    return dto;
  }

  @Override
  protected CarouselItem toEntity(CarouselItemDTO dto) {
    CarouselItem entity = CarouselItemBuilder.create().mediaId(String.valueOf(dto.getMedia().getId())).build();

    fillObject(entity, dto);

    return entity;
  }

  @Override
  @CacheEvict(value = "forCarousel", key = "#a0.carouselId")
  public CarouselItemDTO createEntity(CarouselItemDTO dto) {

    CarouselItem carouselItem = CarouselItemBuilder.create().mediaId(String.valueOf(dto.getMedia().getId())).build();
    fillObject(dto, carouselItem);

    return toDTO(carouselItemRepository.save(carouselItem));
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
