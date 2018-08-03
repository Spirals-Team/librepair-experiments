package com.cmpl.web.core.media;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Objects;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.models.Media;

@CacheConfig(cacheNames = "medias")
public class MediaServiceImpl extends BaseServiceImpl<MediaDTO, Media> implements MediaService {

  private final FileService fileService;
  private final MediaDAO mediaDAO;
  private static final String MEDIA_CONTROLLER_PATH = "/public/medias/";

  public MediaServiceImpl(MediaDAO mediaDAO, MediaMapper mediaMapper, FileService fileService) {
    super(mediaDAO, mediaMapper);
    this.fileService = Objects.requireNonNull(fileService);
    this.mediaDAO = mediaDAO;
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
    return mapper.toDTO(mediaDAO.findByName(name));
  }

}
