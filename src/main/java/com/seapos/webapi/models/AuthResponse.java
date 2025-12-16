package com.seapos.webapi.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponse {
    private String access_token;
    private String token_type;
    private Long expires_in;
    private boolean IsRedirect;
    private String RedirectUrl;

}
