
package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.type.GeocacheLogType;
import java.util.Date;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_FieldNote extends FieldNote {

  private final String cacheCode;
  private final Date dateLogged;
  private final GeocacheLogType logType;
  private final String note;

  private AutoValue_FieldNote(
      String cacheCode,
      Date dateLogged,
      GeocacheLogType logType,
      String note) {
    this.cacheCode = cacheCode;
    this.dateLogged = dateLogged;
    this.logType = logType;
    this.note = note;
  }

  @Override
  public String cacheCode() {
    return cacheCode;
  }

  @Override
  public Date dateLogged() {
    return dateLogged;
  }

  @Override
  public GeocacheLogType logType() {
    return logType;
  }

  @Override
  public String note() {
    return note;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof FieldNote) {
      FieldNote that = (FieldNote) o;
      return (this.cacheCode.equals(that.cacheCode()))
           && (this.dateLogged.equals(that.dateLogged()))
           && (this.logType.equals(that.logType()))
           && (this.note.equals(that.note()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.cacheCode.hashCode();
    h *= 1000003;
    h ^= this.dateLogged.hashCode();
    h *= 1000003;
    h ^= this.logType.hashCode();
    h *= 1000003;
    h ^= this.note.hashCode();
    return h;
  }

  private static final long serialVersionUID = 249144828657285091L;

  static final class Builder extends FieldNote.Builder {
    private String cacheCode;
    private Date dateLogged;
    private GeocacheLogType logType;
    private String note;
    Builder() {
    }
    @Override
    public FieldNote.Builder cacheCode(String cacheCode) {
      if (cacheCode == null) {
        throw new NullPointerException("Null cacheCode");
      }
      this.cacheCode = cacheCode;
      return this;
    }
    @Override
    public FieldNote.Builder dateLogged(Date dateLogged) {
      if (dateLogged == null) {
        throw new NullPointerException("Null dateLogged");
      }
      this.dateLogged = dateLogged;
      return this;
    }
    @Override
    public FieldNote.Builder logType(GeocacheLogType logType) {
      if (logType == null) {
        throw new NullPointerException("Null logType");
      }
      this.logType = logType;
      return this;
    }
    @Override
    public FieldNote.Builder note(String note) {
      if (note == null) {
        throw new NullPointerException("Null note");
      }
      this.note = note;
      return this;
    }
    @Override
    public FieldNote build() {
      String missing = "";
      if (this.cacheCode == null) {
        missing += " cacheCode";
      }
      if (this.dateLogged == null) {
        missing += " dateLogged";
      }
      if (this.logType == null) {
        missing += " logType";
      }
      if (this.note == null) {
        missing += " note";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_FieldNote(
          this.cacheCode,
          this.dateLogged,
          this.logType,
          this.note);
    }
  }

}
