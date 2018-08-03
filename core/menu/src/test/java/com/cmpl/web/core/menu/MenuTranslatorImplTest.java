package com.cmpl.web.core.menu;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MenuTranslatorImplTest {

  @Spy
  @InjectMocks
  private MenuTranslatorImpl translator;

  @Test
  public void testFromDTOToResponse() throws Exception {

    MenuDTO menu = MenuDTOBuilder.create().href("someHref").id(123456789l).build();

    MenuResponse result = translator.fromDTOToResponse(menu);

    Assert.assertEquals(menu, result.getMenu());

  }

  @Test
  public void testFromCreateFormToDTO() throws Exception {

    MenuCreateForm form = MenuCreateFormBuilder.create().href("someHref").label("someLabel").orderInMenu(1)
        .pageId("123456789").title("someTitle").build();
    MenuDTO menu = translator.fromCreateFormToDTO(form);

    Assert.assertEquals(form.getHref(), menu.getHref());
    Assert.assertEquals(form.getLabel(), menu.getLabel());
    Assert.assertEquals(form.getOrderInMenu(), menu.getOrderInMenu());
    Assert.assertEquals(form.getPageId(), menu.getPageId());
    Assert.assertEquals(form.getTitle(), menu.getTitle());
  }

}
