package com.cmpl.web.facebook;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.news.NewsEntryDTO;
import com.cmpl.web.core.news.NewsEntryDTOBuilder;

@RunWith(MockitoJUnitRunner.class)
public class FacebookDispatcherImplTest {

  @Mock
  private FacebookImportService facebookImportService;

  @Mock
  private FacebookImportTranslator facebookImportTranslator;

  @InjectMocks
  @Spy
  private FacebookDispatcherImpl facebookDispatcher;

  @Test
  public void testCreateEntity() throws Exception {

    FacebookImportRequest facebookImportRequest = new FacebookImportRequest();
    FacebookImportPost post = new FacebookImportPost();

    List<FacebookImportPost> posts = Arrays.asList(post);
    facebookImportRequest.setPostsToImport(posts);

    FacebookImportResponse response = new FacebookImportResponse();

    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().id(123456789L).build();

    BDDMockito.doReturn(posts).when(facebookImportTranslator).fromRequestToPosts(BDDMockito.eq(facebookImportRequest));
    BDDMockito.doReturn(Arrays.asList(newsEntry)).when(facebookImportService)
        .importFacebookPost(BDDMockito.anyList(), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(response).when(facebookImportTranslator).fromDTOToResponse(BDDMockito.anyList());

    FacebookImportResponse result = facebookDispatcher.createEntity(facebookImportRequest, Locale.FRANCE);

    Assert.assertEquals(response, result);

    BDDMockito.verify(facebookImportTranslator, BDDMockito.times(1)).fromRequestToPosts(
        BDDMockito.eq(facebookImportRequest));
    BDDMockito.verify(facebookImportTranslator, BDDMockito.times(1)).fromDTOToResponse(BDDMockito.anyList());
    BDDMockito.verify(facebookImportService, BDDMockito.times(1)).importFacebookPost(BDDMockito.anyList(),
        BDDMockito.any(Locale.class));
  }
}
