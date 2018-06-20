package com.tul.ta.response;

import lombok.Data;

@Data
public class Authenticate {
    public String access_token;
    public String token_type;
    public String expires_in;
}
