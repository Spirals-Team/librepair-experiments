package com.cmpl.web.backup.writer;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.cmpl.web.core.common.dao.BaseEntity;

public class EntityTest extends BaseEntity {

  private Date date;
  private byte[] bytes;
  private Locale locale;
  private LocalDateTime localDate;
  private List<String> strings;
  private String string;
  private boolean booleanValue;
  private int integerValue;
  private Long someLong;

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public void setBytes(byte[] bytes) {
    this.bytes = bytes;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public LocalDateTime getLocalDate() {
    return localDate;
  }

  public void setLocalDate(LocalDateTime localDate) {
    this.localDate = localDate;
  }

  public List<String> getStrings() {
    return strings;
  }

  public void setStrings(List<String> strings) {
    this.strings = strings;
  }

  public String getString() {
    return string;
  }

  public void setString(String string) {
    this.string = string;
  }

  public boolean isBooleanValue() {
    return booleanValue;
  }

  public void setBooleanValue(boolean booleanValue) {
    this.booleanValue = booleanValue;
  }

  public int getIntegerValue() {
    return integerValue;
  }

  public void setIntegerValue(int integerValue) {
    this.integerValue = integerValue;
  }

  public Long getSomeLong() {
    return someLong;
  }

  public void setSomeLong(Long someLong) {
    this.someLong = someLong;
  }

}
