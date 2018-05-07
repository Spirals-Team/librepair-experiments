package eu.europeana.enrichment.api.external.model.zoho;

/**
 * This class supports representation of Zoho organization fields for API to Zoho organization
 * object that contains array of 'val'/'content' fields.
 * 
 * @author GrafR
 *
 */
public class ZohoResponseField {

  private String val;
  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getVal() {
    return val;
  }

  public void setVal(String val) {
    this.val = val;
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof ZohoResponseField && this.val.equals(((ZohoResponseField) obj).val));
  }

  @Override
  public int hashCode() {
    return val.hashCode();
  }

}
