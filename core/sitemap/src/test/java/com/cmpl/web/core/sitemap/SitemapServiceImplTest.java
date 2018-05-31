package com.cmpl.web.core.sitemap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.news.NewsEntryDTO;
import com.cmpl.web.core.news.NewsEntryDTOBuilder;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapUrl;

@RunWith(MockitoJUnitRunner.class)
public class SitemapServiceImplTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private WebMessageSource messageSource;

  @Mock
  private ContextHolder contextHolder;

  @InjectMocks
  @Spy
  private SitemapServiceImpl service;

  @Before
  public void setUp() {
    File sitemap = new File("src/test/resources/sitemap.xml");
    if (sitemap.exists()) {
      sitemap.delete();
    }

  }

  @Test
  public void testComputeLastModified() throws Exception {

    LocalDateTime today = LocalDateTime.now();
    LocalDateTime yesterday = today.minusDays(1);
    LocalDateTime twoDaysAgo = yesterday.minusDays(1);

    NewsEntryDTO newsEntryToday = NewsEntryDTOBuilder.create().modificationDate(today).build();
    NewsEntryDTO newsEntryYesterday = NewsEntryDTOBuilder.create().modificationDate(yesterday).build();
    NewsEntryDTO newsEntryTwoDaysAgo = NewsEntryDTOBuilder.create().modificationDate(twoDaysAgo).build();

    List<NewsEntryDTO> entries = new ArrayList<>();
    entries.add(newsEntryTwoDaysAgo);
    entries.add(newsEntryToday);
    entries.add(newsEntryYesterday);

    LocalDateTime result = service.computeLastModified(entries);

    Assert.assertEquals(today, result);
  }

  @Test
  public void testGetI18nValue() throws Exception {

    String value = "someValue";
    String key = "someKey";
    BDDMockito.doReturn(value).when(messageSource).getI18n(BDDMockito.eq(key), BDDMockito.eq(Locale.FRANCE));
    String result = service.getI18nValue(key, Locale.FRANCE);

    Assert.assertEquals(value, result);
  }

  @Test
  public void testReadSitemap() throws IOException {
    Path path = Paths.get("src/test/resources/sitemap_test.xml");

    String result = service.readSitemap(path);

    Assert.assertTrue(result.contains("actualites"));
    Assert.assertTrue(result.contains("horaires"));
    Assert.assertTrue(result.contains("rendez-vous"));
    Assert.assertTrue(result.contains("contact"));
    Assert.assertTrue(result.contains("tarifs"));
    Assert.assertTrue(result.contains("techniques"));
    Assert.assertTrue(result.contains("soins_medicaux"));
    Assert.assertTrue(result.contains("gynecologue"));
    Assert.assertTrue(result.contains("centre-medical"));

  }

  @Test
  public void testWriteSitemap() throws IOException {

    Path path = Paths.get("src/test/resources");
    String host = "http://www.cmpl.com/";

    WebSitemapUrl urlMenu = new WebSitemapUrl.Options(host + "techniques").priority(1d).changeFreq(ChangeFreq.NEVER)
        .build();

    BDDMockito.given(contextHolder.getWebsiteUrl()).willReturn(host);
    BDDMockito.doReturn(Arrays.asList(urlMenu)).when(service).computeMenuUrls(BDDMockito.eq(Locale.FRANCE));

    service.writeSitemap(path, Locale.FRANCE);

    BDDMockito.verify(service, BDDMockito.times(1)).computeMenuUrls(BDDMockito.eq(Locale.FRANCE));

    File sitemap = new File("src/test/resources/sitemap.xml");
    if (sitemap.exists()) {
      sitemap.delete();
    }

  }

  @Test
  public void testCreateSiteMap() throws IOException, BaseException {
    String sitemap = "<?xml version='1.0' encoding='UTF-8'?><urlset><url><loc>http://cmpl.com/</loc><changefreq>never</changefreq><priority>1.0</priority></url></urlset>";

    BDDMockito.doReturn(sitemap).when(service).readSitemap(BDDMockito.any(Path.class));
    BDDMockito.doNothing().when(service).writeSitemap(BDDMockito.any(Path.class), BDDMockito.eq(Locale.FRANCE));

    String result = service.createSiteMap(Locale.FRANCE);

    Assert.assertEquals(sitemap, result);
  }

  @Test
  public void testCreateSiteMap_Exception() throws Exception {

    exception.expect(BaseException.class);
    BDDMockito.doThrow(new IOException("")).when(service)
        .writeSitemap(BDDMockito.any(Path.class), BDDMockito.eq(Locale.FRANCE));

    service.createSiteMap(Locale.FRANCE);

  }

}
