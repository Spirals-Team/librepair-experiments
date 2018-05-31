package com.cmpl.web.core.common.user;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class ActionTokenServiceImpl implements ActionTokenService {

  private final TokenService tokenService;

  public ActionTokenServiceImpl(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  @Override
  public String generateToken(ActionToken actionToken) {
    Token token = tokenService.allocateToken(encryptTokenAsString(actionToken));
    return token.getKey();
  }

  @Override
  public ActionToken decryptToken(String aTokenKey) {
    Token token = tokenService.verifyToken(aTokenKey);
    return decryptTokenFromString(token.getExtendedInformation());
  }

  protected ActionToken decryptTokenFromString(String anExtendedInformationsToken) {

    List<String> result = Arrays.asList(anExtendedInformationsToken.split("\\|")).stream()
        .map(data -> data = data.trim()).collect(Collectors.toList());

    ActionToken actionToken = new ActionToken();
    actionToken.setUserId(Long.parseLong(result.get(0)));
    actionToken.setAction(result.get(1));
    actionToken.setExpirationDate(Instant.ofEpochMilli(Long.parseLong(result.get(2))));
    actionToken.setAdditionalParameters(decryptAdditionalParameters(result.get(3)));
    return actionToken;
  }

  protected String encryptTokenAsString(ActionToken actionToken) {

    String joinedString = new String();
    return joinedString.join("|", String.valueOf(actionToken.getUserId()), actionToken.getAction(),
        String.valueOf(actionToken.getExpirationDate().toEpochMilli()),
        encryptAdditionalParameters(actionToken.getAdditionalParameters()));
  }

  protected Map<String, String> decryptAdditionalParameters(String encryptedParams) {
    if (StringUtils.isEmpty(encryptedParams)) {
      return new HashMap();
    }

    Map<String, String> params = new HashMap();

    Arrays.stream(encryptedParams.split("#")).forEach(entry -> {
      List<String> keyValueAsList = Arrays.asList(entry.split("&=&"));
      if (!CollectionUtils.isEmpty(keyValueAsList) && keyValueAsList.size() > 1) {
        params.put(keyValueAsList.get(0), keyValueAsList.get(1));
      }

    });

    return params;
  }

  protected String encryptAdditionalParameters(Map<String, String> encryptedParams) {
    if (encryptedParams == null) {
      return "";
    }
    List<String> entriesAsStrings = new ArrayList();
    String joinedEntries = new String();
    for (Map.Entry<String, String> entry : encryptedParams.entrySet()) {
      entriesAsStrings.add(joinedEntries.join("&=&", entry.getKey(), entry.getValue()));
    }
    String mapJoined = new String();
    return mapJoined.join("#", entriesAsStrings);
  }
}
