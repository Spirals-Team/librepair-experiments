package com.weaverplatform.sdk;

/**
 * @author bastbijl, Sysunite 2018
 */
public class MinioFile extends WeaverFile {

  private String lastModified;
  private String etag;
  private long size;

  public MinioFile() {
    super();
  }

  @Override
  public String getId() {
    if(name != null && name.indexOf("-") > -1) {
      return name.substring(0, name.indexOf("-"));
    }
    return id;
  }

  @Override
  public String getName() {
    if(name != null && name.indexOf("-") > -1) {
      return name.substring(name.indexOf("-") + 1);
    }
    return name;
  }

  public String getLastModified() {
    return lastModified;
  }

  public String getEtag() {
    return etag;
  }

  public long getSize() {
    return size;
  }
}
