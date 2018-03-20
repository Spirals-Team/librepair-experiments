package com.objectia.twostep;

import com.objectia.twostep.model.Token;
import com.objectia.twostep.model.TokenStore;

public class MyTokenStore implements TokenStore {

  private Token token = null;

  public Token load() {
    return this.token;
  } 

  public void save(Token token) {
    this.token = token;
  } 

  public void clear() {
    this.token = null;
  }

}

