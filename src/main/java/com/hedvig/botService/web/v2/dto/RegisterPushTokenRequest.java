package com.hedvig.botService.web.v2.dto;

import lombok.Value;

@Value
public class RegisterPushTokenRequest {
  String fcmRegistrationToken;
}
