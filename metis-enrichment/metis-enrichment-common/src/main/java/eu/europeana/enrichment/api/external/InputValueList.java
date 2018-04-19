package eu.europeana.enrichment.api.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import eu.europeana.enrichment.utils.InputValue;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * JSON helper class for InputValue class
 * 
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
@JsonSerialize
@JsonRootName(value = "inputValueList")
@XmlRootElement(name = "inputValueList")
@XmlAccessorType(XmlAccessType.FIELD)
public class InputValueList {

  @XmlElement(name = "inputValue")
  @JsonProperty("inputValue")
  private List<InputValue> inputValueList;

  public InputValueList() {
    // Required for XML mapping.
  }

  public List<InputValue> getInputValueList() {
    return inputValueList == null ? null : Collections.unmodifiableList(inputValueList);
  }

  public void setInputValueList(List<InputValue> inputValueList) {
    this.inputValueList = inputValueList == null ? null : new ArrayList<>(inputValueList);
  }
}
