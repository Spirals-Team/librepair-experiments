
package org.molgenis.data.elasticsearch.generator.model;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_DocumentAction extends DocumentAction {

  private final Index index;
  private final Document document;
  private final DocumentAction.Operation operation;

  private AutoValue_DocumentAction(
      Index index,
      Document document,
      DocumentAction.Operation operation) {
    this.index = index;
    this.document = document;
    this.operation = operation;
  }

  @Override
  public Index getIndex() {
    return index;
  }

  @Override
  public Document getDocument() {
    return document;
  }

  @Override
  public DocumentAction.Operation getOperation() {
    return operation;
  }

  @Override
  public String toString() {
    return "DocumentAction{"
        + "index=" + index + ", "
        + "document=" + document + ", "
        + "operation=" + operation
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof DocumentAction) {
      DocumentAction that = (DocumentAction) o;
      return (this.index.equals(that.getIndex()))
           && (this.document.equals(that.getDocument()))
           && (this.operation.equals(that.getOperation()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.index.hashCode();
    h *= 1000003;
    h ^= this.document.hashCode();
    h *= 1000003;
    h ^= this.operation.hashCode();
    return h;
  }

  static final class Builder extends DocumentAction.Builder {
    private Index index;
    private Document document;
    private DocumentAction.Operation operation;
    Builder() {
    }
    @Override
    public DocumentAction.Builder setIndex(Index index) {
      if (index == null) {
        throw new NullPointerException("Null index");
      }
      this.index = index;
      return this;
    }
    @Override
    public DocumentAction.Builder setDocument(Document document) {
      if (document == null) {
        throw new NullPointerException("Null document");
      }
      this.document = document;
      return this;
    }
    @Override
    public DocumentAction.Builder setOperation(DocumentAction.Operation operation) {
      if (operation == null) {
        throw new NullPointerException("Null operation");
      }
      this.operation = operation;
      return this;
    }
    @Override
    public DocumentAction build() {
      String missing = "";
      if (this.index == null) {
        missing += " index";
      }
      if (this.document == null) {
        missing += " document";
      }
      if (this.operation == null) {
        missing += " operation";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_DocumentAction(
          this.index,
          this.document,
          this.operation);
    }
  }

}
