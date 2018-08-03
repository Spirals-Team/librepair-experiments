package com.cmpl.web.core.common.user;

public interface ActionTokenService {

  String generateToken(ActionToken actionToken);

  ActionToken decryptToken(String token);

}
