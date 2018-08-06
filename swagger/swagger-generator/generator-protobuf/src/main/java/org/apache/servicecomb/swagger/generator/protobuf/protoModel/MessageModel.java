package org.apache.servicecomb.swagger.generator.protobuf.protoModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(MessageModel.class);

  private String messageName;

  public String getMessageName() {
    return messageName;
  }

  public void setMessageName(String messageName) {
    this.messageName = messageName;
  }

  private List<FieldModel> fieldModels = new ArrayList<>();

  public MessageModel(String messageName) {
    this.messageName = messageName;
  }

  public void addField(FieldModel fieldModel) {
    fieldModels.add(fieldModel);
  }

  @Override
  public String toString() {
    //每次输出必须排一次序,保证契约不变的情况下,两边tag一致
    Collections.sort(fieldModels);
    StringBuilder stringBuilder = new StringBuilder("message ");
    stringBuilder.append(messageName);
    stringBuilder.append("{\n");
    for (int i = 0; i < fieldModels.size(); i++) {
      stringBuilder.append(fieldModels.get(i).convertToProto(i + 1));
    }
    return stringBuilder.append("}\n").toString();
  }

  public String getAllMessage() {
    Collections.sort(fieldModels);
    StringBuilder stringBuilder = new StringBuilder("message ");
    stringBuilder.append(messageName);
    stringBuilder.append("{\n");
    for (int i = 0; i < fieldModels.size(); i++) {
      stringBuilder.append(fieldModels.get(i).showAllInfo());
    }
    return stringBuilder.append("}\n").toString();
  }
}
