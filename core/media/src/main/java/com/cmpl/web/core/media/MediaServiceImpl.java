package com.cmpl.web.core.media;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.file.FileService;

@CacheConfig(cacheNames = "medias")
public class MediaServiceImpl extends BaseServiceImpl<MediaDTO, Media> implements MediaService {

  private final FileService fileService;
  private final MediaRepository mediaRepository;
  private static final String MEDIA_CONTROLLER_PATH = "/public/medias/";

  public MediaServiceImpl(ApplicationEventPublisher publisher, MediaRepository entityRepository, FileService fileService) {
    super(entityRepository, publisher);
    this.fileService = fileService;
    this.mediaRepository = entityRepository;
  }

  @Override
  @Transactional
  @CacheEvict(value = "pagedMedias", allEntries = true)
  public MediaDTO upload(MultipartFile multipartFile) throws SQLException, IOException {

    String fileName = multipartFile.getOriginalFilename();
    String extension = fileName.split("\\.")[1];
    if (extension != null) {
      extension = extension.toLowerCase();
    }

    MediaDTO mediaToCreate = MediaDTOBuilder.create().name(fileName).contentType(multipartFile.getContentType())
        .extension(extension).size(multipartFile.getSize()).src(MEDIA_CONTROLLER_PATH + fileName).build();

    fileService.saveMediaOnSystem(fileName, multipartFile.getBytes());

    return createEntity(mediaToCreate);
  }

  @Override
  @Transactional
  @CacheEvict(value = "pagedMedias", allEntries = true)
  public MediaDTO createEntity(MediaDTO dto) {
    return super.createEntity(dto);
  }

  @Override
  public InputStream download(String mediaName) {
    return fileService.read(mediaName);
  }

  @Override
  protected MediaDTO toDTO(Media entity) {
    if (entity == null) {
      return null;
    }
    MediaDTO dto = MediaDTOBuilder.create().build();

    fillObject(entity, dto);

    return dto;
  }

  @Override
  protected Media toEntity(MediaDTO dto) {
    Media entity = MediaBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }

  @Override
  @Cacheable(key = "#a0")
  public MediaDTO getEntity(Long id) {
    return super.getEntity(id);
  }

  @Override
  @Cacheable(value = "pagedMedias")
  public Page<MediaDTO> getPagedEntities(PageRequest pageRequest) {
    return super.getPagedEntities(pageRequest);
  }

  @Override
  @Cacheable(key = "#a0", unless = "#result == null")
  public MediaDTO findByName(String name) {
    return toDTO(mediaRepository.findByName(name));
  }

}
