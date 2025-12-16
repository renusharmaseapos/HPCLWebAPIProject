package com.seapos.webapi.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthErrorResponse {
    private String error;
    private String error_description;
}
