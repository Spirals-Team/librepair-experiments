package org.apache.servicecomb.swagger.generator.protobuf.protoModel;

import java.util.HashMap;
import java.util.Map;

public class MessageSwaggerModel {
  //设置版本信息,默认是3
  private String messageName;

  private Map<String, MessageModel> methodMessages = new HashMap<>();

  private Map<String, MessageModel> definitionMessages = new HashMap<>();

  public void addMethodMessage(String key, MessageModel messageModel) {
    methodMessages.put(key, messageModel);
  }

  public void addDefinitionMessage(String key, MessageModel messageModel) {
    definitionMessages.put(key, messageModel);
  }

  /**
   * 会自动过滤出 type 不支持的字段
   * @return
   */
  public String parseToProto() {

    StringBuilder stringBuilder = new StringBuilder("syntax = \"proto3\";\n\n");
    methodMessages.values().forEach(stringBuilder::append);
    definitionMessages.values().forEach(stringBuilder::append);
    return stringBuilder.toString();
  }

  /**
   * 展示全部的转化信息,即使type 暂时不被支持,也会被输出
   * 例如 :
   * message User{
   * int32 age;
   * int32 index;
   * repeated repeated User lists;
   * repeated map<String , User> maps;
   * string name;
   * repeated string names;
   * map<String , map<String , User>> stringMapMap;
   * map<String , User> stringUserMap;
   * repeated User users;
   * }
   * @return
   */
  public String showRawConvertMsg() {

    StringBuilder stringBuilder = new StringBuilder("syntax = \"proto3\";\n\n");
    methodMessages.values().forEach(messageModel -> {
      stringBuilder.append(messageModel.getAllMessage());
    });
    definitionMessages.values().forEach(messageModel -> {
      stringBuilder.append(messageModel.getAllMessage());
    });
    return stringBuilder.toString();
  }
}
