package com.cmpl.web.core.factory.media;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.page.BACK_PAGE;

public class MediaManagerDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<MediaDTO>
    implements MediaManagerDisplayFactory {

  private final MediaService mediaService;
  private final ContextHolder contextHolder;

  public MediaManagerDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      MediaService mediaService, ContextHolder contextHolder, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry,
      Set<Locale> availableLocales) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales);
    this.mediaService = mediaService;
    this.contextHolder = contextHolder;
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllMedias(Locale locale, int pageNumber) {
    ModelAndView pagesManager = super.computeModelAndViewForBackPage(BACK_PAGE.MEDIA_VIEW, locale);
    LOGGER.info("Construction des medias pour la page {}", BACK_PAGE.MEDIA_VIEW.name());

    PageWrapper<MediaDTO> pagedMediaDTOWrapped = computePageWrapper(locale, pageNumber);

    pagesManager.addObject("wrappedMedias", pagedMediaDTOWrapped);

    return pagesManager;
  }

  @Override
  public ModelAndView computeModelAndViewForViewMedia(String mediaId, Locale locale) {
    ModelAndView mediaManager = super.computeModelAndViewForBackPage(BACK_PAGE.MEDIA_VISUALIZE, locale);
    LOGGER.info("Construction de la page de visualisation d'un media ");
    MediaDTO media = mediaService.getEntity(Long.parseLong(mediaId));
    mediaManager.addObject("mediaBean", media);
    return mediaManager;
  }

  @Override
  protected Page<MediaDTO> computeEntries(Locale locale, int pageNumber) {
    List<MediaDTO> mediaEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
    Page<MediaDTO> pagedMediaDTOEntries = mediaService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedMediaDTOEntries.getContent())) {
      return new PageImpl<>(mediaEntries);
    }

    mediaEntries.addAll(pagedMediaDTOEntries.getContent());

    return new PageImpl<>(mediaEntries, pageRequest, pagedMediaDTOEntries.getTotalElements());
  }

  @Override
  public ModelAndView computeModelAndViewForUploadMedia(Locale locale) {
    ModelAndView mediaManager = super.computeModelAndViewForBackPage(BACK_PAGE.MEDIA_UPLOAD, locale);
    LOGGER.info("Construction du formulaire d'upload de media ");
    return mediaManager;
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/medias";
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "webmastering:media:create";
  }

  @Override
  protected String getItemLink() {
    return "/manager/medias/_view/";
  }

  @Override
  protected String getCreateItemLink() {
    return getBaseUrl() + "/_upload";
  }
}
