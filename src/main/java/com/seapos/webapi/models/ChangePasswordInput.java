package com.seapos.webapi.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordInput {
    private String username;
    private String newPassword;
    private String confirmPassword;
}
