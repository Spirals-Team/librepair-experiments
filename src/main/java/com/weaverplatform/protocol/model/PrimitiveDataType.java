package com.weaverplatform.protocol.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Mohamad Alamili
 */
public enum PrimitiveDataType {

  @SerializedName("string")   STRING,
  @SerializedName("double")   DOUBLE,
  @SerializedName("date")     DATE,
  @SerializedName("boolean")  BOOLEAN,
}
