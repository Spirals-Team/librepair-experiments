package com.cmpl.web.core.menu;

import java.util.Arrays;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorBuilder;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.error.ErrorCauseBuilder;

@RunWith(MockitoJUnitRunner.class)
public class MenuValidatorImplTest {

  @Spy
  @InjectMocks
  private MenuValidatorImpl validator;

  @Test
  public void testValidateUpdate_No_Errors() throws Exception {

    MenuUpdateForm form = MenuUpdateFormBuilder.create().title("someTitle").pageId("123456789").orderInMenu(1).build();
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.anyString());

    Assert.assertNull(validator.validateUpdate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateUpdate_Bad_Order() throws Exception {

    MenuUpdateForm form = MenuUpdateFormBuilder.create().orderInMenu(0).build();

    ErrorCause cause = ErrorCauseBuilder.create().code(ERROR_CAUSE.BAD_ORDER.getCauseKey()).message("badOrder").build();
    Error error = ErrorBuilder.create().causes(Arrays.asList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyList());

    Assert.assertEquals(error, validator.validateUpdate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateUpdate_Empty_Page() throws Exception {

    MenuUpdateForm form = MenuUpdateFormBuilder.create().orderInMenu(1).title("someTitle").pageId("somePageId").build();
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getTitle()));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = ErrorCauseBuilder.create().code(ERROR_CAUSE.EMPTY_PAGE.getCauseKey()).message("empty_page")
        .build();
    Error error = ErrorBuilder.create().causes(Arrays.asList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyList());

    Assert.assertEquals(error, validator.validateUpdate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateUpdate_Empty_Menu_Title() throws Exception {

    MenuUpdateForm form = MenuUpdateFormBuilder.create().orderInMenu(1).title("someTitle").pageId("somePageId").build();
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getTitle()));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = ErrorCauseBuilder.create().code(ERROR_CAUSE.EMPTY_MENU_TITLE.getCauseKey())
        .message("empty_title").build();
    Error error = ErrorBuilder.create().causes(Arrays.asList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyList());

    Assert.assertEquals(error, validator.validateUpdate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateUpdate_Empty_Multiple_Errors() throws Exception {

    MenuUpdateForm form = MenuUpdateFormBuilder.create().orderInMenu(1).title("someTitle").pageId("somePageId").build();
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getTitle()));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = ErrorCauseBuilder.create().code(ERROR_CAUSE.BAD_ORDER.getCauseKey()).message("badOrder").build();
    ErrorCause causeTitle = ErrorCauseBuilder.create().code(ERROR_CAUSE.EMPTY_MENU_TITLE.getCauseKey())
        .message("empty_title").build();
    Error error = ErrorBuilder.create().causes(Arrays.asList(cause, causeTitle)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyList());

    Assert.assertEquals(error, validator.validateUpdate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateCreate_No_Errors() throws Exception {

    MenuCreateForm form = MenuCreateFormBuilder.create().title("someTitle").pageId("123456789").orderInMenu(1).build();
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.anyString());

    Assert.assertNull(validator.validateCreate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateCreate_Bad_Order() throws Exception {

    MenuCreateForm form = MenuCreateFormBuilder.create().orderInMenu(0).build();

    ErrorCause cause = ErrorCauseBuilder.create().code(ERROR_CAUSE.BAD_ORDER.getCauseKey()).message("badOrder").build();
    Error error = ErrorBuilder.create().causes(Arrays.asList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyList());

    Assert.assertEquals(error, validator.validateCreate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateCreate_Empty_Page() throws Exception {

    MenuCreateForm form = MenuCreateFormBuilder.create().orderInMenu(1).title("someTitle").pageId("somePageId").build();
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getTitle()));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = ErrorCauseBuilder.create().code(ERROR_CAUSE.EMPTY_PAGE.getCauseKey()).message("empty_page")
        .build();
    Error error = ErrorBuilder.create().causes(Arrays.asList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyList());

    Assert.assertEquals(error, validator.validateCreate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateCreate_Empty_Menu_Title() throws Exception {

    MenuCreateForm form = MenuCreateFormBuilder.create().orderInMenu(1).title("someTitle").pageId("somePageId").build();
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getTitle()));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = ErrorCauseBuilder.create().code(ERROR_CAUSE.EMPTY_MENU_TITLE.getCauseKey())
        .message("empty_title").build();
    Error error = ErrorBuilder.create().causes(Arrays.asList(cause)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyList());

    Assert.assertEquals(error, validator.validateCreate(form, Locale.FRANCE));
  }

  @Test
  public void testValidateCreate_Multiple_Errors() throws Exception {

    MenuCreateForm form = MenuCreateFormBuilder.create().orderInMenu(1).title("someTitle").pageId("somePageId").build();
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(form.getTitle()));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(form.getPageId()));

    ErrorCause cause = ErrorCauseBuilder.create().code(ERROR_CAUSE.BAD_ORDER.getCauseKey()).message("badOrder").build();
    ErrorCause causeTitle = ErrorCauseBuilder.create().code(ERROR_CAUSE.EMPTY_MENU_TITLE.getCauseKey())
        .message("empty_title").build();
    Error error = ErrorBuilder.create().causes(Arrays.asList(cause, causeTitle)).build();
    BDDMockito.doReturn(cause).when(validator)
        .computeCause(BDDMockito.any(ERROR_CAUSE.class), BDDMockito.any(Locale.class));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyList());

    Assert.assertEquals(error, validator.validateCreate(form, Locale.FRANCE));
  }
}
