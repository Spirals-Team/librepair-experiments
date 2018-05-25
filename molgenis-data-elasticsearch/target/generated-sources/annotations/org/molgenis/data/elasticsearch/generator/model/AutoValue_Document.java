
package org.molgenis.data.elasticsearch.generator.model;

import javax.annotation.Generated;
import javax.annotation.Nullable;
import org.elasticsearch.common.xcontent.XContentBuilder;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Document extends Document {

  private final String id;
  private final XContentBuilder content;

  private AutoValue_Document(
      String id,
      @Nullable XContentBuilder content) {
    this.id = id;
    this.content = content;
  }

  @Override
  public String getId() {
    return id;
  }

  @Nullable
  @Override
  public XContentBuilder getContent() {
    return content;
  }

  @Override
  public String toString() {
    return "Document{"
        + "id=" + id + ", "
        + "content=" + content
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Document) {
      Document that = (Document) o;
      return (this.id.equals(that.getId()))
           && ((this.content == null) ? (that.getContent() == null) : this.content.equals(that.getContent()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= (content == null) ? 0 : this.content.hashCode();
    return h;
  }

  static final class Builder extends Document.Builder {
    private String id;
    private XContentBuilder content;
    Builder() {
    }
    @Override
    public Document.Builder setId(String id) {
      if (id == null) {
        throw new NullPointerException("Null id");
      }
      this.id = id;
      return this;
    }
    @Override
    public Document.Builder setContent(@Nullable XContentBuilder content) {
      this.content = content;
      return this;
    }
    @Override
    public Document build() {
      String missing = "";
      if (this.id == null) {
        missing += " id";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_Document(
          this.id,
          this.content);
    }
  }

}
