package com.cmpl.web.core.news;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.service.BaseServiceImpl;

/**
 * Implementation de l'interface pour la gestion des NewsEntry
 * 
 * @author Louis
 *
 */
@CacheConfig(cacheNames = "newsEntries")
public class NewsEntryServiceImpl extends BaseServiceImpl<NewsEntryDTO, NewsEntry> implements NewsEntryService {

  private static final Logger LOGGER = LoggerFactory.getLogger(NewsEntryServiceImpl.class);

  private final NewsEntryRepository newsEntryRepository;
  private final NewsImageService newsImageService;
  private final NewsContentService newsContentService;

  public NewsEntryServiceImpl(ApplicationEventPublisher publisher, NewsEntryRepository newsEntryRepository,
      NewsImageService newsImageService, NewsContentService newsContentService) {
    super(newsEntryRepository, publisher);
    this.newsEntryRepository = newsEntryRepository;
    this.newsImageService = newsImageService;
    this.newsContentService = newsContentService;
  }

  @Override
  @Transactional
  @CacheEvict(value = {"listedNewsEntries", "pagedNewsEntries"}, allEntries = true)
  public NewsEntryDTO createEntity(NewsEntryDTO dto) {

    LOGGER.info("Creation d'une nouvelle entrée de blog");

    NewsEntry entry = new NewsEntry();
    fillObject(dto, entry);

    String contentId = createContent(dto.getNewsContent());
    entry.setContentId(contentId);

    String imageId = createImage(dto.getNewsImage());
    entry.setImageId(imageId);

    return toDTO(newsEntryRepository.save(entry));
  }

  String createContent(NewsContentDTO contentToCreate) {
    String contentId = "";
    if (contentToCreate != null) {
      contentId = String.valueOf(newsContentService.createEntity(contentToCreate).getId());
    }
    return contentId;
  }

  String createImage(NewsImageDTO imageToCreate) {
    String imageId = "";
    if (imageToCreate != null) {
      return String.valueOf(newsImageService.createEntity(imageToCreate).getId());

    }
    return imageId;
  }

  @Override
  @Transactional
  @CachePut(key = "#a0.id")
  @CacheEvict(value = {"listedNewsEntries", "pagedNewsEntries"}, allEntries = true)
  public NewsEntryDTO updateEntity(NewsEntryDTO dto) {

    LOGGER.info("Mise à jour d'une entrée de blog d'id " + dto.getId());
    NewsEntry entry = new NewsEntry();
    fillObject(dto, entry);

    NewsContentDTO contentToUpdate = updateContent(dto.getNewsContent());
    String contentId = contentToUpdate == null ? "" : String.valueOf(contentToUpdate.getId());
    entry.setContentId(contentId);

    NewsImageDTO imageToUpdate = updateImage(dto.getNewsImage());
    String imageId = imageToUpdate == null ? "" : String.valueOf(imageToUpdate.getId());
    entry.setImageId(imageId);

    NewsEntryDTO dtoUpdated = toDTO(newsEntryRepository.save(entry));
    dtoUpdated.setNewsContent(contentToUpdate);
    dtoUpdated.setNewsImage(imageToUpdate);

    return dtoUpdated;
  }

  NewsContentDTO updateContent(NewsContentDTO contentToUpdate) {
    if (contentToUpdate != null) {
      return dealWithContentToUpdate(contentToUpdate);
    }
    return contentToUpdate;
  }

  NewsImageDTO updateImage(NewsImageDTO imageToUpdate) {
    if (imageToUpdate != null) {
      return dealWithImageToUpdate(imageToUpdate);
    }
    return imageToUpdate;
  }

  NewsContentDTO dealWithContentToUpdate(NewsContentDTO contentToUpdate) {
    NewsContentDTO contentSaved;
    Long contentId = contentToUpdate.getId();
    if (contentId == null) {
      contentSaved = newsContentService.createEntity(contentToUpdate);
    } else {
      contentSaved = newsContentService.updateEntity(contentToUpdate);
    }
    return contentSaved;
  }

  NewsImageDTO dealWithImageToUpdate(NewsImageDTO imageToUpdate) {
    Long imageToUpdateId = imageToUpdate.getId();
    if (imageToUpdateId == null) {
      return newsImageService.createEntity(imageToUpdate);
    } else {
      return newsImageService.updateEntity(imageToUpdate);
    }

  }

  @Override
  @Cacheable(key = "#a0")
  public NewsEntryDTO getEntity(Long id) {
    LOGGER.info("Récupération de l'entrée de blog d'id " + id);
    NewsEntry entry = newsEntryRepository.findById(id).get();
    return toDTO(entry);
  }

  @Override
  @Cacheable(value = "listedNewsEntries")
  public List<NewsEntryDTO> getEntities() {

    LOGGER.info("Récupération de toutes les entrées de blog");
    List<NewsEntryDTO> entries = new ArrayList<>();
    List<NewsEntry> newsEntries = newsEntryRepository.findAll();

    newsEntries.forEach(newsEntry -> entries.add(toDTO(newsEntry)));

    return entries;
  }

  @Override
  protected Page<NewsEntryDTO> toPageDTO(Page<NewsEntry> pagedNewsEntries, PageRequest pageRequest) {

    List<NewsEntryDTO> dtos = new ArrayList<>();

    pagedNewsEntries.getContent().forEach(entity -> dtos.add(toDTO(entity)));

    return new PageImpl<>(dtos, pageRequest, pagedNewsEntries.getTotalElements());

  }

  @Override
  protected NewsEntryDTO toDTO(NewsEntry entity) {

    NewsEntryDTO dto = new NewsEntryDTO();
    fillObject(entity, dto);

    if (StringUtils.hasText(entity.getContentId())) {
      dto.setNewsContent(newsContentService.getEntity(Long.parseLong(entity.getContentId())));
    }

    if (StringUtils.hasText(entity.getImageId())) {
      dto.setNewsImage(newsImageService.getEntity(Long.parseLong(entity.getImageId())));
    }

    dto.setName(entity.getTitle());

    return dto;
  }

  @Override
  protected NewsEntry toEntity(NewsEntryDTO dto) {

    NewsEntry entity = new NewsEntry();
    fillObject(dto, entity);

    if (dto.getNewsContent() != null) {
      entity.setContentId(String.valueOf(dto.getNewsContent().getId()));
    }

    if (dto.getNewsImage() != null) {
      entity.setImageId(String.valueOf(dto.getNewsImage().getId()));
    }

    return entity;
  }

  @Override
  public boolean isAlreadyImportedFromFacebook(String facebookId) {
    return !CollectionUtils.isEmpty(newsEntryRepository.findByFacebookId(facebookId));
  }

  @Override
  @Cacheable(value = "pagedNewsEntries")
  public Page<NewsEntryDTO> getPagedEntities(PageRequest pageRequest) {
    return super.getPagedEntities(pageRequest);
  }

}
