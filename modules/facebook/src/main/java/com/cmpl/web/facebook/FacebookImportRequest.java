package com.cmpl.web.facebook;

import java.util.List;

/**
 * Requete poru importer des post facebook via WS REST
 * 
 * @author Louis
 *
 */
public class FacebookImportRequest {

  private List<FacebookImportPost> postsToImport;

  public List<FacebookImportPost> getPostsToImport() {
    return postsToImport;
  }

  public void setPostsToImport(List<FacebookImportPost> postsToImport) {
    this.postsToImport = postsToImport;
  }

}
