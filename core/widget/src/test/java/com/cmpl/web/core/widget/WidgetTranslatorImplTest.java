package com.cmpl.web.core.widget;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.StringUtils;

@RunWith(MockitoJUnitRunner.class)
public class WidgetTranslatorImplTest {

  @Spy
  private WidgetTranslatorImpl translator;

  @Test
  public void testFromCreateFormToDTOWidget() {
    WidgetCreateForm form = WidgetCreateFormBuilder.create().localeCode(Locale.FRANCE.getLanguage()).name("someName")
        .type("HTML").build();

    WidgetDTO result = translator.fromCreateFormToDTO(form);

    Assert.assertTrue(!StringUtils.hasText(result.getPersonalization()));
    Assert.assertEquals(form.getName(), result.getName());
    Assert.assertEquals(form.getType(), result.getType());

  }

  @Test
  public void testFromDTOToResponseWidget() {
    WidgetDTO dto = WidgetDTOBuilder.create().build();

    WidgetResponse result = translator.fromDTOToResponse(dto);

    Assert.assertEquals(dto, result.getWidget());
    Assert.assertNull(result.getError());

  }

  @Test
  public void testFromCreateFormToDTOWidgetPage() {
    WidgetPageCreateForm form = WidgetPageCreateFormBuilder.create().pageId("123456789").widgetId("123456789").build();

    WidgetPageDTO result = translator.fromCreateFormToDTO(form);

    Assert.assertEquals(form.getPageId(), result.getPageId());
    Assert.assertEquals(form.getWidgetId(), result.getWidgetId());

  }

  @Test
  public void testFromDTOToResponseWidgetPage() {
    WidgetPageDTO dto = WidgetPageDTOBuilder.create().build();

    WidgetPageResponse result = translator.fromDTOToResponse(dto);

    Assert.assertEquals(dto, result.getWidgetPage());
    Assert.assertNull(result.getError());

  }

}
