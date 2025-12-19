package com.seapos.webapi.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

@Setter
public class UnlockUserModelOutput {

    @JsonProperty("ResponseCode")
    private int responseCode;

    @JsonProperty("ResponseMessage")
    private String responseMessage;

}