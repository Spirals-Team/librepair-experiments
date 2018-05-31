package com.cmpl.web.backup.writer;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class EntityTestBuilder extends BaseBuilder<EntityTest> {

  private Date date;
  private byte[] bytes;
  private Locale locale;
  private LocalDateTime localDate;
  private List<String> strings;
  private String string;
  private boolean booleanValue;
  private int integerValue;
  private Long someLong;

  public EntityTestBuilder date(Date date) {
    this.date = date;
    return this;
  }

  public EntityTestBuilder bytes(byte[] bytes) {
    this.bytes = bytes;
    return this;
  }

  public EntityTestBuilder locale(Locale locale) {
    this.locale = locale;
    return this;
  }

  public EntityTestBuilder localDate(LocalDateTime localDate) {
    this.localDate = localDate;
    return this;
  }

  public EntityTestBuilder strings(List<String> strings) {
    this.strings = strings;
    return this;
  }

  public EntityTestBuilder string(String string) {
    this.string = string;
    return this;
  }

  public EntityTestBuilder booleanValue(boolean booleanValue) {
    this.booleanValue = booleanValue;
    return this;
  }

  public EntityTestBuilder integerValue(int integerValue) {
    this.integerValue = integerValue;
    return this;
  }

  public EntityTestBuilder someLong(Long someLong) {
    this.someLong = someLong;
    return this;
  }

  @Override
  public EntityTest build() {
    EntityTest test = new EntityTest();
    test.setBooleanValue(booleanValue);
    test.setBytes(bytes);
    test.setCreationDate(localDate);
    test.setDate(date);
    test.setId(id);
    test.setIntegerValue(integerValue);
    test.setLocalDate(localDate);
    test.setModificationDate(localDate);
    test.setString(string);
    test.setStrings(strings);
    test.setLocale(locale);
    test.setSomeLong(someLong);

    return test;
  }

  public EntityTestBuilder create() {
    return new EntityTestBuilder();
  }

}
