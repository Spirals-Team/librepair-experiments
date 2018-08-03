package com.cmpl.web.facebook;

import org.mockito.BDDMockito;
import org.springframework.social.facebook.api.Reference;

import com.cmpl.web.core.common.builder.Builder;

public class ReferenceBuilder extends Builder<Reference> {

  private ReferenceBuilder() {
  }

  private String name;

  public ReferenceBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public Reference build() {
    Reference reference = BDDMockito.mock(Reference.class);

    BDDMockito.doReturn(name).when(reference).getName();

    return reference;
  }

  public static ReferenceBuilder create() {
    return new ReferenceBuilder();
  }

}
