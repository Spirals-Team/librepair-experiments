package org.apache.servicecomb.swagger.generator.protobuf.protoModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldModel implements Comparable<FieldModel> {
  private static final Logger LOGGER = LoggerFactory.getLogger(FieldModel.class);

  protected String fieldName;

  protected String type;

  protected int tag;

  //是否是重复字段
  protected boolean repeat;

  protected boolean enumMsg;

  public FieldModel(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getTag() {
    return tag;
  }

  public void setTag(int tag) {
    this.tag = tag;
  }

  public boolean isRepeat() {
    return repeat;
  }

  public void setRepeat(boolean repeat) {
    this.repeat = repeat;
  }

  public StringBuilder convertToProto(int tag) {
    StringBuilder builder = new StringBuilder();
    if (checkType(type)) {
      if (enumMsg) {
        builder.append(String.format(type, fieldName));
      } else {
        builder.append(type)
            .append(" ")
            .append(fieldName)
            .append(" = ")
            .append(tag)
            .append(";\n");
      }
    }
    return builder;
  }

  public StringBuilder showAllInfo() {
    StringBuilder builder = new StringBuilder();
    if (type.contains("enum")) {
      builder.append(String.format(type, fieldName));
    } else {
      builder.append(type)
          .append(" ")
          .append(fieldName)
          .append(";\n");
    }
    return builder;
  }

  private boolean checkType(String type) {

    //map 在protobuf 中不支持 repeated , map , enum 互相嵌套
    Pattern pattern = Pattern.compile("repeated|enum|map");
    Matcher matcher = pattern.matcher(type);
    int count = 0;
    while (matcher.find()) {
      count++;
    }
    if (count > 1) {
      LOGGER.warn("type {} may contain repeated, enum ,map duplicated", type);
      return false;
    }
    //enum
    if (type.contains("enum")) {
      enumMsg = true;
    } else {
      //unSupport, 防止 enum 中有字段正好包含 unSupport
      if (type.contains("unSupport")) {
        LOGGER.warn("type {} is unSupport ",type);
        return false;
      }
    }
    return true;
  }

  /**
   * 通过 fieldName 排序
   * @param o
   * @return
   */
  @Override
  public int compareTo(FieldModel o) {
    return this.fieldName.compareTo(o.getFieldName());
  }

}
