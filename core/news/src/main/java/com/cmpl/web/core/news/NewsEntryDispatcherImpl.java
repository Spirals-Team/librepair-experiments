package com.cmpl.web.core.news;

import java.io.IOException;
import java.util.Locale;

import org.springframework.web.multipart.MultipartFile;

import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaDTOBuilder;
import com.cmpl.web.core.media.MediaService;

/**
 * Implementation du dispatcher pour les NewsEntry
 * 
 * @author Louis
 *
 */
public class NewsEntryDispatcherImpl implements NewsEntryDispatcher {

  private final NewsEntryRequestValidator validator;
  private final NewsEntryTranslator translator;
  private final NewsEntryService newsEntryService;
  private final FileService fileService;
  private final MediaService mediaService;

  private static final String MEDIA_CONTROLLER_PATH = "/public/medias/";

  public NewsEntryDispatcherImpl(NewsEntryRequestValidator validator, NewsEntryTranslator translator,
      NewsEntryService newsEntryService, FileService fileService, MediaService mediaService) {
    this.validator = validator;
    this.translator = translator;
    this.newsEntryService = newsEntryService;
    this.fileService = fileService;
    this.mediaService = mediaService;
  }

  @Override
  public NewsEntryResponse createEntity(NewsEntryRequest newsEntryRequest, Locale locale) {

    Error error = validator.validateCreate(newsEntryRequest, locale);

    if (error != null) {
      return NewsEntryResponseBuilder.create().error(error).build();
    }

    NewsEntryDTO entityToCreate = translator.fromRequestToDTO(newsEntryRequest);
    NewsEntryDTO entityCreated = newsEntryService.createEntity(entityToCreate);

    return translator.fromDTOToResponse(entityCreated);
  }

  @Override
  public NewsEntryResponse updateEntity(NewsEntryRequest newsEntryRequest, String newsEntryId, Locale locale) {

    Error error = validator.validateUpdate(newsEntryRequest, newsEntryId, locale);

    if (error != null) {
      return NewsEntryResponseBuilder.create().error(error).build();
    }

    newsEntryRequest.setId(Long.parseLong(newsEntryId));
    NewsEntryDTO entityToUpdate = translator.fromRequestToDTO(newsEntryRequest);

    NewsEntryDTO entityUpdated = newsEntryService.updateEntity(entityToUpdate);

    return translator.fromDTOToResponse(entityUpdated);

  }

  @Override
  public NewsEntryResponse deleteEntity(String newsEntryId, Locale locale) {
    Error error = validator.validateDelete(newsEntryId, locale);
    if (error != null) {
      return NewsEntryResponseBuilder.create().error(error).build();
    }
    newsEntryService.deleteEntity(Long.parseLong(newsEntryId));
    return NewsEntryResponseBuilder.create().build();
  }

  @Override
  public void saveNewsMedia(String newsEntryId, MultipartFile uploadedMedia) throws IOException {

    String extension = uploadedMedia.getOriginalFilename().split("\\.")[1];
    if (extension != null) {
      extension = extension.toLowerCase();
    }
    String fileName = "image_" + newsEntryId + "." + extension;

    fileService.saveMediaOnSystem(fileName, uploadedMedia.getBytes());

    MediaDTO mediaToSave = mediaService.findByName(fileName);
    MediaDTO mediaSaved;
    if (mediaToSave == null) {
      mediaToSave = MediaDTOBuilder.create().name(fileName).contentType(uploadedMedia.getContentType())
          .extension(extension).size(uploadedMedia.getSize()).src(MEDIA_CONTROLLER_PATH + fileName).build();
      mediaSaved = mediaService.createEntity(mediaToSave);
    } else {
      mediaToSave.setContentType(uploadedMedia.getContentType());
      mediaToSave.setExtension(extension);
      mediaToSave.setSize(uploadedMedia.getSize());
      mediaSaved = mediaService.updateEntity(mediaToSave);

    }

    NewsEntryDTO newsEntryToUpdate = newsEntryService.getEntity(Long.parseLong(newsEntryId));
    newsEntryToUpdate.getNewsImage().setMedia(mediaSaved);
    newsEntryService.updateEntity(newsEntryToUpdate);

  }

}
