package com.seapos.webapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UnlockUserModelInput {

    @JsonProperty("EntityUserId")
    @NotNull
    private Integer entityUserId;

    @JsonProperty("UserName")
    @NotNull
    private String userName;

    @JsonProperty("UserEmail")
    @NotNull
    @Email
    private String userEmail;

    // getters & setters
}