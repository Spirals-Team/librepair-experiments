package convention.rest.api.tos;

import org.h2.util.StringUtils;

/**
 * This type represents the answer form the backoffice as the user information for authenticated users
 * Created by kfgodel on 14/01/17.
 */
public class BackofficeUserTo {

  public String uid;

  public String email;

  public String username;

  public String fullName;

  public String root;

  public String denied;

  public String hmac;

  public static BackofficeUserTo create(String uid, String email, String username, String fullName, String root, String denied, String hmac) {
    BackofficeUserTo backofficeUserTo = new BackofficeUserTo();
    backofficeUserTo.uid = uid;
    backofficeUserTo.username = username;
    backofficeUserTo.fullName = fullName;
    backofficeUserTo.root = root;
    backofficeUserTo.denied = denied;
    backofficeUserTo.email = email;
    backofficeUserTo.hmac = hmac;
    return backofficeUserTo;
  }

  public Boolean isRoot() {
    return "true".equals(root);
  }

  public Boolean hasGrantedAccess() {
    return StringUtils.isNullOrEmpty(denied);
  }

}
