package com.hubspot.jinjava.interpret;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.hubspot.jinjava.Jinjava;

public class TemplateErrorTest {

  private JinjavaInterpreter interpreter;
  private Jinjava jinjava;

  @Before
  public void setup() {
    jinjava = new Jinjava();
    interpreter = jinjava.newInterpreter();
    JinjavaInterpreter.pushCurrent(interpreter);
  }

  @Test
  public void itShowsFriendlyNameOfBaseObjectForPropNotFound() {
    TemplateError e = TemplateError.fromUnknownProperty(new Object(), "foo", 123, 4);
    assertThat(e.getMessage()).isEqualTo("Cannot resolve property 'foo' in 'Object'");
  }

  @Test
  public void itUsesOverloadedToStringForBaseObject() {
    TemplateError e = TemplateError.fromUnknownProperty(ImmutableMap.of("foo", "bar"), "other", 123, 4);
    assertThat(e.getMessage()).isEqualTo("Cannot resolve property 'other' in '{foo=bar}'");
  }

  @Test
  public void itShowsFieldNameForUnknownTagError() {
    TemplateError e = TemplateError.fromException(new UnknownTagException("unKnown", "{% unKnown() %}", 11, 3));
    assertThat(e.getFieldName()).isEqualTo("unKnown");
  }

  @Test
  public void itShowsFieldNameForSyntaxError() {
    TemplateError e = TemplateError.fromException(new TemplateSyntaxException("da codez", "{{ lolo lolo }}", 11));
    assertThat(e.getFieldName()).isEqualTo("da codez");
  }

  @Test
  public void itRetainsFieldNameCaseForUnknownToken() {
    interpreter.render("{% unKnown() %}");
    assertThat(interpreter.getErrors().get(0).getFieldName()).isEqualTo("unKnown");
  }

  @Test
  public void itSetsFieldNameCaseForSyntaxErrorInFor() {
    RenderResult renderResult = jinjava.renderForResult("{% for item inna navigation %}{% endfor %}", ImmutableMap.of());
    assertThat(renderResult.getErrors().get(0).getFieldName()).isEqualTo("item inna navigation");
  }

}
