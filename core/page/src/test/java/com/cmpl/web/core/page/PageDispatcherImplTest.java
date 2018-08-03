package com.cmpl.web.core.page;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PageDispatcherImplTest {

  @Mock
  private PageTranslator translator;
  @Mock
  private PageService pageService;

  @Spy
  @InjectMocks
  private PageDispatcherImpl dispatcher;

  @Test
  public void testCreateEntity_No_Error() throws Exception {

    PageCreateForm form = PageCreateFormBuilder.create().localeCode(Locale.FRANCE.getLanguage()).build();

    PageDTO page = PageDTOBuilder.create().build();
    PageResponse response = PageResponseBuilder.create().page(page).build();
    BDDMockito.given(translator.fromCreateFormToDTO(BDDMockito.any(PageCreateForm.class))).willReturn(page);
    BDDMockito.given(pageService.createEntity(BDDMockito.any(PageDTO.class), BDDMockito.anyString())).willReturn(page);
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(PageDTO.class))).willReturn(response);

    Assert.assertEquals(response.getPage(), dispatcher.createEntity(form, Locale.FRANCE).getPage());

    BDDMockito.verify(pageService, BDDMockito.times(1)).createEntity(BDDMockito.any(PageDTO.class),
        BDDMockito.anyString());
    BDDMockito.verify(translator, BDDMockito.times(1)).fromCreateFormToDTO(BDDMockito.any(PageCreateForm.class));
    BDDMockito.verify(translator, BDDMockito.times(1)).fromDTOToResponse(BDDMockito.any(PageDTO.class));
  }

  @Test
  public void testUpdateEntity_No_Error() throws Exception {

    PageUpdateForm form = PageUpdateFormBuilder.create().id(123456789l).localeCode(Locale.FRANCE.getLanguage()).build();

    PageDTO page = PageDTOBuilder.create().build();
    PageResponse response = PageResponseBuilder.create().page(page).build();
    BDDMockito.given(pageService.getEntity(BDDMockito.anyLong(), BDDMockito.anyString())).willReturn(page);
    BDDMockito.given(pageService.updateEntity(BDDMockito.any(PageDTO.class), BDDMockito.anyString())).willReturn(page);
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(PageDTO.class))).willReturn(response);

    Assert.assertEquals(response.getPage(), dispatcher.updateEntity(form, Locale.FRANCE).getPage());

    BDDMockito.verify(pageService, BDDMockito.times(1)).getEntity(BDDMockito.anyLong(), BDDMockito.anyString());
    BDDMockito.verify(pageService, BDDMockito.times(1)).updateEntity(BDDMockito.any(PageDTO.class),
        BDDMockito.anyString());
    BDDMockito.verify(translator, BDDMockito.times(1)).fromDTOToResponse(BDDMockito.any(PageDTO.class));
  }

}
