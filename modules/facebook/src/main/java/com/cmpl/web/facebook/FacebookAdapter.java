package com.cmpl.web.facebook;

import java.util.List;

import org.springframework.social.facebook.api.MediaOperations;

public interface FacebookAdapter {

   boolean isAlreadyConnected();

   List<ImportablePost> getRecentFeed();

   MediaOperations getMediaOperations();

}
