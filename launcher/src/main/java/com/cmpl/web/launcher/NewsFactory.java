package com.cmpl.web.launcher;

import java.util.ArrayList;
import java.util.List;

import com.cmpl.web.core.news.NewsContent;
import com.cmpl.web.core.news.NewsContentRepository;
import com.cmpl.web.core.news.NewsEntry;
import com.cmpl.web.core.news.NewsEntryRepository;

public class NewsFactory {

  public static void createNewsEntries(final NewsEntryRepository newsEntryRepository,
      final NewsContentRepository newsContentRepository) {

    NewsContent newsContent = createNewsContent(newsContentRepository);

    newsEntryRepository.saveAll(createNewsEntries(newsContent));
  }

  private static List<NewsEntry> createNewsEntries(NewsContent newsContent) {

    List<NewsEntry> newsEntries = new ArrayList<>();

    NewsEntry newsEntryEmpty = new NewsEntry();
    newsEntryEmpty.setAuthor("TEST");
    newsEntryEmpty.setTitle("Un test vide");
    newsEntryEmpty.setTags("test;fun");

    NewsEntry newsEntryNotEmpty = new NewsEntry();
    newsEntryNotEmpty.setAuthor("TEST");
    newsEntryNotEmpty.setTitle("Un test avec contenu");
    newsEntryNotEmpty.setTags("test;fun;yolo");
    newsEntryNotEmpty.setContentId(String.valueOf(newsContent.getId()));

    NewsEntry newsEntryEmpty2 = new NewsEntry();
    newsEntryEmpty2.setAuthor("TEST");
    newsEntryEmpty2.setTitle("Un test vide 2");
    newsEntryEmpty2.setTags("test;fun");

    newsEntries.add(newsEntryEmpty);
    newsEntries.add(newsEntryNotEmpty);
    newsEntries.add(newsEntryEmpty2);

    return newsEntries;
  }

  private static NewsContent createNewsContent(final NewsContentRepository newsContentRepository) {
    NewsContent newsContent = new NewsContent();
    newsContent.setContent("Un contenu de test");
    return newsContentRepository.save(newsContent);

  }

}
