package com.cmpl.web.facebook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.MediaOperations;
import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaDTOBuilder;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.news.NewsContentDTO;
import com.cmpl.web.core.news.NewsContentDTOBuilder;
import com.cmpl.web.core.news.NewsEntryDTO;
import com.cmpl.web.core.news.NewsEntryDTOBuilder;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.news.NewsImageDTO;
import com.cmpl.web.core.news.NewsImageDTOBuilder;

/**
 * Service qui sert a importer des post facebook en tant que NewsEntry
 * 
 * @author Louis
 *
 */
public class FacebookImportServiceImpl implements FacebookImportService {

  private static final Logger LOGGER = LoggerFactory.getLogger(FacebookImportServiceImpl.class);

  private static final String DATA_START = "data:";
  private static final String DATA_END = ";base64,";
  private static final String MEDIA_CONTROLLER_PATH = "/public/medias/";

  private final NewsEntryService newsEntryService;
  private final FacebookAdapter facebookAdapter;
  private final WebMessageSource messageSource;
  private final MediaService mediaService;
  private final FileService fileService;

  public FacebookImportServiceImpl(NewsEntryService newsEntryService, FacebookAdapter facebookAdapter,
      MediaService mediaService, FileService fileService, WebMessageSource messageSource) {
    this.newsEntryService = newsEntryService;
    this.facebookAdapter = facebookAdapter;
    this.mediaService = mediaService;
    this.fileService = fileService;
    this.messageSource = messageSource;
  }

  @Override
  public List<NewsEntryDTO> importFacebookPost(List<FacebookImportPost> facebookPosts, Locale locale) {
    List<NewsEntryDTO> createdEntries = new ArrayList<>();
    facebookPosts.forEach(postToImport -> {
      NewsEntryDTO createdNewsEntry = newsEntryService.createEntity(convertPostToNewsEntry(postToImport, locale));

      if (createdNewsEntry.getNewsImage() != null) {
        MediaDTO mediaFromFacebookPost = createMediaFromFacebookPost(createdNewsEntry, postToImport);
        MediaDTO createdMedia = mediaService.createEntity(mediaFromFacebookPost);
        createdNewsEntry.getNewsImage().setMedia(createdMedia);
        createdNewsEntry = newsEntryService.updateEntity(createdNewsEntry);
      }
      createdEntries.add(createdNewsEntry);
    });
    return createdEntries;
  }

  NewsEntryDTO convertPostToNewsEntry(FacebookImportPost facebookPost, Locale locale) {

    NewsEntryDTOBuilder convertedPostBuilder = NewsEntryDTOBuilder.create().author(facebookPost.getAuthor())
        .facebookId(facebookPost.getFacebookId()).tags(computeTags(locale)).title(computeTitle(locale));

    if (hasContent(facebookPost)) {
      NewsContentDTO content = computeNewsContentFromPost(facebookPost);
      convertedPostBuilder.newsContent(content);
    }

    if (hasImage(facebookPost)) {
      NewsImageDTO image = computeNewsImageFromPost(facebookPost, locale);
      convertedPostBuilder.newsImage(image);
    }

    return convertedPostBuilder.build();
  }

  NewsImageDTO computeNewsImageFromPost(FacebookImportPost facebookPost, Locale locale) {

    return NewsImageDTOBuilder.create().alt(computeAlt(facebookPost, locale)).legend(computeLegend(locale)).build();
  }

  NewsContentDTO computeNewsContentFromPost(FacebookImportPost facebookPost) {
    return NewsContentDTOBuilder.create().content(facebookPost.getDescription()).linkUrl(facebookPost.getLinkUrl())
        .videoUrl(facebookPost.getVideoUrl()).build();
  }

  String computeLegend(Locale locale) {
    return messageSource.getI18n("facebook.image.legend", locale);
  }

  String computeAlt(FacebookImportPost facebookPost, Locale locale) {
    return messageSource.getI18n("facebook.image.alt", locale) + facebookPost.getFacebookId();
  }

  boolean hasContent(FacebookImportPost facebookPost) {
    return StringUtils.hasText(facebookPost.getDescription());
  }

  boolean hasImage(FacebookImportPost facebookPost) {
    return StringUtils.hasText(facebookPost.getPhotoUrl());
  }

  MediaDTO createMediaFromFacebookPost(NewsEntryDTO newsEntry, FacebookImportPost facebookPost) {

    byte[] data = recoverImageBytes(facebookPost);
    if (data.length == 0) {
      return MediaDTOBuilder.create().build();
    }
    String contentType = computeContentTypeFromBytes(facebookPost, data);
    String extension = computeExtentionFromFacebookPost(facebookPost);

    String fileName = "image_" + newsEntry.getId();
    String src = fileName + "." + extension;

    fileService.saveMediaOnSystem(src, data);

    MediaDTO mediaToCreate = MediaDTOBuilder.create().contentType(contentType).name(src)
        .size(Long.parseLong(String.valueOf(data.length))).extension(extension).src(MEDIA_CONTROLLER_PATH + src)
        .build();

    return mediaToCreate;
  }

  String computeTags(Locale locale) {
    return messageSource.getI18n("facebook.news.tag", locale);
  }

  String computeTitle(Locale locale) {
    return messageSource.getI18n("facebook.news.title", locale);
  }

  byte[] recoverImageBytes(FacebookImportPost facebookPost) {
    return getMediaOperations().getAlbumImage(facebookPost.getObjectId(), ImageType.NORMAL);
  }

  MediaOperations getMediaOperations() {
    return facebookAdapter.getMediaOperations();
  }

  String computeContentTypeFromBytes(FacebookImportPost facebookPost, byte[] data) {
    String contentType = "";
    try {
      contentType = URLConnection.guessContentTypeFromStream(prepareInputStream(data));
    } catch (IOException e) {
      LOGGER.warn("Impossible de determiner le type de l'image pour l'album ", facebookPost.getObjectId(), e);
    }
    return contentType;
  }

  String computeExtentionFromFacebookPost(FacebookImportPost facebookPost) {
    String photoUrl = facebookPost.getPhotoUrl();
    String afterDot = photoUrl.substring(photoUrl.lastIndexOf(".") + 1);
    return afterDot.substring(0, afterDot.indexOf("?"));
  }

  ByteArrayInputStream prepareInputStream(byte[] data) {
    return new ByteArrayInputStream(data);
  }

}
