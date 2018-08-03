package com.cmpl.web.facebook;

import java.util.ArrayList;
import java.util.List;

import org.springframework.social.facebook.api.MediaOperations;

public class DoNothingFacebookAdapter implements FacebookAdapter {

  @Override
  public boolean isAlreadyConnected() {
    return true;
  }

  @Override
  public List<ImportablePost> getRecentFeed() {
    return new ArrayList<>();
  }

  @Override
  public MediaOperations getMediaOperations() {
    return null;
  }

}
