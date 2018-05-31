package com.cmpl.web.core.common.validator;

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

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.ERROR_TYPE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.error.ErrorCauseBuilder;
import com.cmpl.web.core.common.message.WebMessageSource;

@RunWith(MockitoJUnitRunner.class)
public class BaseValidatorTest {

  @Mock
  private WebMessageSource messageSource;

  @InjectMocks
  @Spy
  private BaseValidator validator;

  @Test
  public void testIsStringValid_True() throws Exception {
    boolean result = validator.isStringValid("someString");
    Assert.assertTrue(result);
  }

  @Test
  public void testIsStringValid_False_null() throws Exception {
    boolean result = validator.isStringValid(null);
    Assert.assertFalse(result);
  }

  @Test
  public void testIsStringValid_False_empty() throws Exception {
    boolean result = validator.isStringValid("");
    Assert.assertFalse(result);
  }

  @Test
  public void testIsStringValid_False_blank() throws Exception {
    boolean result = validator.isStringValid(" ");
    Assert.assertFalse(result);
  }

  @Test
  public void testComputeCause() throws Exception {

    BDDMockito.doReturn("someCause").when(validator).getI18n(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));

    ErrorCause result = validator.computeCause(ERROR_CAUSE.INVALID_NEWS_FORMAT, Locale.FRANCE);

    Assert.assertEquals(ERROR_CAUSE.INVALID_NEWS_FORMAT.toString(), result.getCode());
    Assert.assertEquals("someCause", result.getMessage());
  }

  @Test
  public void testComputeError() throws Exception {

    ErrorCause errorCause1 = ErrorCauseBuilder.create().code("someCode1").message("someMessage1").build();
    ErrorCause errorCause2 = ErrorCauseBuilder.create().code("someCode2").message("someMessage2").build();

    List<ErrorCause> causes = Arrays.asList(errorCause1, errorCause2);

    Error result = validator.computeError(causes);

    Assert.assertEquals(2, result.getCauses().size());
    Assert.assertEquals(ERROR_TYPE.INVALID_REQUEST.toString(), result.getCode());

  }

}
