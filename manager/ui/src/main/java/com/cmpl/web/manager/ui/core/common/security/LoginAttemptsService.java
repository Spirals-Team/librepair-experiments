package com.cmpl.web.manager.ui.core.common.security;

import java.util.Map;

public interface LoginAttemptsService {

  void successfulAttempt(String identifier, String ip);

  void failAttempt(String identifier, String ip);

  boolean isBlocked(String identifier, String ip);

  Map<String, Map<String, Integer>> get();

}
