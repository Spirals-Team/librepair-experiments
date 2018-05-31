package com.cmpl.web.core.sitemap;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.menu.MenuDTO;
import com.cmpl.web.core.menu.MenuService;
import com.cmpl.web.core.news.NewsEntryDTO;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

/**
 * Implementation de l'interface gerant le sitemap
 * 
 * @author Louis
 *
 */
@CacheConfig(cacheNames = {"sitemap"})
public class SitemapServiceImpl implements SitemapService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SitemapServiceImpl.class);

  private final WebMessageSource messageSource;

  private final MenuService menuService;

  private final ContextHolder contextHolder;

  public SitemapServiceImpl(WebMessageSource messageSource, MenuService menuService, ContextHolder contextHolder) {
    this.messageSource = messageSource;
    this.menuService = menuService;
    this.contextHolder = contextHolder;
  }

  @Override
  @Cacheable(sync = true)
  public String createSiteMap(Locale locale) throws BaseException {

    try {
      Path temporarySitemapFile = Files.createTempDirectory("sitemap");
      writeSitemap(temporarySitemapFile, locale);
      Path sitemapPath = Paths.get(temporarySitemapFile.toString(), "sitemap.xml");
      return readSitemap(sitemapPath);
    } catch (IOException e) {
      LOGGER.error("Erreur lors de la génération du sitemap", e);
      throw new BaseException(e.getMessage());
    }

  }

  void writeSitemap(Path temporarySitemapFile, Locale locale) throws IOException {
    WebSitemapGenerator sitemap = WebSitemapGenerator.builder(contextHolder.getWebsiteUrl(),
        temporarySitemapFile.toFile()).build();

    List<WebSitemapUrl> menuUrls = computeMenuUrls(locale);
    sitemap.addUrls(menuUrls);
    sitemap.write();

  }

  String readSitemap(Path sitemapPath) throws IOException {
    Charset charset = Charset.forName("UTF-8");
    BufferedReader reader = Files.newBufferedReader(sitemapPath, charset);
    StringBuilder siteMapbuilder = new StringBuilder();
    String sitemapLine;
    while ((sitemapLine = reader.readLine()) != null) {
      siteMapbuilder.append(sitemapLine);
    }
    reader.close();

    return siteMapbuilder.toString();
  }

  String getI18nValue(String key, Locale locale) {
    return messageSource.getI18n(key, locale);
  }

  List<WebSitemapUrl> computeMenuUrls(Locale locale) throws MalformedURLException {
    List<WebSitemapUrl> menuUrls = new ArrayList<>();

    for (MenuDTO menu : menuService.getEntities()) {
      if (!StringUtils.hasText(menu.getParentId())) {
        menuUrls.add(computeUrlForMenu(menu, locale));
      }
    }

    return menuUrls;
  }

  WebSitemapUrl computeUrlForMenu(MenuDTO menu, Locale locale) throws MalformedURLException {
    return new WebSitemapUrl.Options(contextHolder.getWebsiteUrl() + getI18nValue(menu.getHref(), locale))
        .changeFreq(ChangeFreq.YEARLY).priority(1d).build();
  }

  LocalDateTime computeLastModified(List<NewsEntryDTO> entries) {
    LocalDateTime lastModified = entries.get(0).getModificationDate();
    for (NewsEntryDTO newsEntry : entries) {
      LocalDateTime dateModification = newsEntry.getModificationDate();
      if (dateModification.isAfter(lastModified)) {
        lastModified = dateModification;
      }
    }
    return lastModified;

  }

}
