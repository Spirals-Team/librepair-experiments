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

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorBuilder;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.error.ErrorCauseBuilder;
import com.cmpl.web.core.common.message.WebMessageSource;

@RunWith(MockitoJUnitRunner.class)
public class PageValidatorImplTest {

  @Mock
  private WebMessageSource messageSource;

  @Spy
  @InjectMocks
  private PageValidatorImpl validator;

  @Test
  public void testValidate_No_Error() throws Exception {

    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.anyString());

    Assert.assertNull(validator.validate("someName", "someMenuTitle", Locale.FRANCE));
  }

  @Test
  public void testValidate_No_Name() throws Exception {

    String someName = "someName";
    String someMenuTitle = "someMenuTitle";
    Error error = ErrorBuilder.create().build();
    ErrorCause cause = ErrorCauseBuilder.create().code(ERROR_CAUSE.EMPTY_PAGE_NAME.getCauseKey()).message("no_Name")
        .build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyList());
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(someName));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(someMenuTitle));

    Assert.assertEquals(error, validator.validate(someName, someMenuTitle, Locale.FRANCE));
  }

  @Test
  public void testValidate_No_MenuTitle() throws Exception {
    String someName = "someName";
    String someMenuTitle = "someMenuTitle";
    Error error = ErrorBuilder.create().build();
    ErrorCause cause = ErrorCauseBuilder.create().code(ERROR_CAUSE.EMPTY_PAGE_MENU_TITLE.getCauseKey())
        .message("no_Name").build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyList());
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(someName));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(someMenuTitle));

    Assert.assertEquals(error, validator.validate(someName, someMenuTitle, Locale.FRANCE));
  }

  @Test
  public void testValidate_No_Name_No_MenuTitle() throws Exception {
    String someName = "someName";
    String someMenuTitle = "someMenuTitle";
    Error error = ErrorBuilder.create().build();
    ErrorCause cause = ErrorCauseBuilder.create().code(ERROR_CAUSE.EMPTY_PAGE_NAME.getCauseKey()).message("no_Name")
        .build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyList());
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(someName));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(someMenuTitle));

    Assert.assertEquals(error, validator.validate(someName, someMenuTitle, Locale.FRANCE));
  }

  @Test
  public void testValidateUpdate() throws Exception {

    PageUpdateForm form = PageUpdateFormBuilder.create().name("someName").menuTitle("someMenuTitle").build();
    BDDMockito.doReturn(null).when(validator)
        .validate(BDDMockito.anyString(), BDDMockito.anyString(), BDDMockito.any(Locale.class));
    Assert.assertNull(validator.validateUpdate(form, Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).validate(BDDMockito.anyString(), BDDMockito.anyString(),
        BDDMockito.any(Locale.class));
  }

  @Test
  public void testValidateCreate() throws Exception {
    PageCreateForm form = PageCreateFormBuilder.create().name("someName").menuTitle("someMenuTitle").build();
    BDDMockito.doReturn(null).when(validator)
        .validate(BDDMockito.anyString(), BDDMockito.anyString(), BDDMockito.any(Locale.class));
    Assert.assertNull(validator.validateCreate(form, Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).validate(BDDMockito.anyString(), BDDMockito.anyString(),
        BDDMockito.any(Locale.class));
  }

}
